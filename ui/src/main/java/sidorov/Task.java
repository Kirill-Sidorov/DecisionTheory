package sidorov;

import sidorov.common.Logic;
import sidorov.lab1.MixedStrategiesLogic;
import sidorov.lab1.PureStrategiesLogic;

public enum Task {
    PURE_STRATEGIES("Чистые стратегии") {
        @Override
        public Logic getLogic() {
            return new PureStrategiesLogic();
        }
    },
    MIXED_STRATEGIES("Смешанные стратегии"){
        @Override
        public Logic getLogic() {
            return new MixedStrategiesLogic();
        }
    };

    private final String text;

    Task(String text) {
        this.text = text;
    }

    public abstract Logic getLogic();
    public String text() {
        return text;
    }
}
