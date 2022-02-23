package sidorov;

import sidorov.common.Logic;
import sidorov.lab1.MixedStrategiesLogic;
import sidorov.lab1.PureStrategiesLogic;
import sidorov.lab2.ReductionLogic;

public enum Mode {
    PURE_STRATEGIES("������ ���������") {
        @Override
        public Logic getLogic() {
            return new PureStrategiesLogic();
        }
    },
    MIXED_STRATEGIES("��������� ���������") {
        @Override
        public Logic getLogic() {
            return new MixedStrategiesLogic();
        }
    },
    REDUCTION("��������") {
        @Override
        public Logic getLogic() {
            return new ReductionLogic();
        }
    };

    private final String text;

    Mode(String text) {
        this.text = text;
    }

    public abstract Logic getLogic();

    public String text() {
        return text;
    }
}
