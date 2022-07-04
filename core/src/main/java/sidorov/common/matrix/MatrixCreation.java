package sidorov.common.matrix;

import org.apache.commons.math3.util.Precision;
import sidorov.common.jsonreader.lab6.Comparison;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixCreation {

    private final String[] elementsOrder;
    private final int MATRIX_SIZE;

    public MatrixCreation(String[] elements) {
        this.elementsOrder = elements;
        this.MATRIX_SIZE = elements.length;
    }

    public Matrix createComparisonMatrix(String variant, Comparison[] comparisons) {

        if (comparisons.length != MATRIX_SIZE - 1) {
            return null;
        }

        Map<String, String> comparisonMap = new HashMap<>();
        for (Comparison comparison : comparisons) {
            comparisonMap.put(comparison.getName(), comparison.getValue());
        }

        int knownRowNumber = 0;
        List<Double> knownRow = new ArrayList<>();
        for (int i = 0; i < elementsOrder.length; i++) {
            if (elementsOrder[i].equals(variant)) {
                knownRow.add(1d);
                knownRowNumber = i;
            } else if (comparisonMap.containsKey(elementsOrder[i])) {
                String stringValue = comparisonMap.remove(elementsOrder[i]);

                double value;
                if (stringValue.contains("/")) {
                    String[] fraction = stringValue.split("/");
                    if (fraction.length > 1) {
                        try {
                            int numerator = Integer.parseInt(fraction[0]);
                            int denominator = Integer.parseInt(fraction[1]);
                            value = Precision.round((double) numerator / denominator, 3);
                        } catch (NumberFormatException e) {
                            return null;
                        }
                    } else {
                        return null;
                    }
                } else {
                    try {
                        value = Precision.round(Double.parseDouble(stringValue), 3);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }

                knownRow.add(value);
            }
        }

        if ((knownRow.size() != MATRIX_SIZE) && !comparisonMap.isEmpty()) {
            return null;
        }

        List<List<Double>> matrix = new ArrayList<>();
        for (int i = 0; i < MATRIX_SIZE; i++) {
            if (knownRowNumber != i) {
                List<Double> row = new ArrayList<>();
                for (int j = 0; j < MATRIX_SIZE; j++) {
                    double value = (i == j) ? 1 : (double) knownRow.get(j) / knownRow.get(i);
                    row.add(value);
                }
                matrix.add(row);
            } else {
                matrix.add(knownRow);
            }
        }

        return new Matrix(matrix);
    }
}
