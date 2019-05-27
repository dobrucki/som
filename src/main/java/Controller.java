import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;


public class Controller {

    @FXML
    private Canvas plot;

    private GraphicsContext gc;

    private double[][] points;

    @FXML
    private ProgressBar progressBar;


    @FXML
    public void handleStart() {
//        generatePoints();
        handleNeuralGas();
    }

    private void generatePoints() {
        this.points = new double[50][2];
        for (int i = 0; i < points.length; i++) {
            points[i][0] = Math.random();
            points[i][1] = Math.random();
        }
    }

    private void handleNeuralGas() {
        final NeuralGas ng = new NeuralGas(30, 200);
        gc = plot.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, plot.getWidth(), plot.getHeight());
        CsvReader reader = new CsvReader();
        points = reader.read("C:\\Users\\jd\\Desktop\\d.csv");
        squeeze(points);
        AnimationTimer animationTimer = new AnimationTimer() {
            final int counter = 300;
            int i = 0;

            @Override
            public void handle(long now) {
                ng.adaptWeights(i, points);
                draw(ng.getNeurons());
                progressBar.setProgress((double) i / counter);

                i += 1;
                if (i >= counter) stop();
            }
        };
        animationTimer.start();
    }

    private void draw(final double[][] neurons) {
        gc.clearRect(0, 0, plot.getWidth(), plot.getHeight());
        gc.setFill(Color.RED);
        double[] xy;
        for (int i = 0; i < points.length; i++) {
            xy = convert(points[i]);
            gc.fillOval(xy[0], xy[1], 2, 2);
        }
        gc.setFill(Color.BLUE);
        for (int j = 0; j < neurons.length; j++) {
            xy = convert(neurons[j]);
            gc.fillOval(xy[0], xy[1], 5, 5);
        }

    }

    private double[] convert(double[] xy) {
        double[] r = new double[2];
        r[0] = xy[0] * (plot.getWidth() - 5);
        r[1] = xy[1] * (plot.getHeight() - 5);
        r[1] *= -1; r[1] += (plot.getHeight() - 5);
        return r;
    }

    private void squeeze(double[][] xy) {
        double maxX = xy[0][0];
        double maxY = xy[0][1];
        double minX = maxX;
        double minY = maxY;

        for (int i = 0; i < xy.length; i++) {
            minX = Math.min(xy[i][0], minX);
            minY = Math.min(xy[i][1], minY);
            maxX = Math.max(xy[i][0], maxX);
            maxY = Math.max(xy[i][1], maxY);
        }

        for (double[] d : xy) {
            d[0] = (d[0] - minX) / (maxX - minX);
            d[1] = (d[1] - minY) / (maxY - minY);
        }
    }
}
