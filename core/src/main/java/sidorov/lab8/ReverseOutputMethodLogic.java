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
    private List<Double> b = new ArrayList<>();

    private int q = 1;
    private int m = 1;
    private double deltaLimit = 0;

    public ReverseOutputMethodLogic() {
        setX.add("");
        setY.add("");
        b.add(0d);
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
        b = excelReader.getVectorFromSheet("b");

        if (setX.size() != 3) {
            return new Result(Status.ERROR, "Множество X неравно 3");
        }
        if (setY.size() != 3) {
            return new Result(Status.ERROR, "Множество Y неравно 3");
        }
        MatrixValidation validatorMr = new MatrixValidation(listMatrixR);
        if (!validatorMr.validateMatrixWithSize(setX.size(), setY.size())) {
            return new Result(Status.ERROR, "Матрица Mr невалидна, либо не соответствует множествам Y и Z (размеру 3x3)");
        }
        if (b.size() != 3) {
            return new Result(Status.ERROR, "Вектор b неравен 3");
        }

        matrixR = new Matrix(listMatrixR);

        q = matrixR.numberRows;
        m = matrixR.numberColumns;
        deltaLimit = 0.1 * (m - 1);

        StringBuilder result = new StringBuilder();
        result.append(String.format("X = %s\n", Arrays.toString(setX.toArray())));
        result.append(String.format("Y = %s\n", Arrays.toString(setY.toArray())));
        result.append(String.format("Матрица Mr:\n%s\n", matrixR.toText()));
        result.append("b = ");
        result.append(Arrays.toString(b.toArray(new Double[0])));
        result.append(String.format("\nq = %d\nm = %d\ndelta* = %1.2f", q, m, deltaLimit));

        return new Result(Status.DATA_UPLOADED, result.toString());
    }

    @Override
    public Result solveTask() {
        double[] steps = new double[]{0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1};

        List<Matrix> matrixList = new ArrayList<>(steps.length);
        List<Double> minValueInMatrix = new ArrayList<>(steps.length);

        for (int n = 0; n < steps.length; n++) {
            List<List<Double>> matrix = new ArrayList<>(steps.length);
            double min = 10;
            for (int i = 0; i < steps.length; i++) {
                List<Double> row = new ArrayList<>(steps.length);
                for (int j = 0; j < steps.length; j++) {
                    double sum = 0;
                    for (int k = 0; k < 3; k++) {
                        double min1 = Math.min(matrixR.get(0, k), steps[j]);
                        double min2 = Math.min(matrixR.get(1, k), steps[i]);
                        double min3 = Math.min(matrixR.get(2, k), steps[n]);
                        double max1 = Math.max(min1, min2);
                        sum += Math.abs(b.get(k) - Math.max(min3, max1));
                    }
                    row.add(sum);
                    if (min > sum) {
                        min = sum;
                    }
                }
                matrix.add(row);
            }
            matrixList.add(new Matrix(matrix));
            minValueInMatrix.add(min);
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < matrixList.size(); i++) {
            result.append(String.format("\n%s\n", matrixList.get(i).toTextWithMinValues(minValueInMatrix.get(i), deltaLimit)));
        }

        return new Result(Status.SUCCESS, result.toString());
    }
}
