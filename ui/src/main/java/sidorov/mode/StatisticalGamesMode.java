package sidorov.mode;

import sidorov.app.UI;
import sidorov.common.Logic;
import sidorov.lab5.StatisticalGamesLogic;

public class StatisticalGamesMode extends Mode {

    public StatisticalGamesMode(UI UI) {
        super(UI);
    }

    @Override
    public Logic getLogic() {
        if (logic == null) {
            logic = new StatisticalGamesLogic();
        }
        return logic;
    }

    @Override
    public ModeType getModeType() {
        return ModeType.STATISTICAL_GAMES;
    }

    @Override
    public void setNecessaryUIItems() {
        super.setNecessaryUIItems();
        UI.alphaSlider.setVisible(true);
        UI.alphaSliderLabel.setVisible(true);
        UI.betaSlider.setVisible(true);
        UI.betaSliderLabel.setVisible(true);
    }
}
