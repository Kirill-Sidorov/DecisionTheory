package sidorov;

import sidorov.common.Logic;
import sidorov.lab1.MixedStrategiesLogic;
import sidorov.lab1.PureStrategiesLogic;
import sidorov.lab2.ReductionLogic;
import sidorov.lab3.SolutionMatrixGame2x2Logic;
import sidorov.lab4.SolutionMatrixGame2xNLogic;

public enum Mode {
    PURE_STRATEGIES("Чистые стратегии") {
        @Override
        public Logic getLogic() {
            return new PureStrategiesLogic();
        }
    },
    MIXED_STRATEGIES("Смешанные стратегии") {
        @Override
        public Logic getLogic() {
            return new MixedStrategiesLogic();
        }
    },
    REDUCTION("Редукция") {
        @Override
        public Logic getLogic() {
            return new ReductionLogic();
        }
    },
    SOLUTION_MATRIX_GAME_2X2("Решение матр. игры 2x2") {
        @Override
        public Logic getLogic() {
            return new SolutionMatrixGame2x2Logic();
        }
    },
    SOLUTION_MATRIX_GAME_2XN("Решение матр. игры 2xN") {
        @Override
        public Logic getLogic() {
            return new SolutionMatrixGame2xNLogic();
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
