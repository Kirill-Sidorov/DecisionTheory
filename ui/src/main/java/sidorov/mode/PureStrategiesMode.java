package sidorov.mode;

import sidorov.app.UI;
import sidorov.common.Logic;
import sidorov.lab1.PureStrategiesLogic;

public class PureStrategiesMode extends Mode {

    public PureStrategiesMode(UI UI) {
        super(UI);
    }

    @Override
    public Logic getLogic() {
        if (logic == null) {
            logic = new PureStrategiesLogic();
        }
        return logic;
    }

    @Override
    public ModeType getModeType() {
        return ModeType.PURE_STRATEGIES;
    }
}
