package sidorov.lab1.purestrategies;

import com.sun.istack.internal.Nullable;
import sidorov.common.*;

import java.util.ArrayList;
import java.util.List;

public class PureFirstStep {

    private final Matrix matrix;

    public PureFirstStep(Matrix matrix) {
        this.matrix = matrix;
    }

    @Nullable
    public Element execute() {
        for (int i = 0; i < matrix.rowsNumber(); i++) {

            List<Element> minElementsInRow = new ArrayList<>();
            int min = matrix.get(i, 0);

            for (int j = 0; j < matrix.columnsNumber(); j++) {

                if (min > matrix.get(i, j)) {
                    min = matrix.get(i, j);
                    minElementsInRow.clear();
                }

                if (min == matrix.get(i, j)) {
                    minElementsInRow.add(matrix.getElement(i, j));
                }
            }

            for (Element element : minElementsInRow) {
                int max = element.value;
                int column = element.j;

                boolean isMax = true;

                for (int row = 0; row < matrix.rowsNumber(); row++) {
                    if (max < matrix.get(row, column)) {
                        isMax = false;
                        break;
                    }
                }

                if (isMax) {
                    return element;
                }
            }
        }
        return null;
    }
}
