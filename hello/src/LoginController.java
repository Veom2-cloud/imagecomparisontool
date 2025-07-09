import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ListView<String> leftFileList;
@FXML private ListView<String> rightFileList;
    @FXML
private void handleShowCurrentFiles() {
    leftFileList.getItems().add("Example_Left.png");
    rightFileList.getItems().add("Example_Right.png");
}

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if ("admin@example.com".equals(email) && "admin123".equals(password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main_layout.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setTitle("Screen Comparison Tool");
                stage.setScene(new Scene(root, 1200, 800));
            } catch (Exception e) {
                showAlert("Error", "Unable to load main screen.");
                e.printStackTrace();
            }
        } else {
            showAlert("Login Failed", "Invalid email or password.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
