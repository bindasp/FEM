import org.apache.commons.math3.linear.*;

import java.util.Arrays;

public class Soe {
    public int n;
    public double [][] Hg;
    public double [] Pg;
    public double [][] Cg;
    public double [][] result;


    Soe(int n){
        this.n=n;
        Hg = new double[n][n];
        Pg = new double[n];
        Cg = new double[n][n];

        result = new double[n][n+1];
    }

    public void agregate(Element element){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Hg[element.ID[i]-1][element.ID[j]-1] += element.H[i][j];
                Cg[element.ID[i]-1][element.ID[j]-1] += element.C[i][j];
            }
            Pg[element.ID[i]-1]+=element.P[i];
        }
    }


    public RealVector LUDecomposition(double[][] A, double[] R) {
        RealMatrix coefficientMatrix = MatrixUtils.createRealMatrix(A);
        DecompositionSolver solver = new LUDecomposition(coefficientMatrix).getSolver();
        RealVector constants = new ArrayRealVector(R);
        RealVector solution = solver.solve(constants);

        return solution;
    }

    public double [][][] calculate(double simulationStepTime, double [] initialTemp){
        System.out.println(Arrays.toString(initialTemp));
        double [][] Cl = Cg;
        double [] Pl = Pg;
        double [][] Hl = Hg;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Cl[i][j] /= simulationStepTime;
                Pl[i] += Cl[i][j] * initialTemp[j];
                Hl[i][j]+= Cl[i][j];
            }
        }
        double [][][] tab = new double[2][n][n];
        tab[0] = Hl;
        tab[1][0] = Pl;
        return tab;
    }
}



