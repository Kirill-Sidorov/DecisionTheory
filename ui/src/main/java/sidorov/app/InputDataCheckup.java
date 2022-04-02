package sidorov.app;

import sidorov.common.InputData;
import sidorov.common.Logic;
import sidorov.common.Result;
import sidorov.common.Status;
import sidorov.mode.Mode;

public class InputDataCheckup {
    private final UI UI;

    public InputDataCheckup(final UI UI) {
        this.UI = UI;
    }

    public boolean validateDataAndInput(final Mode mode) {
        switch (mode.getModeType()) {
            case SOLUTION_MATRIX_GAME_2xN_OR_Nx2:
                return inputDataForMatrixGame2xNorNx2(mode.getLogic());
        }
        return true;
    }

    private boolean inputDataForMatrixGame2xNorNx2(final Logic logic) {
        int l;
        int s;
        String text1 = UI.textField1.getText();
        String text2 = UI.textField2.getText();
        try {
            l = Integer.parseInt(text1);
            s = Integer.parseInt(text2);
        } catch (NumberFormatException e) {
            UI.showErrorMessage("�������� � ����� ����� ������ ���� ��������������!");
            return false;
        }

        Result result = logic.setInputData(new InputData(l, s));
        if (result.status() != Status.SUCCESS) {
            UI.showErrorMessage(result.text());
            return false;
        } else {
            return true;
        }
    }
}