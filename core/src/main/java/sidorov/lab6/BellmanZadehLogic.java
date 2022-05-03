package sidorov.lab6;

import sidorov.common.Logic;
import sidorov.common.Result;
import sidorov.common.Status;
import sidorov.common.jsonreader.JsonReader;
import sidorov.common.jsonreader.lab6.BellmanZadehPojo;
import sidorov.common.jsonreader.lab6.ComparisonVariant;
import sidorov.common.matrix.Matrix;
import sidorov.common.matrix.MatrixCreation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BellmanZadehLogic implements Logic {

    private String[] orderVariants = new String[]{""};
    private String[] orderCriteria = new String[]{""};
    private Map<String, Matrix> comparisonVariants = new HashMap<>();
    private Matrix comparisonCriteria = new Matrix();

    @Override
    public Result uploadData() {
        BellmanZadehPojo bellmanZadehPojo;
        try {
            bellmanZadehPojo = new JsonReader().loadBellmanZadehData();
        } catch (IOException e) {
            return new Result(Status.ERROR, "Ошибка при получении данных из json файла!");
        }

        orderVariants = bellmanZadehPojo.getVariants();
        orderCriteria = bellmanZadehPojo.getCriteria();
        comparisonVariants = new HashMap<>();

        MatrixCreation matrixCreation = new MatrixCreation(orderVariants);

        for (ComparisonVariant comparisonVariant : bellmanZadehPojo.getComparisonVariants()) {
            Matrix matrix = matrixCreation.createComparisonMatrix(comparisonVariant.getVariant(), comparisonVariant.getComparisons());
            if (matrix == null) {
                return new Result(Status.ERROR, "Не удалось создать матрицу для критерия - " + comparisonVariant.getCriterion());
            }
            comparisonVariants.put(comparisonVariant.getCriterion(), matrix);
        }

        matrixCreation = new MatrixCreation(orderCriteria);
        Matrix matrix = matrixCreation.createComparisonMatrix(
                bellmanZadehPojo.getComparisonCriteria().getCriterion(),
                bellmanZadehPojo.getComparisonCriteria().getComparisons());
        if (matrix == null) {
            return new Result(Status.ERROR, "Не удалось создать матрицу парных сравнений критериев");
        } else {
            comparisonCriteria = matrix;
        }

        StringBuilder result = new StringBuilder();
        for (String criterion : comparisonVariants.keySet()) {
            result.append(String.format("%s\n", criterion));
            result.append(comparisonVariants.get(criterion).toTextAsTable(orderVariants, orderVariants));
        }
        result.append("Матрица парных сравнений критериев:\n\n");
        result.append(comparisonCriteria.toTextAsTable(orderCriteria, orderCriteria));

        return new Result(Status.DATA_UPLOADED, result.toString());
    }

    @Override
    public Result solveTask() {
        // ранги матрицы парных сравнений
        double[] comparisonCriteriaRanks = new double[comparisonCriteria.numberColumns];
        for (int i = 0; i < comparisonCriteriaRanks.length; i++) {
            double sum = 0;
            for (int j = 0; j < comparisonCriteria.numberRows; j++) {
                sum += comparisonCriteria.get(j, i);
            }
            comparisonCriteriaRanks[i] = (double) 1 / sum;
        }

        // равновесны критерии
        List<List<Double>> equilibriumCriteria = new ArrayList<>();
        double[] minEquilibriumCriteria = new double[orderVariants.length];
        Arrays.fill(minEquilibriumCriteria, 1);

        for (String criterion : orderCriteria) {
            Matrix variantMatrix = comparisonVariants.get(criterion);
            List<Double> rowEquilibriumCriterion = new ArrayList<>();
            for (int j = 0; j < variantMatrix.numberColumns; j++) {
                double sum = 0;
                for (int i = 0; i < variantMatrix.numberRows; i++) {
                    sum += variantMatrix.get(i, j);
                }
                double value = (double) 1 / sum;
                if (value < minEquilibriumCriteria[j]) {
                    minEquilibriumCriteria[j] = value;
                }
                rowEquilibriumCriterion.add(value);
            }
            equilibriumCriteria.add(rowEquilibriumCriterion);
        }

        Matrix equilibriumCriteriaMatrix = new Matrix(equilibriumCriteria);

        // неравновесные критерии
        List<List<Double>> notEquilibriumCriteria = new ArrayList<>();
        double[] minNotEquilibriumCriteria = new double[orderVariants.length];
        Arrays.fill(minNotEquilibriumCriteria, 1);

        for (int i = 0; i < equilibriumCriteriaMatrix.numberRows; i++) {
            List<Double> notEquilibriumCriteriaRow = new ArrayList<>();
            for (int j = 0; j < equilibriumCriteriaMatrix.numberColumns; j++) {
                double value = Math.pow(equilibriumCriteriaMatrix.get(i, j), comparisonCriteriaRanks[i]);
                if (value < minNotEquilibriumCriteria[j]) {
                    minNotEquilibriumCriteria[j] = value;
                }
                notEquilibriumCriteriaRow.add(value);
            }
            notEquilibriumCriteria.add(notEquilibriumCriteriaRow);
        }

        Matrix notEquilibriumCriteriaMatrix = new Matrix(notEquilibriumCriteria);

        StringBuilder result = new StringBuilder();
        result.append("Ранги матрицы парных сравений критериев\n");
        for (String orderCriterion : orderCriteria) {
            result.append(String.format("%10s", orderCriterion));
        }
        result.append("\n");
        for (double rank : comparisonCriteriaRanks) {
            result.append(String.format("%10.3f", rank));
        }

        result.append("\n\nМатрица равновесных критериев:\n");
        result.append(equilibriumCriteriaMatrix.toTextAsTable(orderCriteria, orderVariants));
        result.append(String.format("%10s", "Мин"));
        for (double min : minEquilibriumCriteria) {
            result.append(String.format("%10.3f", min));
        }
        result.append(String.format("\n%10s", "Ранги"));
        for (int rank: findRanks(minEquilibriumCriteria)) {
            result.append(String.format("%10d", rank));
        }

        result.append("\n\nМатрица неравновесных критериев:\n");
        result.append(notEquilibriumCriteriaMatrix.toTextAsTable(orderCriteria, orderVariants));
        result.append(String.format("%10s", "Мин"));
        for (double min : minNotEquilibriumCriteria) {
            result.append(String.format("%10.3f", min));
        }
        result.append(String.format("\n%10s", "Ранги"));
        for (int rank: findRanks(minNotEquilibriumCriteria)) {
            result.append(String.format("%10d", rank));
        }

        return new Result(Status.SUCCESS, result.toString());
    }

    private int[] findRanks(double[] array) {
        int[] result = new int[array.length];
        Map<Double, Integer> valueIndexMap = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            valueIndexMap.put(array[i], i);
        }
        Arrays.sort(array);
        double lastValue = array[0];
        int lastRank = 1;
        for (int i = array.length - 1; i >= 0; i--) {
            int valueIndex = valueIndexMap.get(array[i]);
            if (lastValue > array[i]) {
                result[valueIndex] = lastRank;
            } else {
                lastValue = array[i];
                result[valueIndex] = lastRank++;
            }
        }
        return result;
    }
}
