import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private Controller controller;

    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        controller = new Controller();
        Parent root = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        scene = new Scene(root);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
