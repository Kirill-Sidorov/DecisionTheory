package sidorov.lab2.reduction;

import java.util.List;

public class ReductionResult {
    public final List<Integer> deletedColumns;
    public final List<Integer> deletedRows;

    public ReductionResult(List<Integer> deletedColumns, List<Integer> deletedRows) {
        this.deletedColumns = deletedColumns;
        this.deletedRows = deletedRows;
    }
}
