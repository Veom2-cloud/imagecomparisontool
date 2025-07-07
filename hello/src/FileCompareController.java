import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class FileCompareController {

    @FXML private ImageView leftImageView;
    @FXML private ImageView rightImageView;

    @FXML private Slider leftZoomSlider;
    @FXML private Slider rightZoomSlider;

    @FXML private Button toggleButton; // optional if used externally

    @FXML
    private void initialize() {
        leftImageView.setPreserveRatio(true);
        rightImageView.setPreserveRatio(true);

        // Apply zoom scale to fitWidth
        leftZoomSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            leftImageView.setFitWidth(400 * newVal.doubleValue());
        });

        rightZoomSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            rightImageView.setFitWidth(400 * newVal.doubleValue());
        });
    }

    @FXML
    private void handleLeftUpload() {
        File file = openImageFile();
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            leftImageView.setImage(image);
            leftZoomSlider.setValue(1.0);
            leftImageView.setFitWidth(400);
        }
    }

    @FXML
    private void handleRightUpload() {
        File file = openImageFile();
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            rightImageView.setImage(image);
            rightZoomSlider.setValue(1.0);
            rightImageView.setFitWidth(400);
        }
    }

    private File openImageFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Image");
        chooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
        );
        return chooser.showOpenDialog(getPrimaryStage());
    }

    @FXML
    private void handleCompare() {
        System.out.println("Compare logic triggered!");
        // Add image diff or annotation logic here
    }

    private Stage getPrimaryStage() {
        return (Stage) leftImageView.getScene().getWindow();
    }
}
