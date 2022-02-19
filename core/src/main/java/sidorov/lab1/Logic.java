package sidorov.lab1;

import sidorov.common.Element;
import sidorov.common.ExcelReader;
import sidorov.common.Matrix;
import sidorov.lab1.steps.FirstStep;
import sidorov.lab1.steps.FourthStep;
import sidorov.lab1.steps.SecondStep;
import sidorov.lab1.steps.ThirdStep;

import java.io.IOException;
import java.util.List;

public class Logic {


    /*
    private static int[][] matrix = {
            {0, 0, 7, 0, 0},
            {-8, -3, -7, -3, -2},
            {1, 6, -8, -7, 0},
            {-4, -2, 6, 0, -5},
            {0, 6, 1, 0, 0}
    };

     */


    public static void main(String[] args) {
        /*
        Matrix matrix = new Matrix(new int[][]{
                {0, 1, 1, 0},
                {2, 1, 3, 1},
                {1, 1, 1, 1}
        });

        Element H = new FirstStep(matrix).execute();
        if (H == null) {
            return;
        }
        List<Element> elementsY = new SecondStep(matrix).execute(H);
        List<Element> elementsX = new ThirdStep(matrix).execute(H);
        List<Element> saddlePoints = new FourthStep(matrix).execute(elementsX, elementsY);

        for (Element element : saddlePoints) {
            System.out.println("{" + (element.i + 1) + " " + (element.j + 1) + "} " + element.value);
        }

         */
        try {
            ExcelReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" + currentDir);
    }

}
