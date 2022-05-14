package sidorov.lab7.directoutputmethod;

import sidorov.common.matrix.Element;
import sidorov.common.matrix.Matrix;

public class DirectOutputResult {
    public final Matrix matrix;
    public final Element[] maxInColumns;

    public DirectOutputResult(Matrix matrix, Element[] maxInColumns) {
        this.matrix = matrix;
        this.maxInColumns = maxInColumns;
    }
}
