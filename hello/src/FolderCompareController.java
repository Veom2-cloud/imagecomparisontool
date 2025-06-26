import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory; // For linking data to columns
import javafx.stage.DirectoryChooser; // Import DirectoryChooser
import javafx.stage.Stage; // Import Stage

import java.io.File; // Import File
import java.io.IOException; // Import IOException for file operations
import java.nio.file.Files; // For getting file size
import java.nio.file.attribute.BasicFileAttributes; // For getting file attributes
import java.time.LocalDateTime;
import java.time.ZoneId; // For converting file time to LocalDateTime
import java.time.format.DateTimeFormatter;
import java.util.Comparator; // For sorting files

public class FolderCompareController {

    @FXML
    private TextField originalFolderPathField;
    @FXML
    private TextField compareFolderPathField;

    @FXML
    private TableView<FileData> leftFolderTableView;
    @FXML
    private TableColumn<FileData, String> leftFileColumn;
    @FXML
    private TableColumn<FileData, String> leftSizeColumn;
    @FXML
    private TableColumn<FileData, String> leftModifiedColumn;
    @FXML
    private TableColumn<FileData, String> leftTypeColumn;

    @FXML
    private TableView<FileData> rightFolderTableView;
    @FXML
    private TableColumn<FileData, String> rightFileColumn;
    @FXML
    private TableColumn<FileData, String> rightSizeColumn;
    @FXML
    private TableColumn<FileData, String> rightModifiedColumn;
    @FXML
    private TableColumn<FileData, String> rightTypeColumn;

    // Reference to the primary stage to show dialogs
    private Stage stage;

    /**
     * Sets the primary stage for the controller. This should be called by the main application.
     * @param stage The primary stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller. This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        System.out.println("FolderCompareController initialized.");

        // Set up cell value factories for left TableView
        leftFileColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        leftSizeColumn.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        leftModifiedColumn.setCellValueFactory(new PropertyValueFactory<>("fileModified"));
        leftTypeColumn.setCellValueFactory(new PropertyValueFactory<>("fileType"));

        // Set up cell value factories for right TableView
        rightFileColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        rightSizeColumn.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        rightModifiedColumn.setCellValueFactory(new PropertyValueFactory<>("fileModified"));
        rightTypeColumn.setCellValueFactory(new PropertyValueFactory<>("fileType"));

        // Optional: Pre-fill with a default path for testing if needed
        // originalFolderPathField.setText("C:\\Users\\youruser\\Documents\\FolderA");
        // compareFolderPathField.setText("C:\\Users\\youruser\\Documents\\FolderB");
    }

    /**
     * Handles action for browsing the original folder path.
     * This method is linked via onAction in folder_compare_layout.fxml.
     */
    @FXML
    protected void handleOriginalFolderBrowse() {
        System.out.println("Browse Original Folder clicked!");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Original Folder");
        File selectedDirectory = directoryChooser.showDialog(stage); // Use the injected stage

        if (selectedDirectory != null) {
            originalFolderPathField.setText(selectedDirectory.getAbsolutePath());
            loadFolderContents(selectedDirectory, leftFolderTableView);
        }
    }

    /**
     * Handles action for browsing the compare folder path.
     * This method is linked via onAction in folder_compare_layout.fxml.
     */
    @FXML
    protected void handleCompareFolderBrowse() {
        System.out.println("Browse Compare Folder clicked!");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Compare Folder");
        File selectedDirectory = directoryChooser.showDialog(stage); // Use the injected stage

        if (selectedDirectory != null) {
            compareFolderPathField.setText(selectedDirectory.getAbsolutePath());
            loadFolderContents(selectedDirectory, rightFolderTableView);
        }
    }

    /**
     * Loads the contents of a given folder into the specified TableView.
     * @param folder The folder to load files from.
     * @param tableView The TableView to populate.
     */
    private void loadFolderContents(File folder, TableView<FileData> tableView) {
        ObservableList<FileData> data = FXCollections.observableArrayList();
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                // Sort files and directories alphabetically
                java.util.Arrays.sort(files, Comparator.comparing(File::getName));

                for (File file : files) {
                    try {
                        String fileName = file.getName();
                        String fileSize = "";
                        String fileModified = "";
                        String fileType = "";

                        if (file.isFile()) {
                            long sizeBytes = Files.size(file.toPath());
                            fileSize = formatFileSize(sizeBytes);
                            fileType = getFileExtension(fileName);
                        } else if (file.isDirectory()) {
                            fileType = "Folder";
                            fileSize = "--"; // Indicate it's a folder, size not directly applicable here
                        }

                        BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                        LocalDateTime lastModifiedTime = attrs.lastModifiedTime()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime();
                        fileModified = lastModifiedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                        data.add(new FileData(fileName, fileSize, fileModified, fileType));
                    } catch (IOException e) {
                        System.err.println("Error reading file attributes for " + file.getName() + ": " + e.getMessage());
                        data.add(new FileData(file.getName(), "Error", "N/A", "N/A")); // Add error entry
                    }
                }
            }
        }
        tableView.setItems(data);
    }

    /**
     * Helper method to format file size into a human-readable string (KB, MB).
     */
    private String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = ("KMGTPE").charAt(exp-1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    /**
     * Helper method to get file extension.
     */
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "File"; // Default if no extension
    }
}
