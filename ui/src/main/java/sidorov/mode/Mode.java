package sidorov.mode;

import sidorov.app.UI;
import sidorov.common.Logic;
import sidorov.common.result.Result;

public abstract class Mode {
    protected final UI UI;

    public Mode(final UI UI) {
        this.UI = UI;
    }

    public abstract Logic getLogic();

    public void setNecessaryUIItems() {
        resetUIItems();
        UI.solveTaskButton.setEnabled(false);
    }

    public void handleTaskSolved(Result result) {
        UI.resultText.setText(result.text);
    }

    public void handleDataUploaded(Result result) {
        UI.initialDataText.setText(result.text);
        UI.resultText.setText("");
        UI.solveTaskButton.setEnabled(true);
    }

    public void handleInfo(Result result) {
        UI.showInfoMessage(result.text);
    }

    public void handleError(Result result) {
        UI.showErrorMessage(result.text);
        UI.solveTaskButton.setEnabled(false);
        UI.initialDataText.setText("");
    }

    protected void resetUIItems() {
        UI.createChart.setVisible(false);
        UI.textField1.setVisible(false);
        UI.textField2.setVisible(false);
    }
}
