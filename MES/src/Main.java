import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filePath = "E:\\studia\\semestr 5\\MES\\MES\\Files\\dane1.txt";
        String filePath1 = "E:\\studia\\semestr 5\\MES\\MES\\Files\\dane2.txt";
        Scanner scanner = new Scanner(System.in);
        GlobalData data = new GlobalData(filePath1);
        data.setGlobalData();
        data.printData();
        Grid grid = new Grid(data.elementsNumber, data.nodesNumber, filePath1);
        grid.setNodes();
        //   grid.printNodes();
        grid.setElements();
        //  grid.printElements();

        Gauss gauss = new Gauss(4);
        System.out.println(gauss.solution1d(x -> 5 * Math.pow(x, 2) + 3 * x + 6));
        Gauss gauss2d = new Gauss(4);
        System.out.println(gauss.solution2d((x, y) -> (5 * x * x * y * y) + (3.0 * x * y) + 6.0));

        System.out.println("Podaj liczbę punktów");
        int n = scanner.nextInt();

        ElementUniwersalny element = new ElementUniwersalny(n);
        element.importData(n);
//        System.out.println("dN/dKsi: ");
//        element.print(element.ksi);
//
//        System.out.println("dN/dEta: ");
//        element.print(element.eta);
//        System.out.println();
////
//        double[] x = {0.0, 0.025, 0.025, 0.0};
//        double[] y = {0.0, 0.0, 0.025, 0.025};

        for (Element e : grid.elements) {
            double[] x = new double[4];
            double[] y = new double[4];

            for (int i = 0; i < 4; i++) {
                x[i] = grid.nodes[e.ID[i] - 1].x;
                y[i] = grid.nodes[e.ID[i] - 1].y;
            }

            MatrixIntegrationH m = new MatrixIntegrationH(n, element, x, y, data.conductivity);
            e.h = m.calculate();
        }
        for (Element e : grid.elements) {
            grid.print(e.h);
            System.out.println();
        }
    }
}
