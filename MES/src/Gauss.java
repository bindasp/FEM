import java.util.ArrayList;

public class Gauss {
    ArrayList<Double> points = new ArrayList<>();
    ArrayList<Double> weights = new ArrayList<>();
    int quantity = 0;

    Gauss(int k) {
        if (k == 2) {
            quantity = 2;
            points.add(Math.sqrt(1.0 / 3.0) * -1);
            points.add(Math.sqrt(1.0 / 3.0));
            weights.add(1.0);
            weights.add(1.0);

        } else if (k == 3) {
            quantity = 3;
            points.add(Math.sqrt(3.0 / 5.0) * -1.0);
            points.add(0.0);
            points.add(Math.sqrt(3.0 / 5.0));
            weights.add(5.0 / 9.0);
            weights.add(8.0 / 9.0);
            weights.add(5.0 / 9.0);

        } else if (k == 4) {
            quantity = 4;
            points.add((Math.sqrt((3.0 / 7.0) + (2.0 / 7.0) * Math.sqrt(6.0 / 5.0))) * -1.0);
            points.add((Math.sqrt((3.0 / 7.0) - (2.0 / 7.0) * Math.sqrt(6.0 / 5.0))) * -1.0);
            points.add(Math.sqrt((3.0 / 7.0) - (2.0 / 7.0) * Math.sqrt(6.0 / 5.0)));
            points.add(Math.sqrt((3.0 / 7.0) + (2.0 / 7.0) * Math.sqrt(6.0 / 5.0)));

            weights.add((18.0 - Math.sqrt(30.0)) / 36);
            weights.add((18.0 + Math.sqrt(30.0)) / 36);
            weights.add(((18.0 + Math.sqrt(30.0)) / 36));
            weights.add(((18.0 - Math.sqrt(30.0)) / 36));
        } else
            System.out.println("Błędna liczba punktów");
    }

    public double integration1d(Fun1d fun) {
        double sum = 0;
        for (int i = 0; i < quantity; i++) {
            sum += weights.get(i) * fun.fx(points.get(i));
        }
        return sum;
    }

    public double integration2d(Fun2d fun) {
        double sum = 0;
        for (int i = 0; i < quantity; i++) {
            for (int j = 0; j < quantity; j++) {
                sum += weights.get(i) * weights.get(j) * fun.fx(points.get(i), points.get(j));
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

