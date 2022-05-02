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
import java.util.HashMap;
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
        return null;
    }
}
