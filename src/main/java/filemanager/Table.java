package filemanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.text.SimpleDateFormat;

public class Table extends Disc {
    // Ustawienie wartosci
    public void setValues(TableView<Data> tableView, TableColumn<Data, String> date, TableColumn<Data, String> name, TableColumn<Data, String> size){
        this.tableView = tableView;
        this.date = date;
        this.name=name;
        this.size = size;
    }

    // Stworzenie widoku
    @Override
    public void generateTable() {
        // Wzor na date
        lastModification = new SimpleDateFormat("dd/MM/yy");

        File[] file;
        ObservableList<Data> list;

        // Partycje
        if(currentDirectoryValue.equals("This PC")) file = File.listRoots();
        // Lista plikow
        else file = currentDirectoryFile.listFiles();

        assert file != null;
        Data[] data = new Data[file.length];

        for(int i = 0; i < file.length; i++){
            String name = null;
            String size = null;
            String date = null;

            try{
                if(isPartition(file[i])) name = file[i].getAbsolutePath();
                else name = file[i].getName();

                size = fileSize(file[i]);
                date = lastModification.format(file[i].lastModified());
            }catch(Exception x){
                //System.out.println("Exception detected in tableView strings: "+x.getMessage());
            }
            data[i] = new Data(/*img,*/name,size,date);
        }

        list = FXCollections.observableArrayList(data);

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableView.setItems(list);
    }

    @Override
    public TreeItem<String>[] generateTree(File directory) {return null;}
    @Override
    public String findPath(TreeItem<String> object, String string) {return null;}
    @Override
    public void generateTree(TreeView<String> treeView) {}
}
