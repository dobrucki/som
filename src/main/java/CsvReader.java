import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CsvReader {

    private List<double[]> xd;


    public double[][] read(String path) {
        xd = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream.forEach(this::decode);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return xd.toArray(new double[0][0]);
    }

    private void decode(String line) {
        String[] s = line.split(",");
        try {
            double[] res = new double[s.length];
            for (int i = 0; i < s.length; i++) {
                res[i] = Double.parseDouble(s[i]);
            }
            xd.add(res);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
