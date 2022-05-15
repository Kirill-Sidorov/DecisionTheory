package sidorov.mode;

import sidorov.app.UI;
import sidorov.common.Logic;
import sidorov.lab8.ReverseOutputMethodLogic;

public class ReverseOutputMethodMode extends Mode {

    public ReverseOutputMethodMode(UI UI) {
        super(UI);
    }

    @Override
    public Logic getLogic() {
        if (logic == null) {
            logic = new ReverseOutputMethodLogic();
        }
        return logic;
    }

    @Override
    public ModeType getModeType() {
        return ModeType.REVERSE_OUTPUT_METHOD;
    }
}
