import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloController {

    @FXML private ImageView leftImageView;
    @FXML private ImageView rightImageView;

    private FileCompareController compareController;

    public void setCompareController(FileCompareController controller) {
        this.compareController = controller;
    }

    @FXML private Label titleLabel;
    @FXML private ListView<String> leftFileList;
    @FXML private ListView<String> rightFileList;
    @FXML private StackPane centralWorkingArea;
    @FXML private VBox sideBarContainer;
    @FXML private VBox sideBarContent;
    @FXML private VBox sideBar;
    @FXML private Button toggleButton;

    @FXML
    public void initialize() {
        leftFileList.setItems(FXCollections.observableArrayList());
        rightFileList.setItems(FXCollections.observableArrayList());

        // Show only filename in UI
        leftFileList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : new File(item).getName());
            }
        });

        rightFileList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : new File(item).getName());
            }
        });

        // Left list click
        leftFileList.setOnMouseClicked(event -> {
            String fullPath = leftFileList.getSelectionModel().getSelectedItem();
            if (fullPath != null && compareController != null) {
                File imageFile = new File(fullPath);
                compareController.showLeftImage(imageFile);
            }
        });

        // Right list click
        rightFileList.setOnMouseClicked(event -> {
            String fullPath = rightFileList.getSelectionModel().getSelectedItem();
            if (fullPath != null && compareController != null) {
                File imageFile = new File(fullPath);
                compareController.showRightImage(imageFile);
            }
        });
    }

    // Updated methods to add full file paths
    public void addLeftFile(File file) {
        leftFileList.getItems().add(file.getAbsolutePath());
    }

    public void addRightFile(File file) {
        rightFileList.getItems().add(file.getAbsolutePath());
    }

    @FXML
    private void handleOpenFileCompare() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("file_compare_layout.fxml"));
            Parent compareUI = loader.load();

            FileCompareController compareController = loader.getController();
            compareController.setMainController(this);
            setCompareController(compareController);

            centralWorkingArea.getChildren().clear();
            centralWorkingArea.getChildren().add(compareUI);
            titleLabel.setText("Compare File");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toggleSideBarContent() {
        boolean isVisible = sideBar.isVisible();
        sideBar.setVisible(!isVisible);
        sideBar.setManaged(!isVisible);
        toggleButton.setText(isVisible ? "Show Sidebar" : "Hide Sidebar");
    }

    @FXML protected void handleFolderBrowse() {
        titleLabel.setText("Folder Compare");
        loadViewIntoCentralArea("folder_compare_layout.fxml");
    }

    @FXML protected void handleFileBrowse() {
        loadViewIntoCentralArea("file_compare_layout.fxml");
    }

    @FXML protected void handleSessionSave() {
        titleLabel.setText("Saving Session...");
        System.out.println("Session Save triggered");
    }

    @FXML protected void handleSessionRecent() {
        titleLabel.setText("Recent Sessions");
        System.out.println("Session Recent triggered");
    }

    @FXML protected void handleViewDifferenceOnly() {
        titleLabel.setText("Difference View Only");
        System.out.println("View: Difference Only");
    }

    @FXML protected void handleViewAll() {
        titleLabel.setText("Viewing All Files");
        System.out.println("View: All");
    }

    @FXML protected void handleReportHighlight() {
        titleLabel.setText("Report: Highlight Enabled");
        System.out.println("Report: Highlight triggered");
    }

    @FXML protected void handleSettingsSideBySide() {
        titleLabel.setText("Settings: Side-By-Side View");
        System.out.println("Settings: Side-By-Side triggered");
    }

    @FXML private void handleHelpHowToUse() {
        System.out.println("Help: How to Use triggered");
        titleLabel.setText("📘 To compare files: Upload Left & Right files, adjust zoom, then click Compare");
    }

    @FXML private void handleShowLastSession() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("last_session.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Last Session");
            stage.setScene(new Scene(root, 500, 400));
            stage.initOwner(((Stage) centralWorkingArea.getScene().getWindow()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleHelpElementComparison() {
        titleLabel.setText("Help: Element Comparison Guide");
        System.out.println("Help: Element Comparison triggered");
    }

    private void loadViewIntoCentralArea(String fxmlFileName) {
        try {
            centralWorkingArea.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Node view = loader.load();
            centralWorkingArea.getChildren().add(view);
        } catch (IOException e) {
            System.err.println("Error loading view: " + fxmlFileName);
            e.printStackTrace();
            centralWorkingArea.getChildren().clear();
            centralWorkingArea.getChildren().add(new Label("Error loading: " + fxmlFileName));
        }
    }

    @FXML private void handleReportExport() {
        // Export comparison report logic placeholder
    }
}
