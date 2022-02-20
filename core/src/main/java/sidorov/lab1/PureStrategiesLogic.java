package sidorov.lab1;

import sidorov.common.Element;
import sidorov.common.Logic;
import sidorov.common.Matrix;
import sidorov.common.TextHelper;
import sidorov.common.result.Result;
import sidorov.common.result.Status;
import sidorov.lab1.purestrategies.PureFirstStep;
import sidorov.lab1.purestrategies.PureFourthStep;
import sidorov.lab1.purestrategies.PureSecondStep;
import sidorov.lab1.purestrategies.PureThirdStep;

import java.util.List;

public class PureStrategiesLogic implements Logic {

    private Matrix matrix;

    @Override
    public Result uploadData() {
        matrix = new Matrix(new int[][]{
                {0, 1, 1, 0},
                {2, 1, 3, 1},
                {1, 1, 1, 1}
        });
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
        result.append(TextHelper.getSaddlePointsView(saddlePoints));
        result.append(String.format("V = h%d%d = %d", H.i + 1, H.j + 1, H.value));
        return new Result(Status.TASK_SOLVED, result.toString());
    }
}
