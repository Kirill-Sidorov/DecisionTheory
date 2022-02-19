package sidorov.lab1.steps;

import com.sun.istack.internal.Nullable;
import sidorov.common.*;

import java.util.ArrayList;
import java.util.List;

public class FirstStep {

    private final Matrix matrix;

    public FirstStep(Matrix matrix) {
        this.matrix = matrix;
    }

    @Nullable
    public Element execute() {
        for (int i = 0; i < matrix.columnLength(); i++) {

            List<Element> minElementsInRow = new ArrayList<>();
            int min = matrix.get(i, 0);

            for (int j = 0; j < matrix.rowLength(i); j++) {

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

                for (int row = 0; row < matrix.columnLength(); row++) {
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
