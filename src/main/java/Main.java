import org.apache.commons.math3.linear.RealVector;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Wybierz plik");
        Scanner file = new Scanner(System.in);

        int a = file.nextInt();
        String filePath;

        //Wybór pliku z danymi
        //--------------------------------------------------------------------------
        if (a == 1) {
            filePath = "E:\\studia\\semestr 5\\MES\\MES\\Files\\dane1.txt";
        } else if (a == 2) {
            filePath = "E:\\studia\\semestr 5\\MES\\MES\\Files\\dane2.txt";
        } else if (a == 3) {
            filePath = "E:\\studia\\semestr 5\\MES\\MES\\Files\\dane3.txt";
        } else
            filePath = "E:\\studia\\semestr 5\\MES\\MES\\Files\\dane4.txt";

        //Wczytywanie i wyświetlanie danych
        //--------------------------------------------------------------------------
        Scanner scanner = new Scanner(System.in);
        GlobalData data = new GlobalData(filePath);
        data.setGlobalData();
        data.printData();
        Grid grid = new Grid(data.elementsNumber, data.nodesNumber, filePath);
        grid.setNodes();
        //grid.printNodes();
        grid.setElements();
        //  grid.printElements();
        grid.setBC();
        //--------------------------------------------------------------------------

        System.out.println("Podaj liczbę punktów");
        int n = scanner.nextInt();
        Gauss gauss = new Gauss(n);

        //Tworzenie elementu uniwersalnego i wczytanie danych
        //--------------------------------------------------------------------------
        UniversalElement universalElement = new UniversalElement(n);
        universalElement.importData(n);
        universalElement.setSurfaces();
        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(universalElement.eta[i][j] + " ");
            }
            System.out.println();
        }
        //--------------------------------------------------------------------------

        Soe soe = new Soe(data.nodesNumber);

        //Wyliczanie macierzy H, Hbc, C i wektora P
        //--------------------------------------------------------------------------
        for (Element e : grid.elements) {

            Node[] nodes = new Node[4];

            for (int i = 0; i < 4; i++) {
                //Zapisanie odpowiednich węzłów
                nodes[i] = grid.nodes[e.ID[i] - 1];
            }
            CalculateHbcAndP calculateHbcAndP = new CalculateHbcAndP(n, universalElement, data.alfa, data.tot);
            CalculateHandC m = new CalculateHandC(n, universalElement, nodes, data.conductivity);
            e.h = m.calculate();
            e.hbc = calculateHbcAndP.calculate(nodes, e.P);
            e.C = m.calculateC(data.specificHeat, data.density);
            //Wyliczenie macierzy H = h + Hbc
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    e.H[i][j] = e.h[i][j] + e.hbc[i][j];
                }
            }
            //Agregacja
            soe.agregate(e);

        }

        //Wypisywanie wyników
        //--------------------------------------------------------------------------
        int licznik = 1;
        for (Element e : grid.elements) {
            System.out.println("---------------------------------------------------");
            System.out.println("Element nr. " + licznik);
            System.out.println("Wektor P:");
            System.out.println(Arrays.toString(e.P));
            System.out.println("Macierz H:");
            grid.print(e.h);
            System.out.println("Macierz Hbc:");
            grid.print(e.hbc);
            System.out.println("Macierz C:");
            grid.print(e.C);
            licznik++;
        }

        System.out.println();

        //Macierz globalna H
        //--------------------------------------------------------------------------
        for (int i = 0; i < data.nodesNumber; i++) {
            for (int j = 0; j < data.nodesNumber; j++) {
                System.out.printf("%8.3f", soe.Hg[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        //Wektor globalny P
        //--------------------------------------------------------------------------
        System.out.println("Wektor P globalny");
        for (int i = 0; i < data.nodesNumber; i++) {
            System.out.printf("%.3f ", soe.Pg[i]);
        }
        System.out.println();
        System.out.println();

        //Macierz globalna C
        //--------------------------------------------------------------------------
        System.out.println("Macierz C globalna");
        for (int i = 0; i < data.nodesNumber; i++) {
            for (int j = 0; j < data.nodesNumber; j++) {
                System.out.printf("%8.3f ", soe.Cg[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

        //Wczytanie temperatury w chwili t0
        //--------------------------------------------------------------------------
        double[] initialTemp = new double[data.nodesNumber];
        for (int i = 0; i < data.nodesNumber; i++) {
            initialTemp[i] = data.initialTemp;
        }

        //Obliczanie temperatury w węzłach bez uwzględnienia kroku czasowego
        //--------------------------------------------------------------------------
        RealVector T = soe.LUDecomposition(soe.Hg, soe.Pg);
        for (int i = 0; i < data.nodesNumber; i++) {
            System.out.println("T" + i + " " + T.getEntry(i));
        }

        //Obliczanie temperatury w węzłach z uwzględnieniem kroku czasowego
        //--------------------------------------------------------------------------
        RealVector Tw = null;

        int l = data.simulationStepTime;
        System.out.println("Wyniki układu równań");
        System.out.println("Podaj liczbę kroków");
        Scanner scanner1 = new Scanner(System.in);
        double b = scanner1.nextInt();
        ExportData exportData = new ExportData();
        //Czyszczenie katalogu
        exportData.clearDirectory("E:\\\\studia\\\\semestr 5\\\\MES\\\\MES\\\\wyniki");
        for (int i = 0; i < b; i++) {
            //Obliczanie temperatury końcowej
            Tw = soe.calculate(data.simulationStepTime, initialTemp);
            //Zapis temperatury końcowej jako temperatura początkowa dla następnego kroku
            initialTemp = Tw.toArray();

            //Eksportowanie danych do plikku .vtk
            exportData.export(data, grid, Tw, "E:\\studia\\semestr 5\\MES\\MES\\wyniki\\foo" + i + ".vtk");

            double tmin = Arrays.stream(Tw.toArray()).min().orElse(0);
            double tmax = Arrays.stream(Tw.toArray()).max().orElse(0);
            System.out.println("Temperatura po czasie: " + l + "s");
            System.out.println("Tmin = " + tmin + " Tmax = " + tmax);

            l += data.simulationStepTime;
        }
        //--------------------------------------------------------------------------


    }
}


