public class CalculateHbcAndP {
    int n;
    double alfa;
    double tot;

    UniversalElement universalElement;
    CalculateHbcAndP(int n, UniversalElement universalElement, double alfa, double tot) {
        this.alfa = alfa;
        this.tot = tot;

        this.n = n;
        this.universalElement = universalElement;

        det = new double[4];
        hbc = new double[4][4];
    }

    double [] det;
    double [][] hbc;

    public double[][] calculate(Node [] node, double [] P) {
        Gauss gauss = new Gauss(n);
        //Obliczenie det[J] = dx/dksi = L/2
        for (int i = 0; i < 4; i++) {
                det[i] = Math.sqrt(Math.pow(node[(i+1) % 4].x - node[i % 4].x, 2) +Math.pow(node[(i+1) % 4].y - node[i % 4].y, 2))/2;
        }

        for(int l=0; l<4; l++) {
            //Sprawdzenie warunku brzegowego dla ściany
            if (node[l % 4].BC != 0 && node[(l + 1) % 4].BC == node[l % 4].BC) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 4; k++) {
                            //Obliczanie macierzy Hbc = alfa*({N}{N}^T)*dS
                            hbc[j][k] +=  (universalElement.surface[l].N[i][j] * universalElement.surface[l].N[i][k]) //Wyliczenie macierzy dla każdej ściany
                                    * alfa * det[l] * gauss.weights.get(i); //Mnożenie macierzy przez współczynnik konwekcji, wagi i wyznacznik det[j]

                        }
                        //Obliczanie wektora P = alfa*{N}*tot*dS
                        P[j] += universalElement.surface[l].N[i][j] * tot * gauss.weights.get(i) * alfa * det[l];
                    }

                }
            }

        }
        return hbc;
    }

}
