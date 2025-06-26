import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if ("admin".equals(user) && "admin123".equals(pass)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main_layout.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setTitle("Screen Comparison Tool");
                stage.setScene(new Scene(root, 1200, 800));
            } catch (Exception e) {
                errorLabel.setText("Failed to load main layout.");
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Invalid credentials.");
        }
    }
}
