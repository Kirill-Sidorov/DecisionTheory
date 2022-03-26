package sidorov.lab3;

import sidorov.common.Logic;
import sidorov.common.Matrix;
import sidorov.common.MatrixValidator;
import sidorov.common.excelreader.ExcelReader;
import sidorov.common.excelreader.SheetNotFoundException;
import sidorov.common.excelreader.TaskSheet;
import sidorov.common.result.Result;
import sidorov.common.result.Status;
import sidorov.lab1.MixedStrategiesLogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SolutionMatrixGame2x2Logic implements Logic {

    private Matrix matrix = new Matrix();

    @Override
    public Result uploadData() {
        ExcelReader excelReader;
        try {
            excelReader = new ExcelReader(TaskSheet.SOLUTION_MATRIX_GAME_2x2);
        } catch (SheetNotFoundException e) {
            return new Result(Status.ERROR, "Лист с данными задания не найден");
        } catch (IOException e) {
            return new Result(Status.ERROR, "Не удалось загрузить данные из файла");
        }

        List<List<Double>> matrixList = excelReader.getMatrixFromSheet();

        MatrixValidator validator = new MatrixValidator(matrixList);
        if (!validator.validateThatMatrix2x2Size()) {
            return new Result(Status.ERROR, "Матрица невалидна или отлична от размера 2x2");
        }
        matrix = new Matrix(matrixList);
        return new Result(Status.DATA_UPLOADED, matrix.toText());
    }

    @Override
    public Result solveTask() {
        double a = matrix.get(1, 1) - matrix.get(1, 0);
        double b = a + matrix.get(0, 0) - matrix.get(0, 1);
        double p1 = a / b;
        double q1 = (matrix.get(1, 1) - matrix.get(0, 1)) / b;

        double W = (matrix.get(0, 0) * matrix.get(1, 1) - matrix.get(0, 1) * matrix.get(1, 0)) / b;

        List<Double> pVector = new ArrayList<>();
        pVector.add(p1);
        pVector.add(1 - p1);

        List<Double> qVector = new ArrayList<>();
        qVector.add(q1);
        qVector.add(1 - q1);

        MixedStrategiesLogic mixedStrategiesLogic = new MixedStrategiesLogic(matrix, pVector, qVector);
        Result mixedStrategiesResult = mixedStrategiesLogic.solveTask();

        StringBuilder result = new StringBuilder();
        result.append(String.format("<p,q> \u2248 < (%.4f;%.4f)T, (%.4f;%.4f)T >\n", p1, 1 - p1, q1, 1 - q1));
        result.append(String.format("W \u2248 %.4f\n", W));

        if (mixedStrategiesResult.status == Status.TASK_SOLVED) {
            result.append("\nПроверка:\n\n");
            result.append(mixedStrategiesResult.text);
        }

        return new Result(Status.TASK_SOLVED, result.toString());
    }
}
