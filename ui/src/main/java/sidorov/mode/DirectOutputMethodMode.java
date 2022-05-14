package sidorov.mode;

import sidorov.app.UI;
import sidorov.common.Logic;
import sidorov.lab7.DirectOutputMethodLogic;

public class DirectOutputMethodMode extends Mode {

    public DirectOutputMethodMode(UI UI) {
        super(UI);
    }

    @Override
    public Logic getLogic() {
        if (logic == null) {
            logic = new DirectOutputMethodLogic();
        }
        return logic;
    }

    @Override
    public ModeType getModeType() {
        return ModeType.DIRECT_OUTPUT_METHOD;
    }
}
