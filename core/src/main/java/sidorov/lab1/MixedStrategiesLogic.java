package sidorov.lab1;

import sidorov.common.Element;
import sidorov.common.Logic;
import sidorov.common.Matrix;
import sidorov.common.MatrixValidator;
import sidorov.common.result.Result;
import sidorov.common.result.Status;
import sidorov.lab1.excelreader.Lab1ExcelReader;
import sidorov.lab1.excelreader.MixedStrategiesData;
import sidorov.lab1.mixedstrategies.MixedFirstStep;
import sidorov.lab1.mixedstrategies.MixedSecondStep;
import sidorov.lab1.mixedstrategies.MixedSecondStepResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MixedStrategiesLogic implements Logic {

    private Matrix matrix = new Matrix();
    private List<Double> pVector = new ArrayList<>();
    private List<Double> qVector = new ArrayList<>();

    @Override
    public Result uploadData() {
        Lab1ExcelReader lab1ExcelReader = new Lab1ExcelReader();
        MixedStrategiesData data = null;
        try {
            data = lab1ExcelReader.readDataForMixedStrategies();
        } catch (IOException e) {
            return new Result(Status.ERROR, "Не удалось загрузить данные из файла");
        }

        MatrixValidator validator = new MatrixValidator(data.matrix);
        if (!validator.validateMatrix()) {
            return new Result(Status.ERROR, "Матрица невалидна");
        }
        if (!validator.validatePVector(data.pVector)) {
            return new Result(Status.ERROR, "Вектор p невалидин");
        }
        if (!validator.validateQVector(data.qVector)) {
            return new Result(Status.ERROR, "Вектор q невалидин");
        }

        matrix = new Matrix(data.matrix);
        pVector = data.pVector;
        qVector = data.qVector;

        StringBuilder result = new StringBuilder();
        result.append(matrix.toText());
        result.append(String.format("Вектор p = %s\n", Arrays.toString(pVector.toArray())));
        result.append(String.format("Вектор q = %s\n", Arrays.toString(qVector.toArray())));

        return new Result(Status.DATA_UPLOADED, result.toString());
    }

    @Override
    public Result solveTask() {

        MixedFirstStep mixedFirstStep = new MixedFirstStep(matrix);

        Element V1 = mixedFirstStep.findV1();
        Element V2 = mixedFirstStep.findV2();

        MixedSecondStep mixedSecondStep = new MixedSecondStep(matrix);
        MixedSecondStepResult xResult = mixedSecondStep.calcDataX(pVector);
        MixedSecondStepResult yResult = mixedSecondStep.calcDataY(qVector);

        StringBuilder result = new StringBuilder();
        result.append(String.format("V1 = %.4f (i = %d; j = %d)\n", V1.value, V1.i + 1, V1.j + 1));
        result.append(String.format("V2 = %.4f (i = %d; j = %d)\n", V2.value, V2.i + 1, V2.j + 1));
        result.append(String.format("Вектор x = %s\nxMin = %.4f\n", Arrays.toString(xResult.vector), xResult.limitValue));
        result.append(String.format("Вектор y = %s\nyMax = %.4f\n", Arrays.toString(yResult.vector), yResult.limitValue));

        if (xResult.limitValue == yResult.limitValue) {
            result.append("Так как xMin = yMax - ситуация <p,q> в смешанные стратегиях является седловой точкой в смешанные стратегиях.\n");
            result.append(String.format("W = xMin = yMax = %.4f\n", xResult.limitValue));
        }

        return new Result(Status.TASK_SOLVED, result.toString());
    }
}
