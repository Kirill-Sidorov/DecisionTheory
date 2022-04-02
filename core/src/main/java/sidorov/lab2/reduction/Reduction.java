package sidorov.lab2.reduction;

import sidorov.common.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class Reduction {

    private final Matrix matrix;

    public Reduction(Matrix matrix) {
        this.matrix = matrix;
    }

    public ReductionResult perform(boolean isStrict) {
        CompareAction rowsCompare;
        CompareAction columnsCompare;

        if (isStrict) {
            rowsCompare = (row, i, j) -> matrix.get(row, j) > matrix.get(i, j);
            columnsCompare = (column, i, j) -> matrix.get(i, column) < matrix.get(i, j);
        } else {
            rowsCompare = (row, i, j) -> matrix.get(row, j) >= matrix.get(i, j);
            columnsCompare = (column, i, j) -> matrix.get(i, column) <= matrix.get(i, j);
        }

        List<Integer> deletedColumns = new ArrayList<>();
        List<Integer> deletedRows = new ArrayList<>();

        while (true) {
            boolean isContinue = false;

            for (int row = 0; row < matrix.numberRows; row++) {

                for (int i = 0; i < matrix.numberRows; i++) {

                    if (!deletedRows.contains(i) && row != i) {
                        boolean isNeedDeleteRow = true;
                        for (int j = 0; j < matrix.numberColumns; j++) {
                            if (!deletedColumns.contains(j) && !rowsCompare.compare(row, i, j)) {
                                isNeedDeleteRow = false;
                                break;
                            }
                        }
                        if (isNeedDeleteRow) {
                            deletedRows.add(i);
                            isContinue = true;
                            break;
                        }
                    }
                }
                if (isContinue) {
                    break;
                }
            }

            for (int column = 0; column < matrix.numberColumns; column++) {

                for (int j = 0; j < matrix.numberColumns; j++) {

                    if (!deletedColumns.contains(j) && column != j) {
                        boolean isNeedDeleteColumn = true;
                        for (int i = 0; i < matrix.numberRows; i++) {
                            if (!deletedRows.contains(i) && !columnsCompare.compare(column, i, j)) {
                                isNeedDeleteColumn = false;
                                break;
                            }
                        }
                        if (isNeedDeleteColumn) {
                            deletedColumns.add(j);
                            isContinue = true;
                            break;
                        }
                    }
                }
                if (isContinue) {
                    break;
                }
            }

            if (!isContinue) {
                break;
            }
        }
        return new ReductionResult(deletedColumns, deletedRows);
    }
}
