package sidorov.lab8;

import sidorov.common.Logic;
import sidorov.common.Result;
import sidorov.common.Status;
import sidorov.common.excelreader.ExcelReader;
import sidorov.common.excelreader.SheetNotFoundException;
import sidorov.common.excelreader.TaskSheet;
import sidorov.common.matrix.Matrix;
import sidorov.common.matrix.MatrixValidation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReverseOutputMethodLogic implements Logic {

    private List<String> setX = new ArrayList<>();
    private List<String> setY = new ArrayList<>();
    private Matrix matrixR = new Matrix();

    public ReverseOutputMethodLogic() {
        setX.add("");
        setY.add("");
    }

    @Override
    public Result uploadData() {
        ExcelReader excelReader;
        try {
            excelReader = new ExcelReader(TaskSheet.REVERSE_OUTPUT_METHOD);
        } catch (SheetNotFoundException e) {
            return new Result(Status.ERROR, "Лист с данными задания не найден");
        } catch (IOException e) {
            return new Result(Status.ERROR, "Не удалось загрузить данные из файла");
        }

        setX = excelReader.getStringSetFromSheetByName("X");
        setY = excelReader.getStringSetFromSheetByName("Y");

        List<List<Double>> listMatrixR = excelReader.getMatrixFromSheetByName("Mr");

        MatrixValidation validatorMr = new MatrixValidation(listMatrixR);
        if (!validatorMr.validateMatrixWithSize(setX.size(), setY.size())) {
            return new Result(Status.ERROR, "Матрица Mr невалидна, либо не соответствует множествам Y и Z");
        }

        matrixR = new Matrix(listMatrixR);

        StringBuilder result = new StringBuilder();
        result.append(String.format("X = %s\n", Arrays.toString(setX.toArray())));
        result.append(String.format("Y = %s\n", Arrays.toString(setY.toArray())));
        result.append(String.format("Матрица Mr:\n%s\n", matrixR.toText()));

        return new Result(Status.DATA_UPLOADED, result.toString());
    }

    @Override
    public Result solveTask() {
        return new Result(Status.SUCCESS, "OK");
    }
}
