package sidorov.common;

public interface Logic {
    Result uploadData();
    Result solveTask();
    default Result setInputData(InputData data) {
        return new Result(Status.ERROR, "Без входных данных");
    }
    default Result getChartData() {
        return new Result(Status.ERROR, "Без графика");
    }
}