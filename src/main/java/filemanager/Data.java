package filemanager;

import javafx.beans.property.SimpleStringProperty;

public class Data {

    // Zmienne
    private SimpleStringProperty name;
    private SimpleStringProperty size;
    private SimpleStringProperty date;

    // Konstruktor
    public Data(String name, String size, String date){
        this.name = new SimpleStringProperty(name);
        this.size = new SimpleStringProperty(size);
        this.date = new SimpleStringProperty(date);
    }
    // Gettery
    public String getDate(){ return date.get(); }
    public String getSize(){ return size.get(); }
    public String getName(){ return name.get(); }

    //Settery
    public void setName(String name) { this.name.set(name); }
    public void setSize(String size) { this.size.set(size); }
    public void setDate(String date) { this.date.set(date); }
}
