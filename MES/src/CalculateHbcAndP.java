import java.util.ArrayList;

public class CalculateHbc {
    int n;
    double alfa;
    double tot;
    double [][] P;
    ElementUniwersalny elementUniwersalny;

    CalculateHbc(int n, ElementUniwersalny elementUniwersalny, double alfa, double tot) {
        this.alfa = alfa;
        this.tot = tot;
        P = new double[4][4];

        sum = new ArrayList<>();
        this.n = n;
        this.elementUniwersalny = elementUniwersalny;
        for (int i = 0; i < n; i++) {
            sum.add(new double[4][4]);
        }
        wynik = new double[4][4][4];
        det = new double[4];

        hbc = new double[4][4];
    }
    ArrayList<double[][]> sum;
    double [][][] wynik;
    double [] det;
    double [][] hbc;

    public double[][] calculate(Node [] node, double [] Pw) {
        Gauss gauss = new Gauss(n);
        for (int i = 0; i < 4; i++) {
            if(i==3)
            {
                det[i] = Math.sqrt(Math.pow(node[0].x - node[i].x,2) + Math.pow(node[0].y - node[i].y,2))/2;
            }
            else
                det[i] = Math.sqrt(Math.pow(node[i+1].x - node[i].x, 2) +Math.pow(node[i+1].y - node[i].y, 2))/2;
        }

        for(int l=0; l<4; l++) {
            if (node[l % 4].BC != 0 && node[(l + 1) % 4].BC == node[l % 4].BC) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 4; k++) {

                            sum.get(i)[j][k] = elementUniwersalny.surface[l].N[i][j] * elementUniwersalny.surface[l].N[i][k] * alfa * det[l] * gauss.wspolczynniki.get(i);
                            hbc[j][k] +=  sum.get(i)[j][k];
                            //wynik[l][j][k] += sum.get(i)[j][k];
                        }

                        //P[l][j] += elementUniwersalny.surface[l].N[i][j] * tot * gauss.wspolczynniki.get(i) * alfa * det[l];
                        Pw[j] += elementUniwersalny.surface[l].N[i][j] * tot * gauss.wspolczynniki.get(i) * alfa * det[l];
                    }
                }
            }
        }

        return hbc;
    }

}
