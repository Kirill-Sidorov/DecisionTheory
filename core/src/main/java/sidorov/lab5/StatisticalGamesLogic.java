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

import java.io.IOException;
import java.util.List;

public class StatisticalGamesLogic implements Logic {

    private Matrix matrix = new Matrix();
    private double alpha = 0.3;

    @Override
    public Result uploadData() {
        ExcelReader excelReader;
        try {
            excelReader = new ExcelReader(TaskSheet.STATISTICAL_GAMES);
        } catch (SheetNotFoundException e) {
            return new Result(Status.ERROR, "Лист с данными задания не найден");
        } catch (IOException e) {
            return new Result(Status.ERROR, "Не удалось загрузить данные из файла");
        }

        List<List<Double>> matrixList = excelReader.getMatrixFromSheet();

        MatrixValidation validator = new MatrixValidation(matrixList);
        if (!validator.validateMatrix()) {
            return new Result(Status.ERROR, "Матрица невалидна");
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

        StringBuilder result = new StringBuilder();
        result.append(String.format("Критерий азартного игрока:\nстратегия - %d, значение = %.1f (i = %d; j = %d)\n\n",
                gamblerCriterion.i + 1,
                gamblerCriterion.value,
                gamblerCriterion.i + 1,
                gamblerCriterion.j + 1));
        result.append(String.format("Максиминный критерий (критерий Вальда):\nстратегия - %d, значение = %.1f (i = %d; j = %d)\n\n",
                maximinCriterion.i + 1,
                maximinCriterion.value,
                maximinCriterion.i + 1,
                maximinCriterion.j + 1));
        result.append(String.format("Критерий Гурвица:\nстратегия - %d, при \u03B1 = %.3f\n\n",
                HurwitzCriterion,
                alpha));
        result.append(String.format("Критерий Сэвиджа:\nматрица рисков\n%sстратегия - %d, значение = %.1f (i = %d; j = %d)\n\n",
                savageCriterionResult.riskMatrix.toText(),
                savageCriterionResult.strategicElement.i + 1,
                savageCriterionResult.strategicElement.value,
                savageCriterionResult.strategicElement.i + 1,
                savageCriterionResult.strategicElement.j + 1));

        return new Result(Status.SUCCESS, result.toString());
    }
}
