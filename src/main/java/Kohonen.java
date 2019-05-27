import java.util.Random;

public class Kohonen implements Algorithm{

    private double lambdaMin = 0.01d;
    private double lambdaMax = 10.0d;
    private double learningRateMin = 0.001d;
    private double learningRateMax = 0.800d;
    private int iters;
    private double[][] neurons;

    public Kohonen(final int numberOfNeurons, final int numberOfIterations, double lambdaMin, double lambdaMax, double learningRateMin, double learningRateMax) {
        this.iters = numberOfIterations;
        this.neurons = new double[numberOfNeurons][2];
        this.lambdaMin = lambdaMin;
        this.lambdaMax = lambdaMax;
        this.learningRateMin = learningRateMin;
        this.learningRateMax = learningRateMax;
        Random rnd = new Random();
        for (int i = 0; i < numberOfNeurons; i++) {
            neurons[i][0] = 0.25d + rnd.nextDouble() / 2;
            neurons[i][1] = 0.25d + rnd.nextDouble() / 2;
        }
    }

    public double[] chooseWinner(double[] x) {
        double[] winner = neurons[0];
        double distance = Operations.distance2D(x, winner);
        for (int i = 1; i < neurons.length; i++) {
            if (Operations.distance2D(x, neurons[i]) < distance) {
                distance = Operations.distance2D(x, neurons[i]);
                winner = neurons[i];
            }
        }
        return winner;
    }

    @Override
    public void adaptWeights(int i, double[][] x) {
        for (int k = 0; k < x.length; k++) {

            final double[] winner = chooseWinner(x[k]);
//            final double distance = Operations.distance2D(x[k], winner);



            for (int j = 0; j < neurons.length; j++) {
//                if (neurons[j] == winner) continue;
                double dist = Operations.distance2D(winner, neurons[j]);
                neurons[j][0] += learningRate(i) * gFunc(dist, i) * (x[k][0] - neurons[j][0]);
                neurons[j][1] += learningRate(i) * gFunc(dist, i) * (x[k][1] - neurons[j][1]);
            }
        }
    }

    @Override
    public double[][] getNeurons() {
        return neurons;
    }

    private double gFunc(final double d, final int k) {
        return Math.exp((-d * d) / (2 * lambda(k) * lambda(k)));
    }

    private double lambda(final int k) {
        return lambdaMax * Math.pow((lambdaMin / lambdaMax), (k / iters));
    }

    private double learningRate(final int k) {
        return learningRateMax * Math.pow((learningRateMin / learningRateMax), (k / iters));
    }
}
