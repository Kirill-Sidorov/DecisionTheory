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
    public abstract ModeType getModeType();

    public void setNecessaryUIItems() {
        UI.createChart.setVisible(false);
        UI.textField1.setVisible(false);
        UI.textField2.setVisible(false);
        UI.createChart.setEnabled(false);
        UI.textField1.setEnabled(false);
        UI.textField2.setEnabled(false);
        UI.labelTextField1.setVisible(false);
        UI.labelTextField2.setVisible(false);

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
}
