package sidorov.common.result;

import sidorov.lab4.Function;

import java.util.List;

public class ResultWithChartData extends Result {

    public final List<Function> functions;

    public ResultWithChartData(String text, List<Function> functions) {
        super(Status.TASK_SOLVED, text);
        this.functions = functions;
    }
}
