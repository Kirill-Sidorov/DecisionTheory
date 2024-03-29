package sidorov.mode;

import sidorov.app.UI;
import sidorov.common.Logic;
import sidorov.lab1.MixedStrategiesLogic;

public class MixedStrategiesMode extends Mode {

    public MixedStrategiesMode(UI UI) {
        super(UI);
    }

    @Override
    public Logic getLogic() {
        if (logic == null) {
            logic = new MixedStrategiesLogic();
        }
        return logic;
    }

    @Override
    public ModeType getModeType() {
        return ModeType.MIXED_STRATEGIES;
    }
}
