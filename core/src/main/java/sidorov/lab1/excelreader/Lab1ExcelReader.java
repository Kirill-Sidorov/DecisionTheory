package sidorov.lab1.excelreader;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sidorov.common.ExcelHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

        return ExcelHelper.getMatrix(firstSheet);
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

        List<List<Double>> matrix = ExcelHelper.getMatrix(secondSheet);
        List<Double> pVector = ExcelHelper.getVector(secondSheet, "p");
        List<Double> qVector = ExcelHelper.getVector(secondSheet, "q");

        return new MixedStrategiesData(matrix, pVector, qVector);
    }
}
