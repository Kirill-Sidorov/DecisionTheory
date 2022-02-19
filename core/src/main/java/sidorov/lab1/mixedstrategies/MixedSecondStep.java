package sidorov.lab1.mixedstrategies;

import sidorov.common.Matrix;

public class MixedSecondStep {

    private final Matrix matrix;

    public MixedSecondStep(Matrix matrix) {
        this.matrix = matrix;
    }

    public MixedSecondStepResult calcDataX(double[] pVector) {
        double[] xVector = new double[matrix.columnsNumber()];

        for (int j = 0; j < matrix.columnsNumber(); j++) {
            double sum = 0;
            for (int i = 0; i < matrix.rowsNumber(); i++) {
                sum += matrix.get(i, j) * pVector[i];
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

    public MixedSecondStepResult calcDataY(double[] qVector) {
        double[] yVector = new double[matrix.rowsNumber()];

        for (int i = 0; i < matrix.rowsNumber(); i++) {
            double sum = 0;
            for (int j = 0; j < matrix.columnsNumber(); j++) {
                sum += matrix.get(i, j) * qVector[j];
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
