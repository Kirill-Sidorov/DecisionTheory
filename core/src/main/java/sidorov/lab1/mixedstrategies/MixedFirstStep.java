package sidorov.lab1.mixedstrategies;

import sidorov.common.matrix.Element;
import sidorov.common.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class MixedFirstStep {

    private final Matrix matrix;

    public MixedFirstStep(Matrix matrix) {
        this.matrix = matrix;
    }

    public Element findV1() {
        List<Element> minRowElements = new ArrayList<>();

        for (int i = 0; i < matrix.numberRows; i++) {
            Element min = matrix.getElement(i, 0);
            for (int j = 0; j < matrix.numberColumns; j++) {
                if (min.value > matrix.get(i, j)) {
                    min = matrix.getElement(i, j);
                }
            }
            minRowElements.add(min);
        }

        Element maxElement = minRowElements.get(0);
        for (Element element : minRowElements) {
            if (maxElement.value < element.value) {
                maxElement = element;
            }
        }

        return maxElement;
    }

    public Element findV2() {
        List<Element> maxColumnElements = new ArrayList<>();

        for (int j = 0; j < matrix.numberColumns; j++) {
            Element max = matrix.getElement(0, j);
            for (int i = 0; i < matrix.numberRows; i++) {
                if (max.value < matrix.get(i, j)) {
                    max = matrix.getElement(i, j);
                }
            }
            maxColumnElements.add(max);
        }

        Element minElement = maxColumnElements.get(0);
        for (Element element : maxColumnElements) {
            if (minElement.value > element.value) {
                minElement = element;
            }
        }

        return minElement;
    }
}
