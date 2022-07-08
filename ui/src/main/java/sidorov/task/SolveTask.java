package sidorov.task;

import sidorov.common.Logic;
import sidorov.common.Result;
import sidorov.common.Status;

import javax.swing.*;

public class SolveTask extends SwingWorker<Result, Void> {

    private final Logic logic;
    private final ResultAction action;

    public SolveTask(Logic logic, ResultAction action) {
        this.logic = logic;
        this.action = action;
    }

    @Override
    protected Result doInBackground() {
        return logic.solveTask();
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
