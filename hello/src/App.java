
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login_layout.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("Screen Comparison Tool");
    //    stage.getIcons().add(new Image("/dxc_logo.png")); // Add your DXC logo image to resources
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
