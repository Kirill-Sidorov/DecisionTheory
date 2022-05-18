package sidorov.lab7.directoutputmethod;

import sidorov.common.matrix.Matrix;

public class DirectOutputResult {
    public final Matrix matrix;
    public final boolean[][] scores;

    public DirectOutputResult(Matrix matrix, boolean[][] scores) {
        this.matrix = matrix;
        this.scores = scores;
    }
}
