package sidorov.lab5.statisticalgames;

import sidorov.common.matrix.Element;
import sidorov.common.matrix.Matrix;

public class SavageCriterionResult {
    public final Matrix riskMatrix;
    public final Element strategicElement;

    public SavageCriterionResult(Matrix riskMatrix, Element strategicElement) {
        this.riskMatrix = riskMatrix;
        this.strategicElement = strategicElement;
    }
}
