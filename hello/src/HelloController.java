import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HelloController {



    @FXML private Label titleLabel;
    @FXML private ListView<String> leftFileList;
    @FXML private ListView<String> rightFileList;
    @FXML private StackPane centralWorkingArea;
    @FXML private VBox sideBarContainer;
    @FXML private VBox sideBarContent;
    @FXML private VBox sideBar;
    @FXML private Button toggleButton;

    @FXML
    private void initialize() {
        leftFileList.getItems().addAll("File 1", "File 2");
        rightFileList.getItems().addAll("File 1", "File 2", "File 3");

       
    }

    // Sidebar toggle




@FXML
private void toggleSideBarContent() {
    boolean isVisible = sideBar.isVisible();
    sideBar.setVisible(!isVisible);
    sideBar.setManaged(!isVisible);
    toggleButton.setText(isVisible ? "Show Sidebar" : "Hide Sidebar");
}


    // SESSION actions
    @FXML protected void handleFolderBrowse() {
        titleLabel.setText("Folder Compare");
        loadViewIntoCentralArea("folder_compare_layout.fxml");
    }

    @FXML protected void handleFileBrowse() {
        titleLabel.setText("Comparing File1.png");
        loadViewIntoCentralArea("file_compare_layout.fxml");
    }

    @FXML protected void handleSessionSave() {
        titleLabel.setText("Saving Session...");
        // Save logic placeholder
        System.out.println("Session Save triggered");
    }

    @FXML protected void handleSessionRecent() {
        titleLabel.setText("Recent Sessions");
        // Load recent session list
        System.out.println("Session Recent triggered");
    }

    // VIEW actions
    @FXML protected void handleViewDifferenceOnly() {
        titleLabel.setText("Difference View Only");
        // Implement filtering for differences
        System.out.println("View: Difference Only");
    }

    @FXML protected void handleViewAll() {
        titleLabel.setText("Viewing All Files");
        // Show all file contents
        System.out.println("View: All");
    }

    // REPORT actions
    @FXML protected void handleReportHighlight() {
        titleLabel.setText("Report: Highlight Enabled");
        // Apply highlighting logic
        System.out.println("Report: Highlight triggered");
    }

    // SETTINGS actions
    @FXML protected void handleSettingsSideBySide() {
        titleLabel.setText("Settings: Side-By-Side View");
        // Adjust layout orientation to side-by-side
        System.out.println("Settings: Side-By-Side triggered");
    }

   @FXML
private void handleHelpHowToUse() {
    System.out.println("Help: How to Use triggered");

    // Option 1: Show quick instructions in the title bar
    titleLabel.setText("📘 To compare files: Upload Left & Right files, adjust zoom, then click Compare");

    // Option 2 (Optional): Load help view into centralWorkingArea
    // loadViewIntoCentralArea("how_to_use_view.fxml");
}


    // HELP actions
    @FXML protected void handleHelpElementComparison() {
        titleLabel.setText("Help: Element Comparison Guide");
        // Load help overlay or documentation
        System.out.println("Help: Element Comparison triggered");
    }

    // Central view loader
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
}
