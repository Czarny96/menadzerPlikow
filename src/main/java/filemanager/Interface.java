package filemanager;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;

public interface Interface {

    TreeItem<String>[] generateTree(File directory);
    void generateTree(TreeView<String> treeView);

    String findPath(TreeItem<String> object, String string);
    String fileSize(File fileP);
    boolean isPartition(File fileP);

    void setValues(TableView<Data> tableView, TableColumn<Data, String> date, TableColumn<Data, String> name, TableColumn<Data, String> size);
    void generateTable();
    void setLabelTxt();
}
