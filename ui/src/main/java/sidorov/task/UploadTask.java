package sidorov.task;

import sidorov.common.Logic;
import sidorov.common.result.Result;
import sidorov.common.result.Status;

import javax.swing.SwingWorker;

public class UploadTask extends SwingWorker<Result, Void> {

    private final Logic logic;
    private final ResultAction action;

    public UploadTask(Logic logic, ResultAction action) {
        this.logic = logic;
        this.action = action;
    }

    @Override
    protected Result doInBackground() {
        return logic.uploadData();
    }

    @Override
    protected void done() {
        try {
            action.processResult(get());
        } catch (Exception e) {
            action.processResult(new Result(Status.ERROR, "Ошибка при выполнении задачи"));
        }
    }
}
