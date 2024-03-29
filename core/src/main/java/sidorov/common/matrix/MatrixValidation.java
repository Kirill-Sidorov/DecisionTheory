package sidorov.common.matrix;

import java.util.List;

public class MatrixValidation {

    private final List<List<Double>> matrix;

    public MatrixValidation(List<List<Double>> matrix) {
        this.matrix = matrix;
    }

    public boolean validateMatrix() {
        if (matrix != null && !matrix.isEmpty()) {
            int firstRowLength = matrix.get(0).size();
            for (List<Double> row : matrix) {
                if (firstRowLength != row.size()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean validateMatrixWithSize(int rows, int columns) {
        if (matrix != null && !matrix.isEmpty()) {
            int firstRowLength = matrix.get(0).size();
            if (matrix.size() != rows && firstRowLength != columns) {
                return false;
            }
            for (List<Double> row : matrix) {
                if (firstRowLength != row.size()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean validateThatMatrix2x2Size() {
        if (validateMatrix()) {
            return matrix.size() == 2 && matrix.get(0).size() == 2;
        }
        return false;
    }

    public boolean validateThatMatrix2xNSize() {
        if (validateMatrix()) {
            return matrix.size() == 2;
        }
        return false;
    }

    public boolean validateThatMatrixNx2Size() {
        if (validateMatrix()) {
            return matrix.get(0).size() == 2;
        }
        return false;
    }

    public boolean validatePVector(List<Double> pVector) {
        return pVector != null && !pVector.isEmpty() && pVector.size() == matrix.size();
    }

    public boolean validateQVector(List<Double> qVector) {
        if (validateMatrix()) {
            int rowLength = matrix.get(0).size();
            return qVector != null && !qVector.isEmpty() && qVector.size() == rowLength;
        }
        return false;
    }
}
