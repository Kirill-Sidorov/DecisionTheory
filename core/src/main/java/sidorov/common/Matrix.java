package sidorov.common;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private final List<List<Double>> matrix;

    public Matrix() {
        this.matrix = new ArrayList<>();
        matrix.add(new ArrayList<>());
    }

    public Matrix(List<List<Double>> matrix) {
        this.matrix = matrix;
    }

    public double get(int i, int j) {
        return matrix.get(i).get(j);
    }

    public int numberRows() {
        return matrix.size();
    }

    public int numberColumns() {
        return matrix.get(0).size();
    }

    public Element getElement(int i, int j) {
        return new Element(i, j, matrix.get(i).get(j));
    }

    public String toText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Матрица:\n");
        for (List<Double> row : matrix) {
            stringBuilder.append("[");
            for (double value : row) {
                stringBuilder.append(String.format("%5.1f", value));
            }
            stringBuilder.append("  ]\n");
        }
        return stringBuilder.toString();
    }
}
