package sidorov;

import sidorov.common.Logic;
import sidorov.common.result.Result;
import sidorov.task.SolveTask;
import sidorov.task.UploadTask;

import java.awt.event.ActionEvent;

public class App {

    private final UI UI;

    private Mode currentMode;
    private Logic currentLogic;

    public App() {
        currentMode = Mode.values()[0];
        UI = new UI(this::selectMode, this::uploadData, this::solveTask);
        UI.setFocusable(true);
        UI.setVisible(true);
    }

    private void selectMode(ActionEvent event) {
        currentMode = Mode.valueOf(event.getActionCommand());
        UI.solveTaskButton.setEnabled(false);
    }

    private void uploadData(ActionEvent event) {
        currentLogic = currentMode.getLogic();
        new UploadTask(currentLogic, this::processResult).execute();
    }

    private void solveTask(ActionEvent event) {
        new SolveTask(currentLogic, this::processResult).execute();
    }

    private void processResult(Result result) {
        switch (result.status) {
            case TASK_SOLVED:
                UI.resultText.setText(result.text);
                break;
            case DATA_UPLOADED:
                UI.initialDataText.setText(result.text);
                UI.resultText.setText("");
                UI.solveTaskButton.setEnabled(true);
                break;
            case INFO:
                UI.showInfoMessage(result.text);
                break;
            case ERROR:
                UI.showErrorMessage(result.text);
                UI.solveTaskButton.setEnabled(false);
                UI.initialDataText.setText("");
                break;
        }
    }
}
