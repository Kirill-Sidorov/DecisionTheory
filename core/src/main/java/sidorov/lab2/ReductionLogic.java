package sidorov.lab2;

import sidorov.common.Logic;
import sidorov.common.Matrix;
import sidorov.common.MatrixValidation;
import sidorov.common.excelreader.ExcelReader;
import sidorov.common.excelreader.SheetNotFoundException;
import sidorov.common.excelreader.TaskSheet;
import sidorov.common.result.Result;
import sidorov.common.result.Status;
import sidorov.lab2.reduction.Reduction;
import sidorov.lab2.reduction.ReductionResult;

import java.io.IOException;
import java.util.List;

public class ReductionLogic implements Logic {

    private Matrix matrix = new Matrix();

    @Override
    public Result uploadData() {
        ExcelReader excelReader;
        try {
            excelReader = new ExcelReader(TaskSheet.REDUCTION);
        } catch (SheetNotFoundException e) {
            return new Result(Status.ERROR, "Лист с данными задания не найден");
        } catch (IOException e) {
            return new Result(Status.ERROR, "Не удалось загрузить данные из файла");
        }

        List<List<Double>> matrixList = excelReader.getMatrixFromSheet();

        MatrixValidation validator = new MatrixValidation(matrixList);
        if (!validator.validateMatrix()) {
            return new Result(Status.ERROR, "Матрица невалидна");
        }
        matrix = new Matrix(matrixList);
        return new Result(Status.DATA_UPLOADED, matrix.toText());
    }

    @Override
    public Result solveTask() {
        Reduction reduction = new Reduction(matrix);
        ReductionResult strictReductionResult = reduction.perform(true);
        ReductionResult completeReductionResult = reduction.perform(false);

        StringBuilder result = new StringBuilder();
        result.append("Матрица A (полная редукция):\n");
        result.append(matrix.toTextWithDeletedRowsAndColumns(completeReductionResult.deletedColumns, completeReductionResult.deletedRows));
        result.append("\n");
        result.append("Матрица B (строгая редукция):\n");
        result.append(matrix.toTextWithDeletedRowsAndColumns(strictReductionResult.deletedColumns, strictReductionResult.deletedRows));
        result.append("\n");
        return new Result(Status.SUCCESS, result.toString());
    }
}
