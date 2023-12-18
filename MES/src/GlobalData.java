import java.io.File;
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
            System.out.println(e.getMessage());
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
        System.out.println("Simulation time:" + " " + simulationTime);
        System.out.println("Simulation step time:" + " " +simulationStepTime);
        System.out.println("Conductivity:" + " " +conductivity);
        System.out.println("Alfa:" + " " +alfa);
        System.out.println("Tot:" + " " +tot);
        System.out.println("Initial temp:" + " " +initialTemp);
        System.out.println("Density:" + " " +density);
        System.out.println("Specific heat:" + " " +specificHeat);
        System.out.println("Nodes number:" + " " +nodesNumber);
        System.out.println("Elements number:" + " " +elementsNumber);
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
