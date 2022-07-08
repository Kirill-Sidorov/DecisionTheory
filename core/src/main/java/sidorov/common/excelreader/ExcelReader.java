package sidorov.common.excelreader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    private final String path = "data.xlsx";

    private Sheet sheet;

    public ExcelReader(TaskSheet taskSheet) throws IOException, SheetNotFoundException {
        loadSheet(taskSheet);
    }

    public void loadSheet(TaskSheet taskSheet) throws IOException, SheetNotFoundException {
        FileInputStream inputStream = new FileInputStream(Paths.get(path).toAbsolutePath().toString());
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

    public List<List<Double>> getMatrixFromSheetByName(String name) {
        String lowerCaseName = name.toLowerCase();
        List<List<Double>> matrix = new ArrayList<>();
        boolean isMatrixFind = false;
        for (Row row : sheet) {
            List<Double> rowValues = new ArrayList<>();
            for (Cell cell : row) {
                if (CellType.NUMERIC == cell.getCellType() && isMatrixFind) {
                    rowValues.add(cell.getNumericCellValue());
                    continue;
                }
                if (CellType.STRING == cell.getCellType()) {
                    if (isMatrixFind) {
                        return matrix;
                    } else {
                        isMatrixFind = cell.getStringCellValue().toLowerCase().contains(lowerCaseName);
                    }
                }
            }
            if (!rowValues.isEmpty()) {
                matrix.add(rowValues);
            }
        }
        return matrix;
    }

    public List<String> getStringSetFromSheetByName(String name) {
        String lowerCaseName = name.toLowerCase();
        List<String> set = new ArrayList<>();
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (CellType.STRING == cell.getCellType()
                        && cell.getStringCellValue().toLowerCase().contains(lowerCaseName)) {
                    boolean isFirstCell = true;
                    for (Cell vectorCell : row) {
                        if (isFirstCell) {
                            isFirstCell = false;
                            continue;
                        }
                        if (CellType.STRING == vectorCell.getCellType()) {
                            set.add(vectorCell.getStringCellValue());
                        }
                    }
                    return set;
                }
            }
        }
        return set;
    }
}
