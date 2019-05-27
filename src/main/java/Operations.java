public class Operations {


    public static double distance2D(double[] x, double[] y) {
        final double sum = (x[0] - y[0]) * (x[0] - y[0]) + (x[1] - y[1]) * (x[1] - y[1]);
        return Math.sqrt(sum);
    }

}
