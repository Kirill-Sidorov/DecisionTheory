package sidorov.lab4;

import org.apache.commons.math3.util.Precision;
import sidorov.common.InputData;
import sidorov.common.Logic;
import sidorov.common.Result;
import sidorov.common.Status;
import sidorov.common.chart.ChartData;
import sidorov.common.chart.Dot;
import sidorov.common.chart.Function;
import sidorov.common.excelreader.ExcelReader;
import sidorov.common.excelreader.SheetNotFoundException;
import sidorov.common.excelreader.TaskSheet;
import sidorov.common.matrix.Matrix;
import sidorov.common.matrix.MatrixOperation;
import sidorov.common.matrix.MatrixValidation;
import sidorov.lab1.MixedStrategiesLogic;
import sidorov.lab2.reduction.Reduction;
import sidorov.lab2.reduction.ReductionResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolutionMatrixGame2xNorNx2Logic implements Logic {

    private Matrix matrix = new Matrix();
    private Matrix initialMatrix = new Matrix();
    private List<Integer> existsColumnsAfterReduction = new ArrayList<>();
    private boolean isTransposed = false;
    private boolean isInfinitelyManyP = false;
    private int k = 0;
    private int l = 0;
    private int s = 0;

    @Override
    public Result uploadData() {
        existsColumnsAfterReduction = new ArrayList<>();
        isTransposed = false;
        isInfinitelyManyP = false;

        ExcelReader excelReader;
        try {
            excelReader = new ExcelReader(TaskSheet.SOLUTION_MATRIX_GAME_2xN_OR_Nx2);
        } catch (SheetNotFoundException e) {
            return new Result(Status.ERROR, "Лист с данными задания не найден");
        } catch (IOException e) {
            return new Result(Status.ERROR, "Не удалось загрузить данные из файла");
        }

        List<List<Double>> matrixList = excelReader.getMatrixFromSheet();

        MatrixValidation validator = new MatrixValidation(matrixList);
        if (validator.validateThatMatrixNx2Size()) {
            isTransposed = true;
        } else if (!validator.validateThatMatrix2xNSize()) {
            return new Result(Status.ERROR, "Матрица невалидна или отлична от размера 2xN или Nx2");
        }
        matrix = new Matrix(matrixList);
        initialMatrix = matrix;

        if (isTransposed) {
            matrix = new MatrixOperation(matrix).transposeAndChangeSign();
        }

        for (int j = 0; j < matrix.numberColumns; j++) {
            if (matrix.get(0, j) == matrix.get(1, j)) {
                isInfinitelyManyP = true;
                k = j;
            }
        }

        Reduction reduction = new Reduction(matrix);
        ReductionResult reductionResult = reduction.perform(true);

        for (int i = 0; i < matrix.numberColumns; i++) {
            if (!reductionResult.deletedColumns.contains(i)) {
                existsColumnsAfterReduction.add(i);
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("Исходная матрица:\n");
        result.append(initialMatrix.toText());
        if (isTransposed) {
            result.append("Матрица после транспонирования и умножения на -1:\n");
            result.append(matrix.toText());
        }
        result.append("Матрица после редукции:\n");
        result.append(matrix.toTextWithDeletedRowsAndColumns(reductionResult.deletedColumns, reductionResult.deletedRows));
        result.append("\n");
        return new Result(Status.DATA_UPLOADED, result.toString());
    }

    @Override
    public Result solveTask() {
        if (isInfinitelyManyP) {
            double h2l = matrix.get(1, l);
            double c = (matrix.get(0, k) - h2l) / (matrix.get(0, l) - h2l);
            double h2s = matrix.get(1, s);
            double d = (matrix.get(0, k) - h2s) / (matrix.get(0, s) - h2s);

            List<List<Double>> pVectors = new ArrayList<>();
            double step = (d - c) / 5;
            double leftLimit = c;
            while (leftLimit < (d - step)) {
                List<Double> pVector = new ArrayList<>();
                pVector.add(Precision.round(leftLimit, 3));
                pVector.add(Precision.round(1 - leftLimit, 3));
                pVectors.add(pVector);
                leftLimit += step;
            }
            List<Double> pVectorLimitD = new ArrayList<>();
            pVectorLimitD.add(Precision.round(d, 3));
            pVectorLimitD.add(Precision.round(1 - d, 3));
            pVectors.add(pVectorLimitD);


            List<Double> qVector = new ArrayList<>();
            for (int column = 0; column < matrix.numberColumns; column++) {
                if (column == k) {
                    qVector.add(1d);
                } else {
                    qVector.add(0d);
                }
            }

            StringBuilder stringResult = new StringBuilder();
            stringResult.append(String.format("p1 \u2208 [%.3f; %.3f]\n\nПроверка:\n", c, d));

            if (isTransposed) {
                for (List<Double> pVector : pVectors) {
                    MixedStrategiesLogic mixedStrategiesLogic = new MixedStrategiesLogic(initialMatrix, qVector, pVector);
                    Result result = mixedStrategiesLogic.solveTask();

                    stringResult.append(String.format("\nПри p1 = %.3f\n", pVector.get(0)));
                    stringResult.append(String.format("Вектор p = %s\n", Arrays.toString(qVector.toArray())));
                    stringResult.append(String.format("Вектор q = %s\n", Arrays.toString(pVector.toArray())));
                    stringResult.append(result.text());
                }
            } else {
                for (List<Double> pVector : pVectors) {
                    MixedStrategiesLogic mixedStrategiesLogic = new MixedStrategiesLogic(initialMatrix, pVector, qVector);
                    Result result = mixedStrategiesLogic.solveTask();

                    stringResult.append(String.format("\nПри p1 = %.3f\n", pVector.get(0)));
                    stringResult.append(String.format("Вектор p = %s\n", Arrays.toString(pVector.toArray())));
                    stringResult.append(String.format("Вектор q = %s\n", Arrays.toString(qVector.toArray())));
                    stringResult.append(result.text());
                }
            }
            return new Result(Status.SUCCESS, stringResult.toString());
        } else {
            double z = matrix.get(1, s) - matrix.get(1, l);
            double x = z + matrix.get(0, l) - matrix.get(0, s);
            double p1 = z / x;
            double y = (matrix.get(1, s) - matrix.get(0, s)) / x;
            double W = (matrix.get(0, l) * matrix.get(1, s) - matrix.get(0, s) * matrix.get(1, l)) / x;

            List<Double> pVector = new ArrayList<>();
            pVector.add(Precision.round(p1, 3));
            pVector.add(Precision.round(1 - p1, 3));

            List<Double> qVector = new ArrayList<>();
            for (int column = 0; column < matrix.numberColumns; column++) {
                if (column == l) {
                    qVector.add(Precision.round(y, 3));
                } else if(column == s) {
                    qVector.add(Precision.round(1 - y, 3));
                } else {
                    qVector.add(0d);
                }
            }

            if (isTransposed) {
                List<Double> temp = qVector;
                qVector = pVector;
                pVector = temp;
                W *= -1;
            }

            MixedStrategiesLogic mixedStrategiesLogic = new MixedStrategiesLogic(initialMatrix, pVector, qVector);
            Result result = mixedStrategiesLogic.solveTask();

            StringBuilder stringResult = new StringBuilder();
            stringResult.append(String.format("W = %.1f\n", W));
            stringResult.append(String.format("Вектор p = %s\n", Arrays.toString(pVector.toArray())));
            stringResult.append(String.format("Вектор q = %s\n\n", Arrays.toString(qVector.toArray())));
            stringResult.append("Проверка:\n\n");
            stringResult.append(result.text());

            return new Result(Status.SUCCESS, stringResult.toString());
        }
    }

    @Override
    public Result getChartData() {
        List<Function> functions = new ArrayList<>();
        for (int i = 0; i < existsColumnsAfterReduction.size(); i++) {
            double h1 = matrix.get(0, existsColumnsAfterReduction.get(i));
            double h2 = matrix.get(1, existsColumnsAfterReduction.get(i));

            Dot[] dots = new Dot[11];

            for (int p = 0; p < 11; p++) {
                double x = p * 0.1;
                double y = (h1 - h2) * x + h2;
                dots[p] = new Dot(x, y);
            }

            functions.add(new Function(dots, "g" + (i + 1)));
        }
        List<ChartData> chartDataList = new ArrayList<>();
        chartDataList.add(new ChartData(functions, "p1", "g(p1)"));
        return new Result(chartDataList);
    }

    @Override
    public Result setInputData(InputData inputData) {
        int reductionL = inputData.l() - 1;
        int reductionS = inputData.s() - 1;
        if (reductionL >= 0 && reductionS >= 0
                && existsColumnsAfterReduction.size() > reductionL && existsColumnsAfterReduction.size() > reductionS) {
            l = existsColumnsAfterReduction.get(reductionL);
            s = existsColumnsAfterReduction.get(reductionS);
            return new Result(Status.SUCCESS, "Успешный ввод данных");
        }
        return new Result(Status.ERROR, "Для текущих условий данные невалидны");
    }
}
