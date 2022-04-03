package sidorov.lab5;

import sidorov.common.Logic;
import sidorov.common.Result;
import sidorov.common.Status;
import sidorov.common.excelreader.ExcelReader;
import sidorov.common.excelreader.SheetNotFoundException;
import sidorov.common.excelreader.TaskSheet;
import sidorov.common.matrix.Element;
import sidorov.common.matrix.Matrix;
import sidorov.common.matrix.MatrixValidation;
import sidorov.lab5.statisticalgames.ResultWithArrayAndStrategic;
import sidorov.lab5.statisticalgames.SavageCriterionResult;
import sidorov.lab5.statisticalgames.StatisticalGames;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StatisticalGamesLogic implements Logic {

    private Matrix matrix = new Matrix();
    private double alpha = 0.3;
    private double p[] = new double[]{0.14, 0.52, 0.34};
    private double beta = 0.7;

    @Override
    public Result uploadData() {
        ExcelReader excelReader;
        try {
            excelReader = new ExcelReader(TaskSheet.STATISTICAL_GAMES);
        } catch (SheetNotFoundException e) {
            return new Result(Status.ERROR, "���� � ������� ������� �� ������");
        } catch (IOException e) {
            return new Result(Status.ERROR, "�� ������� ��������� ������ �� �����");
        }

        List<List<Double>> matrixList = excelReader.getMatrixFromSheet();

        MatrixValidation validator = new MatrixValidation(matrixList);
        if (!validator.validateMatrix()) {
            return new Result(Status.ERROR, "������� ���������");
        }
        matrix = new Matrix(matrixList);
        return new Result(Status.DATA_UPLOADED, matrix.toText());
    }

    @Override
    public Result solveTask() {
        StatisticalGames statisticalGames = new StatisticalGames(matrix);
        Element gamblerCriterion = statisticalGames.findGamblerCriterion();
        Element maximinCriterion = statisticalGames.findMaximinCriterion();
        int HurwitzCriterion = statisticalGames.findHurwitzCriterion(alpha);
        SavageCriterionResult savageCriterionResult = statisticalGames.findSavageCriterion();
        ResultWithArrayAndStrategic bayesCriterionResult = statisticalGames.findBayesCriterion(p);
        ResultWithArrayAndStrategic laplaceCriterionResult = statisticalGames.findLaplaceCriterion();
        ResultWithArrayAndStrategic hodgesLehmanCriterionResult = statisticalGames.findHodgesLehmanCriterion(p, beta);

        StringBuilder result = new StringBuilder();
        result.append(String.format("�������� ��������� ������:\n��������� - %d, �������� = %.1f (i = %d; j = %d)\n\n",
                gamblerCriterion.i + 1,
                gamblerCriterion.value,
                gamblerCriterion.i + 1,
                gamblerCriterion.j + 1));
        result.append(String.format("����������� �������� (�������� ������):\n��������� - %d, �������� = %.1f (i = %d; j = %d)\n\n",
                maximinCriterion.i + 1,
                maximinCriterion.value,
                maximinCriterion.i + 1,
                maximinCriterion.j + 1));
        result.append(String.format("�������� �������:\n��������� - %d, ��� \u03B1 = %.3f\n\n",
                HurwitzCriterion,
                alpha));
        result.append(String.format("�������� �������:\n������� ������\n%s��������� - %d, �������� = %.1f (i = %d; j = %d)\n\n",
                savageCriterionResult.riskMatrix.toText(),
                savageCriterionResult.strategicElement.i + 1,
                savageCriterionResult.strategicElement.value,
                savageCriterionResult.strategicElement.i + 1,
                savageCriterionResult.strategicElement.j + 1));
        result.append(String.format("�������� ������:\n���. �������� ������ ������ - %s\n��������� - %d\n\n",
                Arrays.toString(bayesCriterionResult.array),
                bayesCriterionResult.strategic));
        result.append(String.format("�������� �������:\n����� ��������� � ������ ����� - %s\n��������� - %d\n\n",
                Arrays.toString(laplaceCriterionResult.array),
                laplaceCriterionResult.strategic));
        result.append(String.format("�������� �������-������:\n����� �������-������ - %s\n��������� - %d\n\n",
                Arrays.toString(hodgesLehmanCriterionResult.array),
                hodgesLehmanCriterionResult.strategic));

        return new Result(Status.SUCCESS, result.toString());
    }
}
