package sidorov.mode;

import sidorov.app.UI;
import sidorov.common.Logic;
import sidorov.lab2.ReductionLogic;

public class ReductionMode extends Mode {

    private Logic logic;

    public ReductionMode(UI UI) {
        super(UI);
    }

    @Override
    public Logic getLogic() {
        if (logic == null) {
            logic = new ReductionLogic();
        }
        return logic;
    }
}
