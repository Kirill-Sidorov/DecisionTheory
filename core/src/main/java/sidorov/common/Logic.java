package sidorov.common;

public interface Logic {
    Result uploadData();
    Result solveTask();
    default Result setInputData(InputData data) {
        return new Result(Status.ERROR, "��� ������� ������");
    }
    default Result getChartData() {
        return new Result(Status.ERROR, "��� �������");
    }
}