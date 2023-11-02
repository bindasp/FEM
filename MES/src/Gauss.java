import java.util.ArrayList;

public class Gauss {
    ArrayList<Double> punkty = new ArrayList<>();
    ArrayList<Double> wspolczynniki = new ArrayList<>();
    int ilosc = 0;

    Gauss(int k) {
        if (k == 2) {
            ilosc = 2;
            punkty.add(Math.sqrt(1.0 / 3.0));
            punkty.add(Math.sqrt(1.0 / 3.0) * -1.0);
            wspolczynniki.add(1.0);
            wspolczynniki.add(1.0);

        } else if (k == 3) {
            ilosc = 3;
            punkty.add(Math.sqrt(3.0 / 5.0) * -1.0);
            punkty.add(0.0);
            punkty.add(Math.sqrt(3.0 / 5.0));
            wspolczynniki.add(5.0 / 9.0);
            wspolczynniki.add(8.0 / 9.0);
            wspolczynniki.add(5.0 / 9.0);

        } else if (k == 4) {
            ilosc = 4;
            punkty.add((Math.sqrt((3.0 / 7.0) - (2.0 / 7.0) * Math.sqrt(6.0 / 5.0))) * -1.0);
            punkty.add((Math.sqrt((3.0 / 7.0) + (2.0 / 7.0) * Math.sqrt(6.0 / 5.0))) * -1.0);
            punkty.add(Math.sqrt((3.0 / 7.0) - (2.0 / 7.0) * Math.sqrt(6.0 / 5.0)));
            punkty.add(Math.sqrt((3.0 / 7.0) + (2.0 / 7.0) * Math.sqrt(6.0 / 5.0)));

            wspolczynniki.add((18.0 + Math.sqrt(30.0)) / 36);
            wspolczynniki.add((18.0 - Math.sqrt(30.0)) / 36);
            wspolczynniki.add(((18.0 + Math.sqrt(30.0)) / 36));
            wspolczynniki.add(((18.0 - Math.sqrt(30.0)) / 36));
        } else
            System.out.println("Błędna liczba punktów");
    }

    public double solution1d(Fun1d fun) {
        double sum = 0;
        for (int i = 0; i < ilosc; i++) {
            sum += wspolczynniki.get(i) * fun.fx(punkty.get(i));
        }
        return sum;
    }

    public double solution2d(Fun2d fun) {
        double sum = 0;
        for (int i = 0; i < ilosc; i++) {
            for (int j = 0; j < ilosc; j++) {
                sum += wspolczynniki.get(i) * wspolczynniki.get(j) * fun.fx(punkty.get(i), punkty.get(j));
            }
        }
        return sum;
    }
}

interface Fun1d {
    double fx(double x);
}

interface Fun2d {
    double fx(double x, double y);
}

