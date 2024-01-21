import org.apache.commons.math3.linear.RealVector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportData {
    public ExportData(){

    }

    public BufferedWriter bufferedWriter;

    public void clearDirectory(String directoryPath){
        File directory = new File(directoryPath);

        File[] directoryFiles = directory.listFiles();

        if(directoryFiles != null){
            for(File file : directoryFiles){
                if(file.isFile()){
                    file.delete();
                }
            }
        }
    }

    public void export(GlobalData data, Grid grid, RealVector temp, String filePath) throws IOException {
        bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        bufferedWriter.write("# vtk DataFile Version 2.0");
        bufferedWriter.newLine();
        bufferedWriter.write("Unstructured Grid Example");
        bufferedWriter.newLine();
        bufferedWriter.write("ASCII");
        bufferedWriter.newLine();
        bufferedWriter.write("DATASET UNSTRUCTURED_GRID");
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write("POINTS " + data.nodesNumber + " float");
        bufferedWriter.newLine();
        for (Node node : grid.nodes) {
            bufferedWriter.write(node.x + " " + node.y + " 0");
            bufferedWriter.newLine();
        }
        bufferedWriter.newLine();
        bufferedWriter.write("CELLS " + data.elementsNumber + " " + data.elementsNumber * 5);
        bufferedWriter.newLine();
        for (Element e : grid.elements) {
            bufferedWriter.write("4 " + (e.ID[0]-1) + " " + (e.ID[1]-1) + " " + (e.ID[2]-1) + " " + (e.ID[3]-1));
            bufferedWriter.newLine();
        }

        bufferedWriter.newLine();
        bufferedWriter.write("CELL_TYPES " + data.elementsNumber);
        bufferedWriter.newLine();
        for (Element e : grid.elements) {
            bufferedWriter.write("9");
            bufferedWriter.newLine();
        }
        bufferedWriter.newLine();
        bufferedWriter.write("POINT_DATA " + data.nodesNumber);
        bufferedWriter.newLine();
        bufferedWriter.write("SCALARS Temp float 1");
        bufferedWriter.newLine();
        bufferedWriter.write("LOOKUP_TABLE default");
        bufferedWriter.newLine();


        for (int i = 0; i < data.nodesNumber; i++) {
            bufferedWriter.write("" + temp.getEntry(i));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
    }

}
