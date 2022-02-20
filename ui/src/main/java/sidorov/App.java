package sidorov;

import sidorov.common.Logic;
import sidorov.common.result.Result;

import java.awt.event.ActionEvent;

public class App {

    private final UI UI;

    private Task currentTask;
    private Logic currentLogic;

    public App() {
        currentTask = Task.values()[0];
        UI = new UI(this::selectTask, this::uploadData, this::solveTask);
        UI.setFocusable(true);
        UI.setVisible(true);
    }

    private void selectTask(ActionEvent event) {
        currentTask = Task.valueOf(event.getActionCommand());
        UI.getSolveTaskButton().setEnabled(false);
    }

    private void uploadData(ActionEvent event) {
        currentLogic = currentTask.getLogic();
        processResult(currentLogic.uploadData());
        UI.getSolveTaskButton().setEnabled(true);
    }

    private void solveTask(ActionEvent event) {
        processResult(currentLogic.solveTask());
    }

    private void processResult(Result result) {
        switch (result.status) {
            case TASK_SOLVED:
                UI.getResultInfo().setText(result.text);
                break;
            case DATA_UPLOADED:
                UI.getTaskInfo().setText(result.text);
                break;
            case INFO:
                UI.showInfoMessage(result.text);
                break;
            case ERROR:
                UI.showErrorMessage(result.text);
                break;
        }
    }
}
