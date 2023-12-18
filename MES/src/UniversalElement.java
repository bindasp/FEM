import java.util.function.BiFunction;
import java.util.function.Function;

public class UniversalElement {
    UniversalElement(int n) {
        ksi = new double[n * n][4];
        eta = new double[n * n][4];
        tabksi = new double[n * n][4];
        this.n = n;
        gauss = new Gauss(n);
        surface = new Surface[4];

        for (int i = 0; i < 4; i++) {
            surface[i] = new Surface(n);
        }
        fun();
        N = new double[n*n][4];
    }

    double[][] tabksi;
    double[][] ksi;
    double[][] eta;
    int n;
    public Surface[] surface;
    double [][] N;
    Gauss gauss;

    void importData(int n) {
        for (int j = 0; j < n * n; j++) {
            for (int i = 0; i < 4; i++) {
                //Wartość pochodnej funkcji kształtu po ksi
                ksi[j][i] = fun_ksi[i].apply(gauss.points.get((int) Math.floor((double) j / n)));
                //Wartość pochodnej funkcji kształtu po eta
                eta[j][i] = fun_eta[i].apply(gauss.points.get(j % n));
            }
        }

        for(int i=0; i<n*n; i++)
        {
            for (int j = 0; j < 4; j++) {
                N[i][j] = funN[j].apply(gauss.points.get(i%n) , gauss.points.get((int) Math.floor((double)i/n)));
            }
        }

    }
    Function<Double, Double>[] fun_ksi = new Function[4];
    Function<Double, Double>[] fun_eta = new Function[4];
    BiFunction<Double, Double, Double>[] funN = new BiFunction[4];

    void fun() {
        //Pochodne funkcji kształu po eta
        fun_eta[0] = ksi -> -0.25 * (1 - ksi);
        fun_eta[1] = ksi -> -0.25 * (1 + ksi);
        fun_eta[2] = ksi -> 0.25 * (1 + ksi);
        fun_eta[3] = ksi -> 0.25 * (1 - ksi);

        //Pochodne funkcji kształtu po ksi
        fun_ksi[0] = eta -> -0.25 * (1 - eta);
        fun_ksi[1] = eta -> 0.25 * (1 - eta);
        fun_ksi[2] = eta -> 0.25 * (1 + eta);
        fun_ksi[3] = eta -> -0.25 * (1 + eta);

        //Funkcje kształtu
        funN[0] = (ksi, eta) -> 0.25 * (1 - ksi) * (1 - eta);
        funN[1] = (ksi, eta) -> 0.25 * (1 + ksi) * (1 - eta);
        funN[2] = (ksi, eta) -> 0.25 * (1 + ksi) * (1 + eta);
        funN[3] = (ksi, eta) -> 0.25 * (1 - ksi) * (1 + eta);
    }

    void setSurfaces() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 4; j++) {
                //Wartości funkcji kształtu na powierzchni
                surface[0].N[i][j] = funN[j].apply(gauss.points.get(i), -1.0);
                surface[1].N[i][j] = funN[j].apply(1.0, gauss.points.get(i));
                surface[2].N[i][j] = funN[j].apply(gauss.points.get((n-1)-i), 1.0);
                surface[3].N[i][j] = funN[j].apply(-1.0, gauss.points.get((n-1)-i));
            }
        }
    }

}



