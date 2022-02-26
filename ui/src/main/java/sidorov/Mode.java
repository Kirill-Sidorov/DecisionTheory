package sidorov;

import sidorov.common.Logic;
import sidorov.lab1.MixedStrategiesLogic;
import sidorov.lab1.PureStrategiesLogic;
import sidorov.lab2.ReductionLogic;
import sidorov.lab3.AnalyticalSolutionMatrixGame2x2Logic;

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
    },
    ANALYTICAL_SOLUTION_MATRIX_GAME_2X2("������� ��������� ���� 2x2") {
        @Override
        public Logic getLogic() {
            return new AnalyticalSolutionMatrixGame2x2Logic();
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
