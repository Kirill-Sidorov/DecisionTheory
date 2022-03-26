package sidorov.common.result;

import sidorov.lab4.Function;

import java.util.ArrayList;
import java.util.List;

public class ResultWithChartData extends Result {

    public final List<Function> functions;

    public ResultWithChartData(Status status, String text) {
        super(status, text);
        this.functions = new ArrayList<>();
    }

    public ResultWithChartData(String text, List<Function> functions) {
        super(Status.SUCCESS, text);
        this.functions = functions;
    }
}
