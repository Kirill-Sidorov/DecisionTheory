package sidorov.lab7.directoutputmethod;

import sidorov.common.matrix.Element;
import sidorov.common.matrix.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectOutputOperation {

    private final Matrix matrixZ;
    private final Matrix matrixR;

    public DirectOutputOperation(Matrix matrixZ, Matrix matrixR) {
        this.matrixZ = matrixZ;
        this.matrixR = matrixR;
    }

    public DirectOutputResult execute(Operation first, Operation last) {
        List<List<Double>> matrix = new ArrayList<>();
        Element[] maxInColumns = new Element[matrixR.numberColumns];
        Arrays.fill(maxInColumns, new Element(0, 0, 0));
        for (int i = 0; i < matrixZ.numberRows; i++) {
            List<Double> row = new ArrayList<>();
            for (int k = 0; k < matrixR.numberColumns; k++) {

                //direct output operations
                double value = first.perform(matrixZ.get(i, 0), matrixR.get(0, k));
                for (int j = 1; j < matrixZ.numberColumns; j++) {
                    double current = first.perform(matrixZ.get(i, j), matrixR.get(j, k));
                    value = last.perform(value, current);
                }

                row.add(value);
                if (maxInColumns[k].value < value) {
                    maxInColumns[k] = new Element(i, k, value);
                }
            }
            matrix.add(row);
        }
        return new DirectOutputResult(new Matrix(matrix), maxInColumns);
    }

    public DirectOutputResult sumProd() {
        List<List<Double>> matrix = new ArrayList<>();
        Element[] maxInColumns = new Element[matrixR.numberColumns];
        Arrays.fill(maxInColumns, new Element(0, 0, 0));
        for (int i = 0; i < matrixZ.numberRows; i++) {
            List<Double> row = new ArrayList<>();
            for (int k = 0; k < matrixR.numberColumns; k++) {

                double sum = 0;
                for (int j = 0; j < matrixZ.numberColumns; j++) {
                    sum += matrixZ.get(i, j) * matrixR.get(j, k);
                }
                double value = 1 / (1 + Math.exp(sum * -1));
                row.add(value);
                if (maxInColumns[k].value < value) {
                    maxInColumns[k] = new Element(i, k, value);
                }
            }
            matrix.add(row);
        }
        return new DirectOutputResult(new Matrix(matrix), maxInColumns);
    }
}
