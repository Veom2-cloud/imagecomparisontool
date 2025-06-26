import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node; // Import Node for dynamic FXML loading

import java.io.IOException;

public class HelloController {

    @FXML
    private Label titleLabel; // Label to display the current file name/title
    @FXML
    private ListView<String> leftFileList; // ListView for files in the left pane (e.g., original folder)
    @FXML
    private ListView<String> rightFileList; // ListView for files in the right pane (e.g., compare folder)
    @FXML
    private StackPane centralWorkingArea; // StackPane to hold dynamic content (e.g., folder compare UI or file compare UI)
    @FXML
    private TextField searchTextField; // TextField for the search input in the sidebar
    @FXML
    private VBox sideBar; // Inject the sidebar VBox to control its visibility

    /**
     * Initializes the controller. This method is automatically called after the FXML file has been loaded.
     * It's where you put setup logic for your UI elements.
     */
    @FXML
    private void initialize() {
        // Example of populating lists initially (you would load real data here)
        leftFileList.getItems().addAll("File 1", "File 2");
        rightFileList.getItems().addAll("File 1", "File 2", "File 3");

        // Null check for searchTextField before accessing its properties
        if (searchTextField != null) {
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Search text changed to: " + newValue);
                // Implement search/filter logic here
            });
        } else {
            System.err.println("Error: searchTextField was not injected by FXML. Check fx:id in main_layout.fxml.");
        }

        // Initially load the default central working area content
        // Or you could load a specific view (e.g., folder_compare_layout.fxml) by default
        // For now, it will show the "Central working area" label as defined in main_layout.fxml
    }

    /**
     * Handles the action when "Folder" menu item is clicked.
     * Loads the folder_compare_layout.fxml into the central working area.
     */
    @FXML
    protected void handleFolderBrowse() {
        System.out.println("Folder Browse clicked! Loading folder_compare_layout.fxml");
        titleLabel.setText("Folder Compare"); // Update title
        loadViewIntoCentralArea("folder_compare_layout.fxml");
    }

    /**
     * Handles the action when "File" menu item is clicked.
     * Loads the file_compare_layout.fxml into the central working area.
     */
    @FXML
    protected void handleFileBrowse() {
        System.out.println("File Browse clicked! Loading file_compare_layout.fxml");
        titleLabel.setText("Comparing File1.png"); // Update title
        loadViewIntoCentralArea("file_compare_layout.fxml");
    }

    /**
     * Toggles the visibility of the sidebar.
     * When the sidebar is hidden, it also ensures it doesn't take up layout space.
     * This method is called when the "Show/Hide Button" is clicked.
     */
    @FXML
    protected void toggleSideBarVisibility() {
        if (sideBar != null) {
            boolean isCurrentlyVisible = sideBar.isVisible();
            sideBar.setVisible(!isCurrentlyVisible);
            sideBar.setManaged(!isCurrentlyVisible); // Crucial: hides the element from layout calculations
        } else {
            System.err.println("Error: sideBar was not injected by FXML. Check fx:id in main_layout.fxml.");
        }
    }

    /**
     * Helper method to load an FXML file into the centralWorkingArea.
     * @param fxmlFileName The name of the FXML file to load (e.g., "folder_compare_layout.fxml")
     */
    private void loadViewIntoCentralArea(String fxmlFileName) {
        try {
            // Clear any existing content from the central area
            centralWorkingArea.getChildren().clear();
            // Load the new FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Node view = loader.load();
            centralWorkingArea.getChildren().add(view); // Add the loaded view to the StackPane
            // The controller for the loaded FXML can be accessed via loader.getController()
            // if you need to pass data or call methods on it, e.g.:
            // if (fxmlFileName.equals("folder_compare_layout.fxml")) {
            //     FolderCompareController controller = loader.getController();
            //     // controller.setFolderPath("some/path");
            // }
        } catch (IOException e) {
            System.err.println("Failed to load FXML: " + fxmlFileName);
            e.printStackTrace();
            // Display an error message to the user in the UI if necessary
            centralWorkingArea.getChildren().clear(); // Clear existing content
            centralWorkingArea.getChildren().add(new Label("Error loading view: " + fxmlFileName + "\n" + e.getMessage()));
        }
    }
}
