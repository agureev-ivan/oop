/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication1;

import java.io.*;

public class JavaApplication1 {
    public static void main(String[] args) {
         double[] arguments = {30, 45, 60, 90};

        int onesCount = CalculationManager.countOnes(arguments);

        ResultData resultData = new ResultData(arguments, onesCount);
        try {
            StateManager.saveObject("result.dat", resultData);
            ResultData restoredResult = (ResultData) StateManager.restoreObject("result.dat");

            System.out.println("Before serialization:");
            resultData.printResult();

            System.out.println("\nAfter deserialization:");
            restoredResult.printResult();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }    
    }
}

class CalculationManager implements Serializable {
    private static final long serialVersionUID = 2L;

    public static int countOnes(double... args) {
        double sum = 0;
        for (double arg : args) {
            sum += Math.sin(arg)*1000;
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

class ResultData implements Serializable {
    private static final long serialVersionUID = 1L;

    private double[] arguments;
    private int onesCount;

    public ResultData(double[] arguments, int onesCount) {
        this.arguments = arguments;
        this.onesCount = onesCount;
    }

    public void printResult() {
        System.out.println("Arguments: ");
        for (double arg : arguments) {
            System.out.print(arg + " ");
        }
        System.out.println("\nOnes Count in Binary Representation of Average: " + onesCount);
    }
}

class StateManager {
    public static void saveObject(String filename, Serializable obj) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(obj);
        }
    }

    public static Object restoreObject(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return ois.readObject();
        }
    }
}
