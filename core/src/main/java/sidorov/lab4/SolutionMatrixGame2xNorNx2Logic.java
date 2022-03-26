package sidorov.lab4;

import sidorov.common.InputData;
import sidorov.common.Logic;
import sidorov.common.Matrix;
import sidorov.common.MatrixValidator;
import sidorov.common.WithChart;
import sidorov.common.WithInputData;
import sidorov.common.excelreader.ExcelReader;
import sidorov.common.excelreader.SheetNotFoundException;
import sidorov.common.excelreader.TaskSheet;
import sidorov.common.result.Result;
import sidorov.common.result.ResultWithChartData;
import sidorov.common.result.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SolutionMatrixGame2xNorNx2Logic implements Logic, WithChart, WithInputData {

    private Matrix matrix = new Matrix();

    @Override
    public Result uploadData() {
        ExcelReader excelReader;
        try {
            excelReader = new ExcelReader(TaskSheet.SOLUTION_MATRIX_GAME_2xN_OR_Nx2);
        } catch (SheetNotFoundException e) {
            return new Result(Status.ERROR, "Лист с данными задания не найден");
        } catch (IOException e) {
            return new Result(Status.ERROR, "Не удалось загрузить данные из файла");
        }

        List<List<Double>> matrixList = excelReader.getMatrixFromSheet();

        MatrixValidator validator = new MatrixValidator(matrixList);
        if (!validator.validateThatMatrix2xNSize()) {
            return new Result(Status.ERROR, "Матрица невалидна или отлична от размера 2xN");
        }
        matrix = new Matrix(matrixList);
        return new Result(Status.DATA_UPLOADED, matrix.toText());
    }

    @Override
    public Result solveTask() {
        return null;
    }

    @Override
    public ResultWithChartData getChartData() {
        List<Function> functions = new ArrayList<>();
        for (int j = 0; j < matrix.numberColumns; j++) {
            double h1 = matrix.get(0, j);
            double h2 = matrix.get(1, j);
            Function function = p -> ((h1 - h2) * p + h2);
            functions.add(function);
        }
        return new ResultWithChartData("", functions);
    }

    @Override
    public void setInputData(InputData inputData) {

    }
}
