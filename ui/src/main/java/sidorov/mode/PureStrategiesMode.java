package sidorov.mode;

import sidorov.app.UI;
import sidorov.common.Logic;
import sidorov.lab1.PureStrategiesLogic;

public class PureStrategiesMode extends Mode {

    private Logic logic;

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
}
