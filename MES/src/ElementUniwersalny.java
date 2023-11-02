import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

public class ElementUniwersalny {
    ElementUniwersalny(int n) {
        ksi = new double[n * n][4];
        eta = new double[n * n][4];
        tabksi = new double[n * n][4];
        this.n = n;
        fun();
        if (n == 2) {
            double p1 = -1.0 * Math.sqrt(1.0 / 3.0);
            double p2 = Math.sqrt(1.0 / 3.0);
            p = new double[n];
            p[0] = p1;
            p[1] = p2;
        } else if (n == 3) {
            double p1 = -1.0 * Math.sqrt(3.0 / 5.0);
            double p2 = 0.0;
            double p3 = Math.sqrt(3.0 / 5.0);
            p = new double[n];
            p[0] = p1;
            p[1] = p2;
            p[2] = p3;
        } else if (n == 4) {
            double p1 = -1.0 * Math.sqrt((3.0 / 7.0) - ((2.0 / 7.0) * Math.sqrt(6.0 / 5.0)));
            double p2 = -1.0 * Math.sqrt((3.0 / 7.0) + ((2.0 / 7.0) * Math.sqrt(6.0 / 5.0)));
            double p3 = Math.sqrt((3.0 / 7.0) - ((2.0 / 7.0) * Math.sqrt(6.0 / 5.0)));
            double p4 = Math.sqrt((3.0 / 7.0) + ((2.0 / 7.0) * Math.sqrt(6.0 / 5.0)));
            p = new double[n];
            p[0] = p1;
            p[1] = p2;
            p[2] = p3;
            p[3] = p4;
        }
    }

    double[][] tabksi;
    double[][] ksi;
    double[][] eta;
    double[] p;
    int n;

    void importData(int n) {
        for (int j = 0; j < n * n; j++) {
            for (int i = 0; i < 4; i++) {
                ksi[j][i] = fun_ksi[i].apply(p[(int) Math.floor(j / n)]);
                eta[j][i] = fun_eta[i].apply(p[j % n]);
            }
        }
    }

    Function<Double, Double>[] fun_ksi = new Function[4];
    Function<Double, Double>[] fun_eta = new Function[4];

    void fun() {
        fun_eta[0] = x -> -0.25 * (1 - x);
        fun_eta[1] = x -> -0.25 * (1 + x);
        fun_eta[2] = x -> 0.25 * (1 + x);
        fun_eta[3] = x -> 0.25 * (1 - x);

        fun_ksi[0] = x -> -0.25 * (1 - x);
        fun_ksi[1] = x -> 0.25 * (1 - x);
        fun_ksi[2] = x -> 0.25 * (1 + x);
        fun_ksi[3] = x -> -0.25 * (1 + x);
    }

    void print(double[][] tab) {
        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%.6f" + " ", tab[i][j]);
            }
            System.out.println();
        }
    }
}



