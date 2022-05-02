package sidorov.mode;

import sidorov.app.UI;
import sidorov.common.Logic;
import sidorov.lab6.BellmanZadehLogic;

public class BellmanZadehMode extends Mode {

    public BellmanZadehMode(UI UI) {
        super(UI);
    }

    @Override
    public Logic getLogic() {
        if (logic == null) {
            logic = new BellmanZadehLogic();
        }
        return logic;
    }

    @Override
    public ModeType getModeType() {
        return ModeType.BELLMAN_ZADEH;
    }
}
