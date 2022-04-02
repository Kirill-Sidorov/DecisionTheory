package sidorov.mode;

import sidorov.app.UI;
import sidorov.common.Logic;
import sidorov.common.result.Result;
import sidorov.lab4.SolutionMatrixGame2xNorNx2Logic;

public class SolutionMatrixGame2xNorNx2Mode extends Mode {

    private Logic logic;

    public SolutionMatrixGame2xNorNx2Mode(UI UI) {
        super(UI);
    }

    @Override
    public Logic getLogic() {
        if (logic == null) {
            logic = new SolutionMatrixGame2xNorNx2Logic();
        }
        return logic;
    }

    @Override
    public ModeType getModeType() {
        return ModeType.SOLUTION_MATRIX_GAME_2xN_OR_Nx2;
    }

    @Override
    public void setNecessaryUIItems() {
        UI.solveTaskButton.setEnabled(false);
        UI.createChart.setVisible(true);
        UI.textField1.setVisible(true);
        UI.textField2.setVisible(true);
        UI.labelTextField1.setVisible(true);
        UI.labelTextField2.setVisible(true);
    }

    @Override
    public void handleDataUploaded(Result result) {
        super.handleDataUploaded(result);

        UI.createChart.setEnabled(true);
        UI.textField1.setEnabled(true);
        UI.textField2.setEnabled(true);
    }

    @Override
    public void handleError(Result result) {
        super.handleError(result);

        UI.createChart.setEnabled(false);
        UI.textField1.setEnabled(false);
        UI.textField2.setEnabled(false);
    }
}
