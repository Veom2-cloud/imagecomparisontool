import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LastSessionController {

    @FXML private TextField sessionTimeField;
    @FXML private ListView<String> sessionFilesList;
    @FXML private TextArea sessionSummaryArea;

    @FXML
    public void initialize() {
        // Load mock data — replace with actual session loading logic
        sessionTimeField.setText("2025-07-09 16:48");
        sessionFilesList.getItems().addAll("File1.png", "File2.png", "Notes.txt");
        sessionSummaryArea.setText("This session included comparing screenshots and a notes file.\nMajor differences were found in visual layout and text structure.");
    }

    @FXML
    private void handleReloadSession() {
        // Implement logic to re-open main screen with session content
        System.out.println("Reloading last session...");
        // Example: load saved data into main layout's comparison view
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) sessionTimeField.getScene().getWindow();
        stage.close();
    }
}
