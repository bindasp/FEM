import java.util.ArrayList;
import java.util.Arrays;

public class MatrixIntegrationH {
    MatrixIntegrationH(int n, ElementUniwersalny elementUniwersalny, double[] x, double[] y, double conductivity) {
        this.n = n;
        this.elementUniwersalny = elementUniwersalny;
        this.x = x;
        this.y = y;
        dxdksi = new double[n * n];
        dxdeta = new double[n * n];
        dydksi = new double[n * n];
        dydeta = new double[n * n];
        tabx = new double[n * n][4];
        taby = new double[n * n][4];
        gauss = new Gauss(n);
        d = new double[n * n];
        this.conductivity = conductivity;
        for (int i = 0; i < n * n; i++) {
            hpcx.add(new double[4][4]);
            hpcy.add(new double[4][4]);

        }
    }

    public int n;
    public ElementUniwersalny elementUniwersalny;
    public double[] dxdksi, dxdeta, dydksi, dydeta;
    double[] x;
    double[] y;
    double[][] tabx;
    double[][] taby;
    double[][] res = new double[4][4];
    ArrayList<double[][]> hpcx = new ArrayList<>();
    ArrayList<double[][]> hpcy = new ArrayList<>();
    Gauss gauss;
    double[] d;
    double conductivity;

    public double[][] calculate() {
        for (int i = 0; i < n * n; i++) {
            dxdksi[i] = 0.0;
            dxdeta[i] = 0.0;
            dydksi[i] = 0.0;
            dydeta[i] = 0.0;
            for (int j = 0; j < 4; j++) {
                dxdksi[i] += elementUniwersalny.ksi[i][j] * x[j];
                dxdeta[i] += elementUniwersalny.eta[i][j] * x[j];
                dydksi[i] += elementUniwersalny.ksi[i][j] * y[j];
                dydeta[i] += elementUniwersalny.eta[i][j] * y[j];
            }
//            elementUniwersalny.print(elementUniwersalny.ksi);
//            System.out.println();
//            elementUniwersalny.print(elementUniwersalny.eta);

//            System.out.println(dxdksi[i] + " " + dydksi[i]);
//            System.out.println(dxdeta[i] + " " + dydeta[i]);

            d[i] = (dxdksi[i] * dydeta[i] - (dxdeta[i] * dydksi[i]));
//            System.out.println("DetJ: " + d[i]);
            d[i] = 1.0 / d[i];
//            System.out.println("1/d");
//            System.out.println(d[i]);


            //odwrócenie macierzy
///////////////////////////////////////////

            dxdksi[i] *= d[i];
            dydksi[i] *= -1.0 * d[i];
            dxdeta[i] *= -1.0 * d[i];
            dydeta[i] *= d[i];
            
            double temp;
            temp = dxdksi[i];
            dxdksi[i] = dydeta[i];
            dydeta[i] = temp;

            double temp1;
            temp1 = dydksi[i];
            dydksi[i] = dxdeta[i];
            dxdeta[i] = temp1;
///////////////////////////////////////////

//            System.out.println(dxdksi[i]);
//            System.out.println(dxdeta[i]);
//            System.out.println(dydksi[i]);
//            System.out.println(dydeta[i]);


        }

        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < 4; j++) {
                tabx[i][j] = dxdksi[i] * elementUniwersalny.ksi[i][j] + dxdeta[i] * elementUniwersalny.eta[i][j];
                taby[i][j] = dydeta[i] * elementUniwersalny.eta[i][j] + dydksi[i] * elementUniwersalny.ksi[i][j];

            }
        }

        double[][] tabxt = transpose(tabx);
        double[][] tabyt = transpose(taby);

        for (int k = 0; k < n * n; k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {

                    hpcx.get(k)[i][j] = tabx[k][i] * tabxt[j][k];
                    hpcy.get(k)[i][j] = taby[k][i] * tabyt[j][k];
                    hpcx.get(k)[i][j] += hpcy.get(k)[i][j];
                    hpcx.get(k)[i][j] *= conductivity * (1.0 / d[k]);
                    res[i][j] += (hpcx.get(k)[i][j] * gauss.wspolczynniki.get(k % n) * gauss.wspolczynniki.get((int) Math.floor(k / n)));
                }
            }
        }
//        elementUniwersalny.print(tabx);
//        System.out.println();
//        elementUniwersalny.print(taby);
//        System.out.println();
//        this.print(res);
        //System.out.println(Arrays.deepToString(tabx));
        return res;
    }

    public double[][] transpose(double[][] array) {
        int rows = array.length;
        int columns = array[0].length;

        double[][] transposedArray = new double[columns][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                transposedArray[j][i] = array[i][j];
            }
        }
        return transposedArray;
    }

    void print(double[][] tab) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%.6f" + " ", tab[i][j]);
            }
            System.out.println();
        }
    }
}
