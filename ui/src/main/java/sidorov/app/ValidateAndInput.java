package sidorov.app;

import sidorov.common.WithInputData;
import sidorov.common.inputdata.InputDataForMatrixGame2xNorNx2;
import sidorov.common.result.Result;
import sidorov.common.result.Status;

public class ValidateAndInput {

    private final UI UI;

    public ValidateAndInput(final UI UI) {
        this.UI = UI;
    }

    public boolean inputDataForMatrixGame2xNorNx2(WithInputData logic) {
        String text1 = UI.textField1.getText();
        String text2 = UI.textField2.getText();
        int l;
        int s;
        try {
            l = Integer.parseInt(text1);
            s = Integer.parseInt(text2);
        } catch (NumberFormatException e) {
            UI.showErrorMessage("Значения в полях ввода должны быть целочисленными!");
            return false;
        }

        Result result = logic.setInputData(new InputDataForMatrixGame2xNorNx2(l, s));
        if (result.status != Status.SUCCESS) {
            UI.showErrorMessage(result.text);
            return false;
        } else {
            return true;
        }
    }
}
