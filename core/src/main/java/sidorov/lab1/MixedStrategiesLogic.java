package sidorov.lab1;

import sidorov.common.Element;
import sidorov.common.Logic;
import sidorov.common.Matrix;
import sidorov.common.result.Result;
import sidorov.common.result.Status;
import sidorov.lab1.mixedstrategies.MixedFirstStep;
import sidorov.lab1.mixedstrategies.MixedSecondStep;
import sidorov.lab1.mixedstrategies.MixedSecondStepResult;

import java.util.Arrays;

public class MixedStrategiesLogic implements Logic {


    /*
    private static int[][] matrix = {
            {0, 0, 7, 0, 0},
            {-8, -3, -7, -3, -2},
            {1, 6, -8, -7, 0},
            {-4, -2, 6, 0, -5},
            {0, 6, 1, 0, 0}
    };

     */

    private Matrix matrix;
    private double[] pVector;
    private double[] qVector;

/*
    public static void main(String[] args) {

        Matrix

        MixedFirstStep mixedFirstStep = new MixedFirstStep(matrix);

        Element V1 = mixedFirstStep.findV1();
        Element V2 = mixedFirstStep.findV2();

        System.out.println("{" + (V1.i + 1) + " " + (V1.j + 1) + "} " + V1.value);
        System.out.println("{" + (V2.i + 1) + " " + (V2.j + 1) + "} " + V2.value);

        double[] pVector = new double[]{ 0.625, 0, 0.375, 0 };
        double[] qVector = new double[]{ 0, 0.8125, 0.1875 };

        MixedSecondStep mixedSecondStep = new MixedSecondStep(matrix);
        MixedSecondStepResult xResult = mixedSecondStep.calcDataX(pVector);
        MixedSecondStepResult yResult = mixedSecondStep.calcDataY(qVector);

        System.out.println(xResult.value + " :" + Arrays.toString(xResult.vector));
        System.out.println(yResult.value + " :" + Arrays.toString(yResult.vector));
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

        /*
        try {
            ExcelReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" + currentDir);



    }
    */


    @Override
    public Result uploadData() {
        matrix = new Matrix(new int[][]{
                {8, -1, -7},
                {3, -5, 8},
                {3, -4, 6},
                {-4, -2, -5}
        });
        pVector = new double[]{0.625, 0, 0.375, 0};
        qVector = new double[]{0, 0.8125, 0.1875};

        StringBuilder result = new StringBuilder();
        result.append(matrix.toText());
        result.append(String.format("������ p = %s\n", Arrays.toString(pVector)));
        result.append(String.format("������ q = %s\n", Arrays.toString(qVector)));

        return new Result(Status.DATA_UPLOADED, result.toString());
    }

    @Override
    public Result solveTask() {

        MixedFirstStep mixedFirstStep = new MixedFirstStep(matrix);

        Element V1 = mixedFirstStep.findV1();
        Element V2 = mixedFirstStep.findV2();

        MixedSecondStep mixedSecondStep = new MixedSecondStep(matrix);
        MixedSecondStepResult xResult = mixedSecondStep.calcDataX(pVector);
        MixedSecondStepResult yResult = mixedSecondStep.calcDataY(qVector);

        StringBuilder result = new StringBuilder();
        result.append(String.format("V1 = %d (i = %d; j = %d)\n", V1.value, V1.i + 1, V1.j + 1));
        result.append(String.format("V2 = %d (i = %d; j = %d)\n", V2.value, V2.i + 1, V2.j + 1));
        result.append(String.format("������ x = %s\nxMin = %.4f\n", Arrays.toString(xResult.vector), xResult.limitValue));
        result.append(String.format("������ y = %s\nyMax = %.4f\n", Arrays.toString(yResult.vector), yResult.limitValue));

        if (xResult.limitValue == yResult.limitValue) {
            result.append("��� ��� xMin = yMax - �������� <p,q> � ��������� ���������� �������� �������� ������ � ��������� ����������.\n");
            result.append(String.format("W = xMin = yMax = %.4f\n", xResult.limitValue));
        }

        return new Result(Status.TASK_SOLVED, result.toString());
    }
}
