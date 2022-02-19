package sidorov.lab1.steps;

import sidorov.common.Element;
import sidorov.common.Matrix;

import java.util.ArrayList;
import java.util.List;

public class FourthStep {

    private final Matrix matrix;

    public FourthStep(Matrix matrix) {
        this.matrix = matrix;
    }

    public List<Element> execute(List<Element> elementsX, List<Element> elementsY) {
        List<Element> saddlePoints = new ArrayList<>();
        for (Element elementX : elementsX) {
            for (Element elementY : elementsY) {
                int i = elementX.i;
                int j = elementY.j;
                saddlePoints.add(matrix.getElement(i, j));
            }
        }
        return saddlePoints;
    }
}
