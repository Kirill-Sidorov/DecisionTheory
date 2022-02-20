package sidorov.lab1.mixedstrategies;

import sidorov.common.Matrix;

import java.util.List;

public class MixedSecondStep {

    private final Matrix matrix;

    public MixedSecondStep(Matrix matrix) {
        this.matrix = matrix;
    }

    public MixedSecondStepResult calcDataX(List<Double> pVector) {
        double[] xVector = new double[matrix.numberColumns()];

        for (int j = 0; j < matrix.numberColumns(); j++) {
            double sum = 0;
            for (int i = 0; i < matrix.numberRows(); i++) {
                sum += matrix.get(i, j) * pVector.get(i);
            }
            xVector[j] = sum;
        }

        double xMin = xVector[0];
        for (double v : xVector) {
            if (xMin > v) {
                xMin = v;
            }
        }

        return new MixedSecondStepResult(xMin, xVector);
    }

    public MixedSecondStepResult calcDataY(List<Double> qVector) {
        double[] yVector = new double[matrix.numberRows()];

        for (int i = 0; i < matrix.numberRows(); i++) {
            double sum = 0;
            for (int j = 0; j < matrix.numberColumns(); j++) {
                sum += matrix.get(i, j) * qVector.get(j);
            }
            yVector[i] = sum;
        }

        double yMax = yVector[0];
        for (double v : yVector) {
            if (yMax < v) {
                yMax = v;
            }
        }

        return new MixedSecondStepResult(yMax, yVector);
    }
}
