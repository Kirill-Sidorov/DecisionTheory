package sidorov.lab1.excelreader;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lab1ExcelReader {

    private final String path;

    public Lab1ExcelReader() {
        this.path = "C:\\Users\\user\\IdeaProjects\\DecisionTheory\\core\\src\\main\\resources\\lab1.xlsx";
    }

    public List<List<Double>> readDataForPureStrategies() throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        workbook.close();
        inputStream.close();

        List<List<Double>> matrix = new ArrayList<>();

        for (Row row : firstSheet) {
            List<Double> rowValues = new ArrayList<>();
            for (Cell cell : row) {
                if (CellType.NUMERIC == cell.getCellType()) {
                    rowValues.add(cell.getNumericCellValue());
                }
            }
            if (!rowValues.isEmpty()) {
                matrix.add(rowValues);
            }
        }
        return matrix;
    }

    public MixedStrategiesData readDataForMixedStrategies() throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet secondSheet = workbook.getSheetAt(0);
        workbook.close();
        inputStream.close();

        List<List<Double>> matrix = new ArrayList<>();
        List<Double> pVector = new ArrayList<>();
        List<Double> qVector = new ArrayList<>();

        for (Row row : secondSheet) {
            List<Double> rowValues = new ArrayList<>();
            for (Cell cell : row) {
                if (CellType.NUMERIC == cell.getCellType()) {
                    rowValues.add(cell.getNumericCellValue());
                }
                if (CellType.STRING == cell.getCellType()) {
                    if (cell.getStringCellValue().toLowerCase().contains("p")) {
                        fillVector(pVector, row);
                        break;
                    }
                    if (cell.getStringCellValue().toLowerCase().contains("q")) {
                        fillVector(qVector, row);
                        break;
                    }
                }
            }
            if (!rowValues.isEmpty()) {
                matrix.add(rowValues);
            }
        }

        return new MixedStrategiesData(matrix, pVector, qVector);
    }

    private void fillVector(List<Double> vector, Row row) {
        for (Cell cell : row) {
            if (CellType.NUMERIC == cell.getCellType()) {
                vector.add(cell.getNumericCellValue());
            }
        }
    }
}
