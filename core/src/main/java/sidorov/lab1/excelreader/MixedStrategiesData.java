package sidorov.lab1.excelreader;

import java.util.List;

public class MixedStrategiesData {

    public final List<List<Double>> matrix;
    public final List<Double> pVector;
    public final List<Double> qVector;

    public MixedStrategiesData(List<List<Double>> matrix, List<Double> pVector, List<Double> qVector) {
        this.matrix = matrix;
        this.pVector = pVector;
        this.qVector = qVector;
    }
}
