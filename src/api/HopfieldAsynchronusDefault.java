package api;

import com.heatonresearch.book.introneuralnet.neural.matrix.Matrix;
import com.heatonresearch.book.introneuralnet.neural.matrix.MatrixMath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HopfieldAsynchronusDefault implements Hopfield {



    static double W[][] = {
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
    private int repCounter;
    private List<Double> energyList;

    public HopfieldAsynchronusDefault() {
        this.matrix = new Matrix(W);
        this.energyList = new ArrayList<>();
    }

    @Override
    public double calculateEnergy() {
        return calculateEnergyAsynchronousMode(matrix, output);
    }

    public static double calculateEnergyAsynchronousMode(Matrix matrix, Matrix output) {
        double E = 0.0;
        for (int row = 0; row < 3; row++) {
            for (int cell = 0; cell < 3; cell++) {
                E += (matrix.get(row, cell) * output.get(row, 0) * output.get(cell, 0));
            }
        }
        return E * -0.5;
    }

    @Override
    public void matrixToString(Matrix M) {
        matrixUToString(M, stepCounter);
    }


    private static String matrixNormalToString(Matrix M) {
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
        return tmp.toString();
    }

    private static void matrixUToString(Matrix M, int stepCounter) {
        String[] table = null;
        if (stepCounter == 0) {
            String[] table1 = {String.valueOf(M.get(0, 0)), "NW", "NW"};
            table = table1;
        } else if (stepCounter == 1) {
            String[] table1 = {"NW", String.valueOf(M.get(1, 0)), "NW"};
            table = table1;
        } else if (stepCounter == 2) {
            String[] table1 = {"NW", "NW", String.valueOf(M.get(2, 0))};
            table = table1;
        }
        Arrays.stream(table).forEach(System.out::println);
    }

    public Matrix multiplyAsynchronous(int stepCounter, Matrix input, Matrix vector) {
        Matrix result = MatrixMath.multiply(input, vector);
        Matrix output = null;

        if (stepCounter == 0) {
            double[] table = {result.get(0, 0), vector.get(1, 0), vector.get(2, 0)};
            output = Matrix.createColumnMatrix(table);

        } else if (stepCounter == 1) {
            double[] table = {vector.get(0, 0), result.get(1, 0), vector.get(2, 0)};
            output = Matrix.createColumnMatrix(table);

        } else if (stepCounter == 2) {
            double[] table = {vector.get(0, 0), vector.get(1, 0), result.get(2, 0)};
            output = Matrix.createColumnMatrix(table);

        }
        return output;
    }

    public Matrix changeToBiPolar(int stepCounter, Matrix vector) {
        Matrix result = vector;
        double point = 0;
        if (stepCounter == 0) {
            result.set(0, 0, bipolarCheck(result.get(0, 0)));
        } else if (stepCounter == 1) {
            result.set(1, 0, bipolarCheck(result.get(1, 0)));
        } else if (stepCounter == 2) {
            result.set(2, 0, bipolarCheck(result.get(2, 0)));
        }
        return result;
    }
}
