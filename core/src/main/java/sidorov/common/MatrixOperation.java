package sidorov.common;

import java.util.ArrayList;
import java.util.List;

public class MatrixOperation {

    private final Matrix matrix;

    public MatrixOperation(Matrix matrix) {
        this.matrix = matrix;
    }

    public Matrix transpose() {
        List<List<Double>> transposedMatrix = new ArrayList<>();
        for (int column = 0; column < matrix.numberColumns; column++) {
            transposedMatrix.add(new ArrayList<>());
        }
        for (int i = 0; i < matrix.numberRows; i++) {
            for (int j = 0; j < matrix.numberColumns; j++) {
                transposedMatrix.get(j).add(matrix.get(i, j));
            }
        }
        return new Matrix(transposedMatrix);
    }

    public Matrix transposeAndChangeSign() {
        List<List<Double>> transposedMatrix = new ArrayList<>();
        for (int column = 0; column < matrix.numberColumns; column++) {
            transposedMatrix.add(new ArrayList<>());
        }
        for (int i = 0; i < matrix.numberRows; i++) {
            for (int j = 0; j < matrix.numberColumns; j++) {
                transposedMatrix.get(j).add(matrix.get(i, j) * -1);
            }
        }
        return new Matrix(transposedMatrix);
    }
}
