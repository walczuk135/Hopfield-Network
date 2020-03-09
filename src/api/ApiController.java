package api;


import com.heatonresearch.book.introneuralnet.neural.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApiController {

    private Scanner scanner;
    private Matrix matrix;
    private Matrix vectorM;
    private boolean check;
    private int stepCounter;
    private List<Double> energyList;
    private double vector[];
    private Hopfield hopfieldA;
    private Hopfield hopfieldS;
    private Hopfield Asynch;

    static double W[][] = {
            {0.0, -1.0, -3.0},
            {-1.0, 0.0, 2.0},
            {-3.0, 2.0, 0.0}
    };

    public ApiController() {
        this.scanner = new Scanner(System.in);
        matrix=new Matrix(W);
        vector=new double[3];
        vectorM=Matrix.createColumnMatrix(vector);
        energyList=new ArrayList<>();
        check=true;
        hopfieldA=new HopfieldAsynchronusDefault();
        hopfieldS=new HopfieldSynchronousDefault();
        Asynch=new HopfieldAsynchronusDefault();
    }

    public void viewMenu(){
        System.out.println();
        System.out.println("----- Sieci Hopfielda wybierz tryb------------------------------------");
        System.out.println("----- 1 - TRYB-SYNCHRONICZNY------------------------------------------");
        System.out.println("----- 2 - TRYB-ASYNCHRONICZNY-----------------------------------------");
        System.out.println("----- 3 - DEFAULT-----------------------------------------------------");
        System.out.println("----- 4 - ZAMKNIJ PROGRAM  -------------------------------------------");
    }

    public void run() {
        boolean check=true;
        while (check) {
            viewMenu();
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    vector = vectorList();
                    vectorM = Matrix.createColumnMatrix(vector);
                    hopfieldS.executeStep(matrix, vectorM, check, 1, energyList);
                    break;
                case 2:
                    vector = vectorList();
                    vectorM = Matrix.createColumnMatrix(vector);
                    hopfieldA.executeStep(matrix, vectorM, check, 0, energyList);
                    break;
                case 3:
                    hopfieldS.executeTest();
                    hopfieldA.executeTest();
                    break;
                case 4:
                    System.out.println("KONIEC");
                    check = false;
                    break;
                default:
                    System.out.println("Nie odpoiednia opcja");
                    break;
            }
        }
    }

    public double[] vectorList(){
        System.out.println("Podaj wektor V[v1,v2,v3]");
        double v1=scanner.nextDouble();
        double v2=scanner.nextDouble();
        double v3=scanner.nextDouble();
        return new double[]{v1,v2,v3};
    }

}
