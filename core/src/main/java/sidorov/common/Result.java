package sidorov.common;

import sidorov.common.chart.ChartData;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private final Status status;
    private final String text;
    private final int number;

    private List<ChartData> chartDataList;

    public Result(Status status, String text) {
        this.status = status;
        this.text = text;
        this.number = 0;
    }

    public Result(Status status, String text, int number) {
        this.status = status;
        this.text = text;
        this.number = number;
    }

    public Result(List<ChartData> chartDataList) {
        this(Status.SUCCESS, "Данные для графиков получены успешно");
        this.chartDataList = chartDataList;
    }

    public Result(Status status, String text, List<ChartData> chartDataList) {
        this(status, text);
        this.chartDataList = chartDataList;
    }

    public Status status() {
        return status;
    }

    public String text() {
        return text;
    }

    public List<ChartData> chartDataList() {
        if ((chartDataList == null) || chartDataList.isEmpty()) {
            List<ChartData> list = new ArrayList<>();
            list.add(new ChartData(new ArrayList<>(), "", ""));
            chartDataList = list;
        }
        return chartDataList;
    }

    public int number() {
        return number;
    }
}
