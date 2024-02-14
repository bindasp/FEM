import java.util.ArrayList;

public class CalculateHandC {
    CalculateHandC(int n, UniversalElement universalElement, Node[] nodes, double conductivity) {
        this.n = n;
        this.universalElement = universalElement;

        this.nodes = nodes;
        dxdksi = new double[n * n];
        dxdeta = new double[n * n];
        dydksi = new double[n * n];
        dydeta = new double[n * n];
        dNdx = new double[n * n][4];
        dNdy = new double[n * n][4];
        gauss = new Gauss(n);
        d = new double[n * n];
        this.conductivity = conductivity;
        for (int i = 0; i < n * n; i++) {
            Hpc.add(new double[4][4]);
        }
    }

    public int n;
    public UniversalElement universalElement;
    public double[] dxdksi, dxdeta, dydksi, dydeta;
    Node[] nodes;
    double[][] dNdx;
    double[][] dNdy;
    double[][] H = new double[4][4];
    double[][] C = new double[4][4];
    ArrayList<double[][]> Hpc = new ArrayList<>();
    Gauss gauss;
    double[] d;
    double conductivity;

    public double[][] calculate() {
        for (int i = 0; i < n * n; i++) {
            //Obliczanie macierzy jakobiego
            for (int j = 0; j < 4; j++) {
                dxdksi[i] += universalElement.ksi[i][j] * nodes[j].x;
                dxdeta[i] += universalElement.eta[i][j] * nodes[j].x;
                dydksi[i] += universalElement.ksi[i][j] * nodes[j].y;
                dydeta[i] += universalElement.eta[i][j] * nodes[j].y;
            }

            //Wyliczanie wyznacznika det[j] jakobianu
            d[i] = (dxdksi[i] * dydeta[i] - (dxdeta[i] * dydksi[i]));

            //Odwracanie wyznacznika jakobianu
            d[i] = 1.0 / d[i];

            //odwracanie macierzy jakobiego - aby wyliczyć dN/dx i dN/dy
            //--------------------------------------------------------------------------
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
            //--------------------------------------------------------------------------
        }

        //Obliczanie macierzy dN/dx i dN/dy - mnożenie odwróconego jakobianu przez wektor [dN/dksi,dNdeta]
        //--------------------------------------------------------------------------
        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < 4; j++) {
                dNdx[i][j] = dxdksi[i] * universalElement.ksi[i][j] + dxdeta[i] * universalElement.eta[i][j];
                dNdy[i][j] = dydeta[i] * universalElement.eta[i][j] + dydksi[i] * universalElement.ksi[i][j];
                System.out.print(dNdy[i][j] + " ");
            }
            System.out.println();
        }


        //Obliczanie macierzy H = k(t)* ({dN/dx}*{dN/dx}^T + {dN/dy}*{dN/dy}^T)*dV
        //--------------------------------------------------------------------------
        for (int k = 0; k < n * n; k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    H[i][j] += (((dNdx[k][i] * dNdx[k][j]) + (dNdy[k][i] * dNdy[k][j])) //Wyliczanie macierzy H dla punktów całkowania
                            * conductivity * (1.0 / d[k]) * gauss.weights.get(k % n) * gauss.weights.get((int) (double) (k / n)));
                }
            }

        }
        return H;
    }

    public double[][] calculateC(double SpecificHeat, double Density) {
        //Obliczanie macierzy C = Ro*cp*({N}{N}^T)*dV
        //--------------------------------------------------------------------------
        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    C[j][k] += universalElement.N[i][j] * universalElement.N[i][k] //Wyliczanie macierzy C dla punktów całkowania
                            * SpecificHeat * Density * (1.0 / d[i]) * gauss.weights.get(i % n) * gauss.weights.get((int) (double) (i / n));
                }
            }
        }
        return C;
    }

}
