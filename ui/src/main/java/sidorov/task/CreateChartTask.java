package sidorov.task;

import sidorov.customcomponents.ChartFrame;
import sidorov.common.Logic;
import sidorov.common.Result;
import sidorov.common.Status;

import javax.swing.SwingWorker;

public class CreateChartTask extends SwingWorker<Result, Void> {

    private final Logic logic;
    private final ResultAction errorAction;

    public CreateChartTask(Logic logic, ResultAction errorAction) {
        this.logic = logic;
        this.errorAction = errorAction;
    }

    @Override
    protected Result doInBackground() {
        return logic.getChartData();
    }

    @Override
    protected void done() {
        try {
            Result result = get();
            if (result.status() == Status.SUCCESS) {
                result.chartDataList().forEach(ChartFrame::new);
            }
        } catch (Exception ignored) {
            errorAction.processResult(new Result(Status.ERROR, "Ошибка при построении графика"));
        }
    }
}
