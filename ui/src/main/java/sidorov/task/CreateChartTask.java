package sidorov.task;

import sidorov.app.ChartFrame;
import sidorov.common.Logic;
import sidorov.common.WithChart;
import sidorov.common.result.Result;
import sidorov.common.result.ResultWithChartData;
import sidorov.common.result.Status;

import javax.swing.SwingWorker;

public class CreateChartTask extends SwingWorker<ResultWithChartData, Void> {

    private final Logic logic;
    private final ResultAction errorAction;

    public CreateChartTask(Logic logic, ResultAction errorAction) {
        this.logic = logic;
        this.errorAction = errorAction;
    }

    @Override
    protected ResultWithChartData doInBackground() {
        if (logic instanceof WithChart) {
            return ((WithChart) logic).getChartData();
        }
        return new ResultWithChartData(Status.ERROR, "Error");
    }

    @Override
    protected void done() {
        try {
            ResultWithChartData result = get();
            if (result.status == Status.TASK_SOLVED) {
                new ChartFrame(get().functions);
            }
        } catch (Exception ignored) {
            errorAction.processResult(new Result(Status.ERROR, "Ошибка при построении графика"));
        }
    }
}
