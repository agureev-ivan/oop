/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JavaApplication1 {
    public static void main(String[] args) {
          Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of arguments:");
        int numArgs = scanner.nextInt();
        double[] arguments = new double[numArgs];
        System.out.println("Enter arguments:");
        for (int i = 0; i < numArgs; i++) {
            arguments[i] = scanner.nextDouble();
        }

        System.out.println("Choose result display format:");
        System.out.println("1. Text Format");
        System.out.println("2. Table Format");
        int choice = scanner.nextInt();

        ResultFactory resultFactory;
        if (choice == 1) {
            System.out.println("Enter column width:");
            int columnWidth = scanner.nextInt();
            resultFactory = new TextResultFactory(columnWidth);
        } else {
            System.out.println("Enter number of columns:");
            int columns = scanner.nextInt();
            resultFactory = new TableResultFactory(columns);
        }

        int onesCount = CalculationManager.countOnes(arguments);
        ResultData resultData = new ResultData(arguments, onesCount);

        ResultDisplay resultDisplay = resultFactory.createResultDisplay();
        resultDisplay.display(resultData);
    }
}

interface ResultDisplay {
    void display(ResultData result);
}

class TextResultDisplay implements ResultDisplay {
    private int columnWidth;

    public TextResultDisplay(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    @Override
    public void display(ResultData result) {
        System.out.println("Arguments:");
        for (int i = 0; i < result.getArguments().length; i++) {
            System.out.printf("%-" + columnWidth + "s", result.getArguments()[i]);
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
        System.out.println("\nOnes Count in Binary Representation of Average: " + result.getOnesCount());
    }
}

class TableResultDisplay implements ResultDisplay {
    private int columns;

    public TableResultDisplay(int columns) {
        this.columns = columns;
    }

    @Override
    public void display(ResultData result) {
        System.out.println("Arguments Table:");
        for (int i = 0; i < result.getArguments().length; i++) {
            System.out.printf("%-15s", result.getArguments()[i]);
            if ((i + 1) % columns == 0) {
                System.out.println();
            }
        }
        System.out.println("\nOnes Count in Binary Representation of Average: " + result.getOnesCount());
    }
}

class CalculationManager {
    public static int countOnes(double... args) {
        double sum = 0;
        for (double arg : args) {
            sum += Math.sin(Math.toRadians(arg));
        }
        double avg = sum / args.length;
        int integerPart = (int) avg;
        String binaryString = Integer.toBinaryString(integerPart);
        int count = 0;
        for (char c : binaryString.toCharArray()) {
            if (c == '1') {
                count++;
            }
        }
        return count;
    }
}

class ResultData {
    private double[] arguments;
    private int onesCount;

    public ResultData(double[] arguments, int onesCount) {
        this.arguments = arguments;
        this.onesCount = onesCount;
    }

    public double[] getArguments() {
        return arguments;
    }

    public int getOnesCount() {
        return onesCount;
    }
}

interface ResultFactory {
    ResultDisplay createResultDisplay();
}

class TextResultFactory implements ResultFactory {
    private int columnWidth;

    public TextResultFactory(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    @Override
    public ResultDisplay createResultDisplay() {
        return new TextResultDisplay(columnWidth);
    }
}

class TableResultFactory implements ResultFactory {
    private int columns;

    public TableResultFactory(int columns) {
        this.columns = columns;
    }

    @Override
    public ResultDisplay createResultDisplay() {
        return new TableResultDisplay(columns);
    }
}
