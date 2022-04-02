package sidorov.mode;

public enum ModeType {
    PURE_STRATEGIES("Чистые стратегии"),
    MIXED_STRATEGIES("Смешанные стратегии"),
    REDUCTION("Редукция"),
    SOLUTION_MATRIX_GAME_2x2("Реш. матр. игры 2x2"),
    SOLUTION_MATRIX_GAME_2xN_OR_Nx2("Реш. матр. игры 2xN/Nx2"),
    STATISTICAL_GAMES("Статистические игры");

    public final String text;

    ModeType(String text) {
        this.text = text;
    }
}
