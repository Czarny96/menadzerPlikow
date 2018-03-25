package filemanager;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public abstract class Disc implements Interface {
    // Zmienne
    static File currentDirectoryFile;
    static String currentDirectoryPath;
    static Label labelValue;
    static String currentDirectoryValue;
    SimpleDateFormat lastModification;

    TableView<Data> tableView;
    TableColumn<Data, String> date;
    TableColumn<Data, String> name;
    TableColumn<Data, String> size;

    public void setLabelTxt(){labelValue.setText(currentDirectoryPath);}

    public String fileSize(File fileP){

        if(isPartition(fileP)) return Long.toString((long) (fileP.getTotalSpace()/Math.pow(1024, 3))) + "GB";

        // Pobranie rozmiaru
        Path path = Paths.get(fileP.toURI());
        long sizeB = 0;
        try {
            sizeB = Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Zamiana rozmiaru z Byte na ...
        String size;
        if(sizeB < (1024)) size = Long.toString(sizeB) + "B";
          else if(sizeB < Math.pow(1024, 2)) {
            long sizeKB = sizeB / 1024;
            size = Long.toString(sizeKB) + "KB";
        } else if(sizeB < Math.pow(1024, 3)) {
            long sizeMB = (long) (sizeB / Math.pow(1024, 2));
            size = Long.toString(sizeMB) + "MB";
        } else if (sizeB < Math.pow(1204, 4)) {
            long sizeGB = (long) (sizeB / Math.pow(1024, 3));
            size = Long.toString(sizeGB) + "GB";
        } else {
            long sizeTB = (long) (sizeB / Math.pow(1024, 4));
            size = Long.toString(sizeTB) + "TB";
        }
        return size;
    }

    // Sprawdzenie czy to partycja
    public boolean isPartition(File fileP){
        File[] partition = File.listRoots();
        for (File aPartition : partition) if (fileP.equals(aPartition)) return true;
        return false;
    }
}