package api;


import com.heatonresearch.book.introneuralnet.neural.matrix.Matrix;

import java.util.Arrays;
import java.util.List;

public interface Hopfield {
    double calculateEnergy();
    void executeTest();
    void executeStep(Matrix matrix, Matrix input, boolean check, int stepCounter, List<Double> energyList);

    List<double[]> vectorListBiPolar = Arrays.asList(
            new double[]{-1.0, -1.0, -1.0},
            new double[]{-1.0, -1.0, 1.0},
            new double[]{-1.0, 1.0, -1.0},
            new double[]{-1.0, 1.0, 1.0},
            new double[]{1.0, -1.0, -1.0},
            new double[]{1.0, 1.0, -1.0},
            new double[]{1.0, -1.0, 1.0},
            new double[]{1.0, 1.0, 1.0}
    );

    default double bipolarCheck(double n) {
        if (n <= 0) {
            return -1;
        } else return 1;
    }

      void matrixToString(Matrix M);

}
