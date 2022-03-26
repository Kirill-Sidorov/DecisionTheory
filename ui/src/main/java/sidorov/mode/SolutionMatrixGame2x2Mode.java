package sidorov.mode;

import sidorov.app.UI;
import sidorov.common.Logic;
import sidorov.lab3.SolutionMatrixGame2x2Logic;

public class SolutionMatrixGame2x2Mode extends Mode {

    private Logic logic;

    public SolutionMatrixGame2x2Mode(UI UI) {
        super(UI);
    }

    @Override
    public Logic getLogic() {
        if (logic == null) {
            logic = new SolutionMatrixGame2x2Logic();
        }
        return logic;
    }

    @Override
    public ModeType getModeType() {
        return ModeType.SOLUTION_MATRIX_GAME_2x2;
    }
}
