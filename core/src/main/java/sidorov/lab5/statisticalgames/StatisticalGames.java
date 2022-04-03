package sidorov.lab5.statisticalgames;

import org.apache.commons.math3.util.Precision;
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
        Element[] maxElementInRows = new Element[matrix.numberRows];
        for (int i = 0; i < matrix.numberRows; i++) {
            Element maxInRow = matrix.getElement(i, 0);
            for (int j = 1; j < matrix.numberColumns; j++) {
                if (maxInRow.value < matrix.get(i, j)) {
                    maxInRow = matrix.getElement(i, j);
                }
            }
            maxElementInRows[i] = maxInRow;
        }
        return getMaxElement(maxElementInRows);
    }

    public Element findMaximinCriterion() {
        Element[] minElementInRows = new Element[matrix.numberRows];
        for (int i = 0; i < matrix.numberRows; i++) {
            Element minInRow = matrix.getElement(i, 0);
            for (int j = 1; j < matrix.numberColumns; j++) {
                if (minInRow.value > matrix.get(i, j)) {
                    minInRow = matrix.getElement(i, j);
                }
            }
            minElementInRows[i] = minInRow;
        }
        return getMaxElement(minElementInRows);
    }

    public int findHurwitzCriterion(double alpha) {
        double[] listG = new double[matrix.numberRows];
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
            listG[i] = alpha * minInRow + (1 - alpha) * maxInRow;
        }
        double maxValue = listG[0];
        int indexMaxValue = 0;
        for (int i = 1; i < listG.length; i++) {
            if (maxValue < listG[i]) {
                maxValue = listG[i];
                indexMaxValue = i;
            }
        }
        return indexMaxValue + 1;
    }

    public SavageCriterionResult findSavageCriterion() {
        double[] maxValueInColumns = new double[matrix.numberColumns];
        for (int j = 0; j < matrix.numberColumns; j++) {
            double maxInColumn = matrix.get(0, j);
            for (int i = 1; i < matrix.numberRows; i++) {
                if (maxInColumn < matrix.get(i, j)) {
                    maxInColumn = matrix.get(i, j);
                }
            }
            maxValueInColumns[j] = maxInColumn;
        }
        List<List<Double>> riskMatrixList = new ArrayList<>();
        for (int i = 0; i < matrix.numberRows; i++) {
            riskMatrixList.add(new ArrayList<>());
            for (int j = 0; j < matrix.numberColumns; j++) {
                double r = maxValueInColumns[j] - matrix.get(i, j);
                riskMatrixList.get(i).add(r);
            }
        }

        Matrix riskMatrix = new Matrix(riskMatrixList);
        Element[] maxElementInRows = new Element[riskMatrix.numberRows];
        for (int i = 0; i < riskMatrix.numberRows; i++) {
            Element maxInRow = riskMatrix.getElement(i, 0);
            for (int j = 1; j < riskMatrix.numberColumns; j++) {
                if (maxInRow.value < riskMatrix.get(i, j)) {
                    maxInRow = riskMatrix.getElement(i, j);
                }
            }
            maxElementInRows[i] = maxInRow;
        }
        return new SavageCriterionResult(riskMatrix, getMinElement(maxElementInRows));
    }

    public ResultWithArrayAndStrategic findBayesCriterion(double[] p) {
        double[] MArray = new double[matrix.numberRows];
        for (int i = 0; i < matrix.numberRows; i++) {
            double M = 0;
            for (int j = 0; j < matrix.numberColumns; j++) {
                M += p[j] * matrix.get(i, j);
            }
            MArray[i] = Precision.round(M, 3);
        }

        double max = MArray[0];
        int indexMax = 0;
        for (int i = 1; i < MArray.length; i++) {
            if (max < MArray[i]) {
                max = MArray[i];
                indexMax = i;
            }
        }
        return new ResultWithArrayAndStrategic(MArray, indexMax + 1);
    }

    public ResultWithArrayAndStrategic findLaplaceCriterion() {
        double[] sumInRows = new double[matrix.numberRows];
        for (int i = 0; i < matrix.numberRows; i++) {
            double sum = 0;
            for (int j = 0; j < matrix.numberColumns; j++) {
                sum += matrix.get(i, j);
            }
            sumInRows[i] = Precision.round(sum, 3);
        }

        double max = sumInRows[0];
        int indexMax = 0;
        for (int i = 1; i < sumInRows.length; i++) {
            if (max < sumInRows[i]) {
                max = sumInRows[i];
                indexMax = i;
            }
        }
        return new ResultWithArrayAndStrategic(sumInRows, indexMax + 1);
    }

    public ResultWithArrayAndStrategic findHodgesLehmanCriterion(double[] p, double beta) {
        double[] LArray = new double[matrix.numberRows];
        for (int i = 0; i < matrix.numberRows; i++) {

            double M = 0;
            double min = matrix.get(i, 0);

            for (int j = 0; j < matrix.numberColumns; j++) {
                double value = matrix.get(i, j);
                M += p[j] * value;
                if (min > value) {
                    min = value;
                }
            }

            LArray[i] = Precision.round(beta * M + (1 - beta) * min, 3);
        }

        double max = LArray[0];
        int indexMax = 0;
        for (int i = 1; i < LArray.length; i++) {
            if (max < LArray[i]) {
                max = LArray[i];
                indexMax = i;
            }
        }
        return new ResultWithArrayAndStrategic(LArray, indexMax + 1);
    }

    public Element getMaxElement(Element[] elements) {
        Element maxElement = elements[0];
        for (Element element : elements) {
            if (maxElement.value < element.value) {
                maxElement = element;
            }
        }
        return maxElement;
    }

    public Element getMinElement(Element[] elements) {
        Element minElement = elements[0];
        for (Element element : elements) {
            if (minElement.value > element.value) {
                minElement = element;
            }
        }
        return minElement;
    }
}
