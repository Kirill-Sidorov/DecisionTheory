package sidorov.lab1;

import org.apache.commons.math3.util.Precision;
import sidorov.common.matrix.Element;
import sidorov.common.Logic;
import sidorov.common.matrix.Matrix;
import sidorov.common.matrix.MatrixValidation;
import sidorov.common.excelreader.ExcelReader;
import sidorov.common.excelreader.SheetNotFoundException;
import sidorov.common.excelreader.TaskSheet;
import sidorov.common.Result;
import sidorov.common.Status;
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

    public MixedStrategiesLogic() {
        this.pVector.add(0d);
        this.qVector.add(0d);
    }

    public MixedStrategiesLogic(Matrix matrix, List<Double> pVector, List<Double> qVector) {
        if (matrix != null &&
                pVector != null &&
                qVector != null &&
                pVector.size() == matrix.numberRows &&
                qVector.size() == matrix.numberColumns) {

            this.matrix = matrix;
            this.pVector = pVector;
            this.qVector = qVector;
        } else {
            this.pVector.add(0d);
            this.qVector.add(0d);
        }
    }

    @Override
    public Result uploadData() {
        ExcelReader excelReader;
        try {
            excelReader = new ExcelReader(TaskSheet.MIXED_STRATEGIES);
        } catch (SheetNotFoundException e) {
            return new Result(Status.ERROR, "Лист с данными задания не найден");
        } catch (IOException e) {
            return new Result(Status.ERROR, "Не удалось загрузить данные из файла");
        }

        List<List<Double>> loadedMatrix = excelReader.getMatrixFromSheet();
        List<Double> loadedPVector = excelReader.getVectorFromSheet("p");
        List<Double> loadedQVector = excelReader.getVectorFromSheet("q");

        MatrixValidation validator = new MatrixValidation(loadedMatrix);
        if (!validator.validateMatrix()) {
            return new Result(Status.ERROR, "Матрица невалидна");
        }
        if (!validator.validatePVector(loadedPVector)) {
            return new Result(Status.ERROR, "Вектор p невалидин");
        }
        if (!validator.validateQVector(loadedQVector)) {
            return new Result(Status.ERROR, "Вектор q невалидин");
        }

        matrix = new Matrix(loadedMatrix);
        pVector = loadedPVector;
        qVector = loadedQVector;

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
        result.append(String.format("V1 = %.3f (i = %d; j = %d)\n", V1.value, V1.i + 1, V1.j + 1));
        result.append(String.format("V2 = %.3f (i = %d; j = %d)\n", V2.value, V2.i + 1, V2.j + 1));
        result.append(String.format("Вектор x = %s\nxMin = %.1f\n", Arrays.toString(xResult.vector), xResult.limitValue));
        result.append(String.format("Вектор y = %s\nyMax = %.1f\n", Arrays.toString(yResult.vector), yResult.limitValue));

        if (Precision.round(xResult.limitValue, 1) == Precision.round(yResult.limitValue, 1)) {
            result.append("Так как xMin = yMax - ситуация <p,q> в смешанных стратегиях \nявляется седловой точкой в смешанных стратегиях.\n");
            result.append(String.format("W = xMin = yMax = %.1f\n", xResult.limitValue));
        }

        return new Result(Status.SUCCESS, result.toString());
    }
}
