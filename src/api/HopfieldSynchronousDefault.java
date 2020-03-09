package api;


import com.heatonresearch.book.introneuralnet.neural.matrix.Matrix;
import com.heatonresearch.book.introneuralnet.neural.matrix.MatrixMath;

import java.util.ArrayList;
import java.util.List;

public class HopfieldSynchronousDefault implements Hopfield {

    double W[][] = {
            {0.0, -1.0, -3.0},
            {-1.0, 0.0, 2.0},
            {-3.0, 2.0, 0.0}
    };

    private Matrix matrix;
    private Matrix vector;
    private Matrix input;
    private Matrix output;
    private Matrix U;
    private boolean check;
    private int stepCounter;
    private List<Double> energyList = new ArrayList<>();

    public HopfieldSynchronousDefault() {
        this.matrix = new Matrix(W);
        this.energyList = new ArrayList<>();
    }

    @Override
    public double calculateEnergy() {
        return calculateEnergy(matrix, input, output);
    }

    public static double calculateEnergy(Matrix matrix, Matrix input, Matrix output) {
        double E = 0.0;
        for (var row = 0; row < 3; row++) {
            for (var cell = 0; cell < 3; cell++) {
                E += matrix.get(row, cell) * input.get(row, 0) * output.get(cell, 0);
            }
        }
        return E * (-1);
    }

    @Override
    public void executeTest() {
        for (int i = 0; i < vectorListBiPolar.size(); i++) {
            System.out.println("########## Badanie nr " + (i + 1) + " ###########");
            System.out.println("-> Badany wektor V: ");
            vector = Matrix.createColumnMatrix(vectorListBiPolar.get(i));
            matrixToString(vector);
            System.out.println("!--- Badanie punktu w trybie synchronicznym ---!");
            check = true;
            input = vector;
            stepCounter = 1;
            executeStep(matrix, input, check, stepCounter, energyList);
        }
    }

    @Override
    public void executeStep(Matrix matrix, Matrix input, boolean check, int stepCounter, List<Double> energyList) {
        while (check) {
            U = MatrixMath.multiply(matrix, input);
            System.out.println("\n******** Krok nr " + stepCounter + " ********");
            System.out.println("-> Potencjał wejściowy U");
            matrixToString(U);
            double a1 = bipolarCheck(U.get(0, 0));
            double a3 = bipolarCheck(U.get(2, 0));
            double a2 = bipolarCheck(U.get(1, 0));
            output = Matrix.createColumnMatrix(new double[]{a1, a2, a3});

            System.out.println("-> Potencjał wyjściowy V:");
            matrixToString(output);

            energyList.add(calculateEnergy(matrix, input, output));
            System.out.println("E(" + stepCounter + ") = " + calculateEnergy(matrix, input, output));
            if (input.equals(output)) {
                System.out.println("\n!-- Sieć ustabilizowała się! --!");
                System.out.println("-> V" + (stepCounter - 1) + ":");
                matrixToString(input);
                check = false;
            }
            if (energyList.size() >= 2) {
                int listsize = energyList.size();
                if (0 == energyList.get(listsize - 1) - energyList.get(listsize - 2)) {
                    System.out.println("\n!-- Oscylacja dwypunktowa --!");
                    System.out.println("Punkty oscylacji:");
                    System.out.println("-> V" + (stepCounter - 1) + ":");
                    matrixToString(input);
                    System.out.println("-> V" + (stepCounter) + ":");
                    matrixToString(output);
                    energyList.clear();
                    check = false;
                }
            }

            if (stepCounter > 9) {
                System.out.println("\n!-- Osiągnięto maksymalną ilość kroków sieć się nie ustabilizoała się --!");
                check = false;
            }
            input = output;
            stepCounter++;
        }
    }

    @Override
    public void matrixToString(Matrix M) {
        StringBuilder tmp = new StringBuilder();
        tmp.append("\t");
        int i = 0;
        while (i < M.getRows()) {
            int j = 0;
            while (j < M.getCols()) {
                tmp.append(M.get(i, j)).append(",");
                j++;
            }
            tmp.append("\n\t");
            i++;
        }
        System.out.println(tmp.toString());
    }
}
