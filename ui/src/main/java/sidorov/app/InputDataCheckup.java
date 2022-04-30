package sidorov.app;

import org.apache.commons.math3.util.Precision;
import sidorov.common.InputData;
import sidorov.common.Logic;
import sidorov.common.Result;
import sidorov.common.Status;
import sidorov.mode.Mode;

import java.util.Arrays;

public class InputDataCheckup {
    private final UI UI;

    public InputDataCheckup(final UI UI) {
        this.UI = UI;
    }

    public boolean validateDataAndInput(final Mode mode) {
        switch (mode.getModeType()) {
            case SOLUTION_MATRIX_GAME_2xN_OR_Nx2:
                return inputDataForMatrixGame2xNorNx2(mode.getLogic());
            case STATISTICAL_GAMES:
                return inputDataForStatisticalGamesLogic(mode.getLogic());
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
            UI.showErrorMessage("Значения в полях ввода должны быть целочисленными!");
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

    private boolean inputDataForStatisticalGamesLogic(final Logic logic) {
        int alphaValue = UI.alphaSlider.getValue();
        int betaValue = UI.betaSlider.getValue();
        String[] stringPValues = UI.variableTextFieldsProbabilityPanel.getValues();
        String[] stringRanks = UI.variableTextFieldsRankPanel.getValues();
        double[] pValues = new double[stringPValues.length];
        int[] ranks = new int[stringRanks.length];
        try {
            for (int i = 0; i < pValues.length; i++) {
                pValues[i] = Precision.round(Double.parseDouble(stringPValues[i]), 3);
            }
        } catch (NumberFormatException e) {
            UI.showErrorMessage("Не удалось получить значение из поля ввода вероятности P!");
            return false;
        }
        if (Precision.round(Arrays.stream(pValues).sum(), 3) != 1) {
            UI.showErrorMessage("Сумма вероятностей не равна 1");
            return false;
        }

        try {
            for (int i = 0; i < ranks.length; i++) {
                ranks[i] = Integer.parseInt(stringRanks[i]);
                if (ranks[i] < 1) {
                    UI.showErrorMessage("Значение ранга должно быть больше 0!");
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            UI.showErrorMessage("Не удалось получить значение из поля ввода рангов R!");
            return false;
        }


        InputData inputData = new InputData(Precision.round(alphaValue * 0.001, 3),
                Precision.round(betaValue * 0.001, 3),
                pValues,
                ranks);
        Result result = logic.setInputData(inputData);
        if (result.status() != Status.SUCCESS) {
            UI.showErrorMessage(result.text());
            return false;
        } else {
            return true;
        }
    }
}
