import java.util.Random;

public class NeuralGas {

    private double lambdaMin = 0.01d;
    private double lambdaMax = 10.0d;
    private double learningRateMin = 0.001d;
    private double learningRateMax = 0.800d;
    private int iters;
    private double[][] neurons;

    public NeuralGas(final int numberOfNeurons, final int numberOfIterations) {
        this.iters = numberOfIterations;

        this.neurons = new double[numberOfNeurons][2];
        Random rnd = new Random();
        for (int i = 0; i < numberOfNeurons; i++) {
            neurons[i][0] = 0.25d + rnd.nextDouble() / 2;
            neurons[i][1] = 0.25d + rnd.nextDouble() / 2;
        }
    }

    public void sortByDistanceToVector(final double[] x) {

        int numberOfElements = neurons.length;

        do {
            for (int i = 0; i < numberOfElements - 1; i++) {
                if (Operations.distance2D(x, neurons[i]) > Operations.distance2D(x, neurons[i + 1])) {
                    double[] tmp = neurons[i];
                    neurons[i] = neurons[i + 1];
                    neurons[i + 1] = tmp;
                }
            }
            numberOfElements -= 1;
        } while (numberOfElements > 1);

    }

    public double gFunc(final int pos, final int k) {
        return Math.exp(-pos / lambda(k));
    }

    public double lambda(final int k) {
        return lambdaMax * Math.pow((lambdaMin / lambdaMax), (k / iters));
    }

    public double learningRate(final int k) {
        return learningRateMax * Math.pow((learningRateMin / learningRateMax), (k / iters));
    }

    public void adaptWeights(int i, final double[][] x) {

        for (int k = 0; k < x.length; k++) {

            sortByDistanceToVector(x[k]);

            for (int j = 0; j < neurons.length; j++) {
                neurons[j][0] += learningRate(i) * gFunc(j, i) * (x[k][0] - neurons[j][0]);
                neurons[j][1] += learningRate(i) * gFunc(j, i) * (x[k][1] - neurons[j][1]);
            }
        }
    }

    public double[][] getNeurons() {
        return neurons;
    }

}
