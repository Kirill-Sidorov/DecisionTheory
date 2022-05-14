package sidorov.lab7;

import sidorov.common.Logic;
import sidorov.common.Result;
import sidorov.common.Status;
import sidorov.common.excelreader.ExcelReader;
import sidorov.common.excelreader.SheetNotFoundException;
import sidorov.common.excelreader.TaskSheet;
import sidorov.common.matrix.Element;
import sidorov.common.matrix.Matrix;
import sidorov.common.matrix.MatrixValidation;
import sidorov.lab7.directoutputmethod.DirectOutputOperation;
import sidorov.lab7.directoutputmethod.DirectOutputResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectOutputMethodLogic implements Logic {

    private List<String> setX = new ArrayList<>();
    private List<String> setY = new ArrayList<>();
    private List<String> setZ = new ArrayList<>();
    private Matrix matrixZ = new Matrix();
    private Matrix matrixR = new Matrix();

    public DirectOutputMethodLogic() {
        setX.add("");
        setY.add("");
        setZ.add("");
    }

    @Override
    public Result uploadData() {
        ExcelReader excelReader;
        try {
            excelReader = new ExcelReader(TaskSheet.DIRECT_OUTPUT_METHOD);
        } catch (SheetNotFoundException e) {
            return new Result(Status.ERROR, "Лист с данными задания не найден");
        } catch (IOException e) {
            return new Result(Status.ERROR, "Не удалось загрузить данные из файла");
        }

        setX = excelReader.getStringSetFromSheetByName("X");
        setY = excelReader.getStringSetFromSheetByName("Y");
        setZ = excelReader.getStringSetFromSheetByName("Z");

        List<List<Double>> listMatrixZ = excelReader.getMatrixFromSheetByName("Mz");
        List<List<Double>> listMatrixR = excelReader.getMatrixFromSheetByName("Mr");

        MatrixValidation validatorMz = new MatrixValidation(listMatrixZ);
        if (!validatorMz.validateMatrixWithSize(setX.size(), setY.size())) {
            return new Result(Status.ERROR, "Матрица Mz невалидна, либо не соответствует множествам X и Y");
        }
        MatrixValidation validatorMr = new MatrixValidation(listMatrixR);
        if (!validatorMr.validateMatrixWithSize(setY.size(), setZ.size())) {
            return new Result(Status.ERROR, "Матрица Mr невалидна, либо не соответствует множествам Y и Z");
        }

        matrixZ = new Matrix(listMatrixZ);
        matrixR = new Matrix(listMatrixR);

        StringBuilder result = new StringBuilder();
        result.append(String.format("X = %s\n", Arrays.toString(setX.toArray())));
        result.append(String.format("Y = %s\n", Arrays.toString(setY.toArray())));
        result.append(String.format("Z = %s\n\n", Arrays.toString(setZ.toArray())));
        result.append(String.format("Матрица Mz:\n%s\n", matrixZ.toText()));
        result.append(String.format("Матрица Mr:\n%s\n", matrixR.toText()));

        return new Result(Status.DATA_UPLOADED, result.toString());
    }

    @Override
    public Result solveTask() {

        DirectOutputOperation directOutputOperation = new DirectOutputOperation(matrixZ, matrixR);

        DirectOutputResult maxMin = directOutputOperation.execute(Math::min, Math::max);
        DirectOutputResult maxProd = directOutputOperation.execute((a, b) -> a * b, Math::max);
        DirectOutputResult minMax = directOutputOperation.execute(Math::max, Math::min);
        DirectOutputResult maxMax = directOutputOperation.execute(Math::max, Math::max);
        DirectOutputResult minMin = directOutputOperation.execute(Math::min, Math::min);
        DirectOutputResult maxAverage = directOutputOperation.execute((a, b) -> 0.5 * (a + b), Math::max);
        DirectOutputResult sumProd = directOutputOperation.sumProd();

        StringBuilder stringResult = new StringBuilder();

        stringResult.append("\nmaxMin\n");
        stringResult.append(maxMin.matrix.toText());
        stringResult.append("\n");
        for (Element element : maxMin.maxInColumns) {
            stringResult.append(element.value).append(" ");
        }

        stringResult.append("\nmaxProd\n");
        stringResult.append(maxProd.matrix.toText());
        stringResult.append("\n");
        for (Element element : maxProd.maxInColumns) {
            stringResult.append(element.value).append(" ");
        }

        stringResult.append("\nminMax\n");
        stringResult.append(minMax.matrix.toText());
        stringResult.append("\n");
        for (Element element : minMax.maxInColumns) {
            stringResult.append(element.value).append(" ");
        }

        stringResult.append("\nmaxMax\n");
        stringResult.append(maxMax.matrix.toText());
        stringResult.append("\n");
        for (Element element : maxMax.maxInColumns) {
            stringResult.append(element.value).append(" ");
        }

        stringResult.append("\nminMin\n");
        stringResult.append(minMin.matrix.toText());
        stringResult.append("\n");
        for (Element element : minMin.maxInColumns) {
            stringResult.append(element.value).append(" ");
        }

        stringResult.append("\nmaxAverage\n");
        stringResult.append(maxAverage.matrix.toText());
        stringResult.append("\n");
        for (Element element : maxAverage.maxInColumns) {
            stringResult.append(element.value).append(" ");
        }

        stringResult.append("\nsumProd\n");
        stringResult.append(sumProd.matrix.toText());
        stringResult.append("\n");
        for (Element element : sumProd.maxInColumns) {
            stringResult.append(element.value).append(" ");
        }

        return new Result(Status.SUCCESS, stringResult.toString());
    }
}
