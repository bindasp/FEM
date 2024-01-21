import org.apache.commons.math3.linear.*;

import java.util.Arrays;

public class Soe {
    public int n;
    public double[][] Hg;
    public double[] Pg;
    public double[][] Cg;

    Soe(int n) {
        this.n = n;
        Hg = new double[n][n];
        Pg = new double[n];
        Cg = new double[n][n];

    }

    public void agregate(Element element) {
        //Agregacja macierzy H, C i wektora P
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Hg[element.ID[i] - 1][element.ID[j] - 1] += element.H[i][j];
                Cg[element.ID[i] - 1][element.ID[j] - 1] += element.C[i][j];
            }
            Pg[element.ID[i] - 1] += element.P[i];
        }
    }

    //Funkcja do obliczania układów równań metodą LU
    public RealVector LUDecomposition(double[][] A, double[] R) {
        RealMatrix coefficientMatrix = MatrixUtils.createRealMatrix(A);
        DecompositionSolver solver = new LUDecomposition(coefficientMatrix).getSolver();
        RealVector constants = new ArrayRealVector(R);

        return solver.solve(constants);
    }

    public RealVector calculate(double simulationStepTime, double[] initialTemp) {
        //Symulacja nieustalonego procesu cieplnego
        double[][] Cl = new double[n][n];
        double[] Pl = new double[n];
        double[][] Hl = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Cl[i][j] = Cg[i][j];
                Hl[i][j] = Hg[i][j];
            }
            Pl[i] = Pg[i];
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Cl[i][j] /= simulationStepTime;

                Pl[i] += Cl[i][j] * initialTemp[j];
                Hl[i][j] += Cl[i][j];
            }

        }

        return LUDecomposition(Hl, Pl);
    }
}

