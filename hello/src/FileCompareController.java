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

     private HelloController mainController;  // Reference to main controller

    public void setMainController(HelloController controller) {
        this.mainController = controller;
    }


    public void showLeftImage(File imageFile) {
    if (imageFile != null && leftImageView != null) {
        leftImageView.setImage(new Image(imageFile.toURI().toString()));
    }
}

public void showRightImage(File imageFile) {
    if (imageFile != null && rightImageView != null) {
        rightImageView.setImage(new Image(imageFile.toURI().toString()));
    }
}


   @FXML
    private void initialize() {
        leftImageView.setPreserveRatio(true);
        rightImageView.setPreserveRatio(true);

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
        leftImageView.setImage(new Image(file.toURI().toString()));
        leftZoomSlider.setValue(1.0);
        leftImageView.setFitWidth(400);

        if (mainController != null)
            mainController.addLeftFile(file.getName(), file.getParentFile()); // ✅ send folder
    }
}

@FXML
private void handleRightUpload() {
    File file = openImageFile();
    if (file != null) {
        rightImageView.setImage(new Image(file.toURI().toString()));
        rightZoomSlider.setValue(1.0);
        rightImageView.setFitWidth(400);

        if (mainController != null)
            mainController.addRightFile(file.getName(), file.getParentFile()); // ✅ send folder
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
