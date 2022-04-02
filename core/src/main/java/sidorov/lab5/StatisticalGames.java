package sidorov.lab5;

import sidorov.common.matrix.Element;
import sidorov.common.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class StatisticalGames {

    private final Matrix matrix;

    public StatisticalGames(final Matrix matrix) {
        this.matrix = matrix;
    }

    public Element findGamblerCriterion() {
        List<Element> maxElementInRows = new ArrayList<>();
        for (int i = 0; i < matrix.numberRows; i++) {
            Element maxInRow = matrix.getElement(i, 0);
            for (int j = 1; j < matrix.numberColumns; j++) {
                if (maxInRow.value < matrix.get(i, j)) {
                    maxInRow = matrix.getElement(i, j);
                }
            }
            maxElementInRows.add(maxInRow);
        }
        return getMaxElement(maxElementInRows);
    }

    public Element findMaximinCriterion() {
        List<Element> minElementInRows = new ArrayList<>();
        for (int i = 0; i < matrix.numberRows; i++) {
            Element minInRow = matrix.getElement(i, 0);
            for (int j = 1; j < matrix.numberColumns; j++) {
                if (minInRow.value > matrix.get(i, j)) {
                    minInRow = matrix.getElement(i, j);
                }
            }
            minElementInRows.add(minInRow);
        }
        return getMaxElement(minElementInRows);
    }

    public int findHurwitzCriterion(double alpha) {
        List<Double> listG = new ArrayList<>();
        for (int i = 0; i < matrix.numberRows; i++) {
            double minInRow = matrix.get(i, 0);
            double maxInRow = matrix.get(i, 0);
            for (int j = 1; j < matrix.numberColumns; j++) {
                if (minInRow > matrix.get(i, j)) {
                    minInRow = matrix.get(i, j);
                }
                if (maxInRow < matrix.get(i, j)) {
                    maxInRow = matrix.get(i, j);
                }
            }
            listG.add(alpha * minInRow + (1 - alpha) * maxInRow);
        }
        double maxValue = listG.get(0);
        int indexMaxValue = 0;
        for (int i = 1; i < listG.size(); i++) {
            if (maxValue < listG.get(i)) {
                maxValue = listG.get(i);
                indexMaxValue = i;
            }
        }
        return indexMaxValue + 1;
    }

    public SavageCriterionResult findSavageCriterion() {
        List<Double> maxValueInColumns = new ArrayList<>();
        for (int j = 0; j < matrix.numberColumns; j++) {
            double maxInColumn = matrix.get(0, j);
            for (int i = 1; i < matrix.numberRows; i++) {
                if (maxInColumn < matrix.get(i, j)) {
                    maxInColumn = matrix.get(i, j);
                }
            }
            maxValueInColumns.add(maxInColumn);
        }
        List<List<Double>> riskMatrixList = new ArrayList<>();
        for (int i = 0; i < matrix.numberRows; i++) {
            riskMatrixList.add(new ArrayList<>());
            for (int j = 0; j < matrix.numberColumns; j++) {
                double r = maxValueInColumns.get(j) - matrix.get(i, j);
                riskMatrixList.get(i).add(r);
            }
        }

        Matrix riskMatrix = new Matrix(riskMatrixList);
        List<Element> maxElementInRows = new ArrayList<>();
        for (int i = 0; i < riskMatrix.numberRows; i++) {
            Element maxInRow = riskMatrix.getElement(i, 0);
            for (int j = 1; j < riskMatrix.numberColumns; j++) {
                if (maxInRow.value < riskMatrix.get(i, j)) {
                    maxInRow = riskMatrix.getElement(i, j);
                }
            }
            maxElementInRows.add(maxInRow);
        }
        return new SavageCriterionResult(riskMatrix, getMinElement(maxElementInRows));
    }

    public Element getMaxElement(List<Element> elementList) {
        Element maxElement = elementList.get(0);
        for (Element element : elementList) {
            if (maxElement.value < element.value) {
                maxElement = element;
            }
        }
        return maxElement;
    }

    public Element getMinElement(List<Element> elementList) {
        Element minElement = elementList.get(0);
        for (Element element : elementList) {
            if (minElement.value > element.value) {
                minElement = element;
            }
        }
        return minElement;
    }
}
