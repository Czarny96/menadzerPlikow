package filemanager;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import java.io.File;

public class Tree extends Disc {

    @Override
    public TreeItem<String>[] generateTree(File directory){

        // Sprawdzenie czy pliki sa ukryte
        int count = 0;
        File[] file2 = directory.listFiles();
        for (File aFile2 : file2) if (aFile2.isHidden() || aFile2.isFile()) count++;

        // Dodanie plikow
        File[] file = directory.listFiles();
        int countHideFiles = file.length - count;
        TreeItem<String>[] treeItem = new TreeItem[countHideFiles];
        int position = 0;

        // Przejscie przez wszystkie elementy
        for (File aFile : file) {
            if ( !aFile.isFile() && !aFile.isHidden() && aFile.isDirectory() && countHideFiles == 0) {
                treeItem[position] = new TreeItem<>(aFile.getName());
                position++;
            } else if (!aFile.isFile() && !aFile.isHidden() &&aFile.isDirectory() && countHideFiles > 0) {
                treeItem[position] = new TreeItem<>(aFile.getName());
                try {
                    treeItem[position].getChildren().addAll(generateTree(aFile));
                    position++;
                } catch (Exception x) {
                    System.out.println(aFile.getAbsolutePath());
                }
            }
        }
        return treeItem;
    }

    @Override
    public String findPath(TreeItem<String> object, String string){
        if((object.getParent() == null) || (object.getParent().getValue().equals("This PC"))) return string;
        else{
            String directory = object.getParent().getValue();
            directory += "\\"+string;
            return findPath(object.getParent(), directory);
        }
    }

    @Override
    public void generateTree(TreeView<String> treeView){
        File[] roots = File.listRoots();

        TreeItem<String> ThisPc = new TreeItem<>("This PC");
        TreeItem<String>[] drives = new TreeItem[roots.length];

        for(int i = 0; i < roots.length; i++) {
            drives[i] = new TreeItem<>(roots[i].getAbsolutePath());
            try { drives[i].getChildren().addAll(generateTree(roots[i])); }
            catch(NullPointerException x) { System.out.println("Exeption x detected: "+x.fillInStackTrace()+drives[i].toString()); }

            ThisPc.getChildren().add(drives[i]);
        }
        treeView.setRoot(ThisPc);
    }

    @Override
    public void generateTable() {}
    @Override
    public void setValues(TableView<Data> tableView, TableColumn<Data, String> date, TableColumn<Data, String> name, TableColumn<Data, String> size) {}

}
