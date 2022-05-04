package sidorov.mode;

import sidorov.app.UI;
import sidorov.common.Logic;
import sidorov.common.Result;
import sidorov.customcomponents.ChartFrame;
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

    @Override
    public void handleTaskSolved(Result result) {
        super.handleTaskSolved(result);
        result.chartDataList().forEach(ChartFrame::new);
    }
}
