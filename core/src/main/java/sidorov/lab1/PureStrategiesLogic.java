package sidorov.lab1;

import sidorov.common.*;
import sidorov.common.result.Result;
import sidorov.common.result.Status;
import sidorov.lab1.excelreader.Lab1ExcelReader;
import sidorov.lab1.purestrategies.PureFirstStep;
import sidorov.lab1.purestrategies.PureFourthStep;
import sidorov.lab1.purestrategies.PureSecondStep;
import sidorov.lab1.purestrategies.PureThirdStep;

import java.io.IOException;
import java.util.List;

public class PureStrategiesLogic implements Logic {

    private Matrix matrix = new Matrix();

    @Override
    public Result uploadData() {
        Lab1ExcelReader lab1ExcelReader = new Lab1ExcelReader();
        List<List<Double>> matrixList = null;
        try {
            matrixList = lab1ExcelReader.readDataForPureStrategies();
        } catch (IOException e) {
            return new Result(Status.ERROR, "Не удалось загрузить данные из файла");
        }

        MatrixValidator validator = new MatrixValidator(matrixList);
        if (!validator.validateMatrix()) {
            return new Result(Status.ERROR, "Матрица невалидна");
        }
        matrix = new Matrix(matrixList);
        return new Result(Status.DATA_UPLOADED, matrix.toText());
    }

    @Override
    public Result solveTask() {
        Element H = new PureFirstStep(matrix).execute();
        if (H == null) {
            return new Result(Status.INFO, "Игра не имеет решений в чистых стратегиях");
        }
        List<Element> elementsY = new PureSecondStep(matrix).execute(H);
        List<Element> elementsX = new PureThirdStep(matrix).execute(H);
        List<Element> saddlePoints = new PureFourthStep(matrix).execute(elementsX, elementsY);

        StringBuilder result = new StringBuilder();
        result.append(TextHelper.getSaddlePointsText(saddlePoints));
        result.append(String.format("V = h%d%d = %.4f", H.i + 1, H.j + 1, H.value));
        return new Result(Status.TASK_SOLVED, result.toString());
    }
}
