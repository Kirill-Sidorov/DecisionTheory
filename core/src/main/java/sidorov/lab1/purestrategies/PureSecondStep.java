package sidorov.lab1.purestrategies;

import sidorov.common.Element;
import sidorov.common.Matrix;

import java.util.ArrayList;
import java.util.List;

public class PureSecondStep {

    private final Matrix matrix;

    public PureSecondStep(Matrix matrix) {
        this.matrix = matrix;
    }

    public List<Element> execute(Element H) {
        List<Element> result = new ArrayList<>();
        result.add(H);

        List<Element> elementsWithEqualValues = new ArrayList<>();

        int row = H.i;
        int startColumn = H.j + 1;
        int value = H.value;

        for (int j = startColumn; j < matrix.columnsNumber(); j++) {
            if (value == matrix.get(row, j)) {
                elementsWithEqualValues.add(matrix.getElement(row, j));
            }
        }

        for (Element element : elementsWithEqualValues) {
            int max = element.value;
            int column = element.j;

            boolean isMax = true;

            for (int i = 0; i < matrix.rowsNumber(); i++) {
                if (max < matrix.get(i, column)) {
                    isMax = false;
                    break;
                }
            }

            if (isMax) {
                result.add(element);
            }
        }
        return result;
    }
}
