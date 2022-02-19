package sidorov.common;

public class Matrix {
    private final int[][] matrix;

    public Matrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int get(int i, int j) {
        return matrix[i][j];
    }

    public int rowsNumber() {
        return matrix.length;
    }

    public int columnsNumber() {
        return matrix[0].length;
    }

    public Element getElement(int i, int j) {
        return new Element(i, j, matrix[i][j]);
    }
}
