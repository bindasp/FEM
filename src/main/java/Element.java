public class Element {
    int[] ID = new int[4];
    public double[][] h;
    public double[][] hbc;
    public double [] P;
    public double [][] C;
    public double [][] H;

    public Element() {
        h = new double[4][4];
        hbc = new double[4][4];
        P = new double[4];
        H = new double[4][4];
        C = new double[4][4];
    }

}
