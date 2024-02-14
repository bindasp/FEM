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
            System.out.println(e.getMessage());
        }
        elements = new Element[elementsNumber];
        nodes = new Node[nodesNumber];
    }

    public void setNodes() {
        while (!(scanner.next().equals("Node"))) {
            scanner.nextLine();
        }

        for (int i = 0; i < nodes.length; i++) {
            Node node = new Node();
            scanner.next();
            node.x = Double.parseDouble(scanner.next().replace(",", ""));
            node.y = Double.parseDouble(scanner.next().replace(",", ""));
            nodes[i] = node;
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
    public void setBC() {
        scanner.next();
        while (scanner.hasNext()) {
            nodes[Integer.parseInt(scanner.next().replace(",", "")) - 1].BC = 1;
        }
    }

    void print(double[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab.length; j++) {
                System.out.printf("%.6f" + " ", tab[i][j]);
            }
            System.out.println();
        }
    }

}
