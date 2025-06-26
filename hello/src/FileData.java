import javafx.beans.property.SimpleStringProperty;

// This class represents a row in the TableView for file comparison
public class FileData {
    private final SimpleStringProperty fileName;
    private final SimpleStringProperty fileSize;
    private final SimpleStringProperty fileModified;
    private final SimpleStringProperty fileType;

    public FileData(String fileName, String fileSize, String fileModified, String fileType) {
        this.fileName = new SimpleStringProperty(fileName);
        this.fileSize = new SimpleStringProperty(fileSize);
        this.fileModified = new SimpleStringProperty(fileModified);
        this.fileType = new SimpleStringProperty(fileType);
    }

    public String getFileName() {
        return fileName.get();
    }

    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize.get();
    }

    public SimpleStringProperty fileSizeProperty() {
        return fileSize;
    }

    public String getFileModified() {
        return fileModified.get();
    }

    public SimpleStringProperty fileModifiedProperty() {
        return fileModified;
    }

    public String getFileType() {
        return fileType.get();
    }

    public SimpleStringProperty fileTypeProperty() {
        return fileType;
    }
}