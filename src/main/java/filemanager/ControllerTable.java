package filemanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ControllerTable implements Initializable {

    // Obiekty z FXML (sceny)
    @FXML private TableView<Data> tableView;
    @FXML private TableColumn<Data, String> date;
    @FXML private TableColumn<Data, String> name;
    @FXML private TableColumn<Data, String> size;
    static Disc scene2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scene2 = new Table();
        scene2.setValues(tableView,date,name,size);

        SimpleDateFormat lastModification = new SimpleDateFormat("dd/MM/yy");
        File[] file;
        ObservableList<Data> list;
        
        file = Disc.currentDirectoryFile.listFiles();
        assert file != null;
        Data data[] = new Data[file.length];
        
        for(int i = 0; i < file.length; i++){
            String name;
            String size;
            String date;
            
            if(scene2.isPartition(file[i])) name = file[i].getAbsolutePath();
            else name = file[i].getName();

            size = scene2.fileSize(file[i]);
            date = lastModification.format(file[i].lastModified());
            data[i] = new Data(name,size,date);
        }

        list = FXCollections.observableArrayList(data);

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableView.setItems(list);
    }

    private void refreshOject(){
        scene2.tableView.getItems().clear();
        scene2.generateTable();
    }

    // Obsluga klikania na tabelce (2clicked)
    private String objectName, objectNameSource, objectPath, objectPathSource;
    private File file = null;
    private boolean checkSum = false;
    @FXML private void clickedObject(MouseEvent mouseEvent){

        if(mouseEvent.getClickCount() == 1){
            objectName = tableView.getSelectionModel().getSelectedItem().getName();

            objectPath = Disc.currentDirectoryPath +"\\"+ objectName;
            file = new File(objectPath);
        }
    }
    @FXML private void copy () {
        objectPathSource = objectPath;
        objectNameSource = objectName;
    }

    Path sourceCopy;
    Path destinationCopy;

    @FXML private void paste () {
            sourceCopy = Paths.get(objectPathSource);
            destinationCopy = Paths.get(objectPath + File.separator + objectNameSource);

            System.out.println(objectName);
            System.out.println(sourceCopy);
            System.out.println(destinationCopy);
            try {
                Files.copy(sourceCopy, destinationCopy);
            } catch (IOException e) {
                System.out.println("ERROR COPY");
            }
        refreshOject();
    }
    Path sourceDelete;
    @FXML private void delete () {
        System.out.println("PRESS: DELETE");
        sourceDelete = Paths.get(objectPath);
        try {
            //Files.delete(sourceDelete);
            Files.walk(sourceDelete, FileVisitOption.FOLLOW_LINKS).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File :: delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshOject();
    }

    @FXML private void open () {
        System.out.println("PRESS: OPEN");
        File file = new File(objectPath);
        if(file.isDirectory()) try {
            Disc.currentDirectoryFile = file;
            Disc.currentDirectoryPath = Disc.currentDirectoryFile.getAbsolutePath();
            scene2.setLabelTxt();
            tableView.getItems().clear();
            scene2.generateTable();
        } catch (Exception x) { System.out.println(x.getMessage()); }
        else if(file.isFile()){
            Desktop desktop = Desktop.getDesktop();
            try { desktop.open(file); }
            catch(IOException x){ System.out.println(x.getMessage()); }
        }
    }
Path sourceCreateFolder;
    @FXML private void createFolder() {
        System.out.println("PRESS: Create Folder");

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create new folder");
        dialog.setHeaderText("Enter folder name");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            //System.out.println("Folder name: " + result.get());
            sourceCreateFolder = Paths.get(objectPath + File.separator + result.get());
            System.out.println(sourceCreateFolder);
            if(!Files.exists(sourceCreateFolder))
                try {
                    Files.createDirectory(sourceCreateFolder);
                } catch (IOException e) {
                    System.out.println("ERROR");
                }
                refreshOject();
        }
    }
}
