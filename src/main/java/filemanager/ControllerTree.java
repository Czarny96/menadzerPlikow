package filemanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static filemanager.ControllerTable.scene2;

public class ControllerTree implements Initializable {
    // Obiekty z FXML (sceny)
    @FXML private Pane panel;
    @FXML private TreeView<String> treeView;
    @FXML private Label label;
    private static Tree sceneFX1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sceneFX1 = new Tree();
        // Sciezka poczatkowa
        Disc.currentDirectoryFile = new File("C:/");

        // Wyswietlenie sciezki
        Disc.currentDirectoryPath = Disc.currentDirectoryFile.getAbsolutePath();
        Disc.labelValue = label;
        label.setText(Disc.currentDirectoryPath);

        // Ustawienie sceny
        try{
            Pane loadScene2 = FXMLLoader.load(getClass().getResource("/Scene2.fxml"));
            panel.getChildren().add(loadScene2);
        } catch(NullPointerException | IOException x) { x.printStackTrace(); }

        // Stworzenie widoku
        sceneFX1.generateTree(treeView);
    }

    @FXML private void clicked(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 1){

                TreeItem<String> object = treeView.getSelectionModel().getSelectedItem();
                Disc.currentDirectoryValue = object.getValue();
                System.out.println("Selected Text : " + object.getValue());

                Disc.currentDirectoryFile = new File(sceneFX1.findPath(object, object.getValue()));
                Disc.currentDirectoryPath = Disc.currentDirectoryFile.getAbsolutePath();
                label.setText(Disc.currentDirectoryPath);

                scene2.tableView.getItems().clear();
                scene2.generateTable();
        }

    }
}
