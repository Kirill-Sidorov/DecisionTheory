package sidorov.mode;

import sidorov.app.UI;
import sidorov.common.Logic;
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
    public void setNecessaryUIItems() {
        UI.createChart.setVisible(true);
        UI.textField1.setVisible(true);
        UI.textField2.setVisible(true);
    }
}
