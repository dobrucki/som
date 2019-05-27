public interface Algorithm {


    void adaptWeights(int i, final double[][] x);

    double[][] getNeurons();
}
