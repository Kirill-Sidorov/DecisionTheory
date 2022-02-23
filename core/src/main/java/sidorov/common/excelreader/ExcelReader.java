package sidorov.common.excelreader;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    private final String path = "C:\\Users\\user\\IdeaProjects\\DecisionTheory\\core\\src\\main\\resources\\data.xlsx";

    private Sheet sheet;

    public ExcelReader(TaskSheet taskSheet) throws IOException, SheetNotFoundException {
        loadSheet(taskSheet);
    }

    public void loadSheet(TaskSheet taskSheet) throws IOException, SheetNotFoundException {
        FileInputStream inputStream = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(inputStream);
        inputStream.close();
        if (workbook.getNumberOfSheets() < taskSheet.ordinal()) {
            workbook.close();
            throw new SheetNotFoundException();
        }
        sheet = workbook.getSheetAt(taskSheet.ordinal());
        workbook.close();
    }

    public List<List<Double>> getMatrixFromSheet() {
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

    public List<Double> getVectorFromSheet(String vectorSymbol) {
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
