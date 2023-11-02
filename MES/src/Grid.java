import java.io.File;
import java.util.Scanner;

public class Grid {
    public Element[] elements;
    public Node[] nodes;
    Scanner scanner;

    public Grid(int elementsNumber, int nodesNumber, String filePath) {
        File file = new File(filePath);
        try {
            this.scanner = new Scanner(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        elements = new Element[elementsNumber];
        nodes = new Node[nodesNumber];
    }

    public void setNodes() {
        while (!(scanner.next().equals("Node"))) {
            String line = scanner.nextLine();

        }

        for (int i = 0; i < nodes.length; i++) {
            Node node = new Node();
            scanner.next();
            node.x = Double.parseDouble(scanner.next().replace(",", ""));
            node.y = Double.parseDouble(scanner.next().replace(",", ""));
            nodes[i] = node;
        }
    }

    public void printNodes() {
        for (int i = 0; i < nodes.length; i++) {
            System.out.println(i + " x: " + nodes[i].x + " y: " + nodes[i].y);
        }
    }

    public void setElements() {

        scanner.next();
        scanner.next();
        for (int i = 0; i < elements.length; i++) {
            Element element = new Element();
            element.ID = new int[4];
            scanner.next();
            element.ID[0] = Integer.parseInt(scanner.next().replace(",", ""));
            element.ID[1] = Integer.parseInt(scanner.next().replace(",", ""));
            element.ID[2] = Integer.parseInt(scanner.next().replace(",", ""));
            element.ID[3] = Integer.parseInt(scanner.next().replace(",", ""));
            elements[i] = element;

        }
    }

    public void printElements() {
        for (int i = 0; i < elements.length; i++) {
            System.out.println(i + ": " + elements[i].ID[0] + " " + elements[i].ID[1] + " " + elements[i].ID[2] + " " + elements[i].ID[3]);
        }
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
