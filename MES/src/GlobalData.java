import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class GlobalData {
    GlobalData(String filePath)
    {

        File file = new File(filePath);
        try{
            this.scanner = new Scanner(file);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    public int simulationTime;
    public int simulationStepTime;
    public int conductivity;
    public int alfa;
    public int tot;
    public int initialTemp;
    public int density;
    public int specificHeat;
    public int nodesNumber;
    public int elementsNumber;


    Scanner scanner;

    public void printData()
    {
        System.out.println(simulationTime);
        System.out.println(simulationStepTime);
        System.out.println(conductivity);
        System.out.println(alfa);
        System.out.println(tot);
        System.out.println(initialTemp);
        System.out.println(density);
        System.out.println(specificHeat);
        System.out.println(nodesNumber);
        System.out.println(elementsNumber);
    }
    public void setGlobalData()
    {
        scanner.next();
        this.simulationTime = Integer.parseInt(scanner.next());
        scanner.next();
        this.simulationStepTime = Integer.parseInt(scanner.next());
        scanner.next();
        this.conductivity = Integer.parseInt(scanner.next());
        scanner.next();
        this.alfa = Integer.parseInt(scanner.next());
        scanner.next();
        this.tot = Integer.parseInt(scanner.next());
        scanner.next();
        this.initialTemp = Integer.parseInt(scanner.next());scanner.next();
        this.density = Integer.parseInt(scanner.next());
        scanner.next();
        this.specificHeat= Integer.parseInt(scanner.next());
        scanner.next();
        scanner.next();
        this.nodesNumber= Integer.parseInt(scanner.next());
        scanner.next();
        scanner.next();
        this.elementsNumber= Integer.parseInt(scanner.next());

    }
}
