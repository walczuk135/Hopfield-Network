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
