package sidorov.common.chart;

import java.util.List;

public class ChartData {

    public final List<Function> functions;
    public final String xAxisName;
    public final String yAxisName;
    public final String title;

    public ChartData(List<Function> functions, String xAxisName, String yAxisName) {
        this.functions = functions;
        this.xAxisName = xAxisName;
        this.yAxisName = yAxisName;
        this.title = "";
    }

    public ChartData(List<Function> functions, String xAxisName, String yAxisName, String title) {
        this.functions = functions;
        this.xAxisName = xAxisName;
        this.yAxisName = yAxisName;
        this.title = title;
    }
}
