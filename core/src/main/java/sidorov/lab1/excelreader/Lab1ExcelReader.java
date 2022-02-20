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
        inputStream.close();
        if (workbook.getNumberOfSheets() == 0) {
            workbook.close();
            throw new IOException();
        }
        Sheet firstSheet = workbook.getSheetAt(0);
        workbook.close();

        return getMatrix(firstSheet);
    }

    public MixedStrategiesData readDataForMixedStrategies() throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(inputStream);
        inputStream.close();
        if (workbook.getNumberOfSheets() < 2) {
            workbook.close();
            throw new IOException();
        }
        Sheet secondSheet = workbook.getSheetAt(1);
        workbook.close();

        List<List<Double>> matrix = getMatrix(secondSheet);
        List<Double> pVector = getVector(secondSheet, "p");
        List<Double> qVector = getVector(secondSheet, "q");

        return new MixedStrategiesData(matrix, pVector, qVector);
    }

    private List<List<Double>> getMatrix(Sheet sheet) {
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

    private List<Double> getVector(Sheet sheet, String vectorSymbol) {
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
