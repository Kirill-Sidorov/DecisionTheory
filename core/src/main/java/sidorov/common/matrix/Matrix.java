package sidorov.common.matrix;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    public final int numberRows;
    public final int numberColumns;
    private final List<List<Double>> matrix;

    public Matrix() {
        this.matrix = new ArrayList<>();
        List<Double> column = new ArrayList<>();
        column.add(0d);
        matrix.add(column);
        this.numberRows = 1;
        this.numberColumns = 1;
    }

    public Matrix(List<List<Double>> matrix) {
        this.matrix = matrix;
        this.numberRows = matrix.size();;
        this.numberColumns = matrix.get(0).size();
    }

    public double get(int i, int j) {
        return matrix.get(i).get(j);
    }

    public Element getElement(int i, int j) {
        return new Element(i, j, matrix.get(i).get(j));
    }

    public String toText() {
        StringBuilder stringBuilder = new StringBuilder();
        for (List<Double> row : matrix) {
            for (double value : row) {
                stringBuilder.append(String.format("%6.1f", value));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String toTextWithDeletedRowsAndColumns(List<Integer> deletedColumns, List<Integer> deletedRows) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numberRows; i++) {
            if (!deletedRows.contains(i)) {
                for (int j = 0; j < numberColumns; j++) {
                    if (!deletedColumns.contains(j)) {
                        stringBuilder.append(String.format("%6.1f", matrix.get(i).get(j)));
                    }
                }
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public String toTextAsTable(String[] rowNames, String[] columnNames) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%10s", ""));
        for (int i = 0; i < columnNames.length; i++) {
            stringBuilder.append(String.format("%10s", columnNames[i]));
        }
        stringBuilder.append("\n");
        for (int i = 0; i < numberRows; i++) {
            stringBuilder.append(String.format("%10s", rowNames[i]));
            for (int j = 0; j < numberColumns; j++) {
                stringBuilder.append(String.format("%10.3f", matrix.get(i).get(j)));
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
