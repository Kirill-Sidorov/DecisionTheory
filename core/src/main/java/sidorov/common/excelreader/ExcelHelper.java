package sidorov.common.excelreader;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {

    private static final String filePath = "C:\\Users\\user\\IdeaProjects\\DecisionTheory\\core\\src\\main\\resources\\data.xlsx";

    private ExcelHelper() {
    }

    public static Sheet getTaskSheet(TaskSheet taskSheet) throws IOException, SheetNotFoundException {
        FileInputStream inputStream = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        inputStream.close();
        if (workbook.getNumberOfSheets() < taskSheet.ordinal()) {
            workbook.close();
            throw new SheetNotFoundException();
        }
        Sheet sheet = workbook.getSheetAt(taskSheet.ordinal());
        workbook.close();
        return sheet;
    }

    public static List<List<Double>> getMatrixFromSheet(Sheet sheet) {
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

    public static List<Double> getVectorFromSheet(Sheet sheet, String vectorSymbol) {
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
