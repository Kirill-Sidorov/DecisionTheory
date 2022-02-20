package sidorov.common;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {
    private ExcelHelper() {
    }

    public static List<List<Double>> getMatrix(Sheet sheet) {
        List<List<Double>> matrix = new ArrayList<>();
        for (Row row : sheet) {
            List<Double> rowValues = new ArrayList<>();
            for (Cell cell : row) {
                if (CellType.NUMERIC == cell.getCellType()) {
                    rowValues.add(cell.getNumericCellValue());
                }
                if (CellType.STRING == cell.getCellType()) {
                    String value = cell.getStringCellValue().toLowerCase();
                    if (value.contains("p") || value.contains("q")) {
                        return matrix;
                    }
                }
            }
            matrix.add(rowValues);
        }
        return matrix;
    }

    public static List<Double> getVector(Sheet sheet, String vectorSymbol) {
        List<Double> vector = new ArrayList<>();
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (CellType.STRING == cell.getCellType()
                        && cell.getStringCellValue().toLowerCase().contains(vectorSymbol)) {
                    for (Cell vectorCell : row) {
                        if (CellType.NUMERIC == vectorCell.getCellType()) {
                            vector.add(vectorCell.getNumericCellValue());
                        }
                    }
                    return vector;
                }
            }
        }
        return vector;
    }
}
