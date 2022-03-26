package sidorov.mode;

public enum ModeType {
    PURE_STRATEGIES("������ ���������"),
    MIXED_STRATEGIES("��������� ���������"),
    REDUCTION("��������"),
    SOLUTION_MATRIX_GAME_2x2("���. ����. ���� 2x2"),
    SOLUTION_MATRIX_GAME_2xN_OR_Nx2("���. ����. ���� 2xN/Nx2");

    public final String text;

    ModeType(String text) {
        this.text = text;
    }
}