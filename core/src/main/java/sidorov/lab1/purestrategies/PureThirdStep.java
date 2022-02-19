package sidorov.lab1.purestrategies;

import sidorov.common.Element;
import sidorov.common.Matrix;

import java.util.ArrayList;
import java.util.List;

public class PureThirdStep {

    private final Matrix matrix;

    public PureThirdStep(Matrix matrix) {
        this.matrix = matrix;
    }

    public List<Element> execute(Element H) {
        List<Element> result = new ArrayList<>();
        result.add(H);

        List<Element> elementsWithEqualValues = new ArrayList<>();

        int startRow = H.i + 1;
        int column = H.j;
        int value = H.value;

        for (int i = startRow; i < matrix.rowsNumber(); i++) {
            if (value == matrix.get(i, column)) {
                elementsWithEqualValues.add(matrix.getElement(i, column));
            }
        }

        for (Element element : elementsWithEqualValues) {
            int min = element.value;
            int row = element.i;

            boolean isMin = true;

            for (int j = 0; j < matrix.columnsNumber(); j++) {
                if (min > matrix.get(row, j)) {
                    isMin = false;
                    break;
                }
            }

            if (isMin) {
                result.add(element);
            }
        }
        return result;
    }
}
