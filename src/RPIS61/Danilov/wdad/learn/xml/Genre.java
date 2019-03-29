package RPIS61.Danilov.wdad.learn.xml;

public class Genre {
    private int ID;
    private String name;
    private String description;

    public Genre(){
        this (null, null);
    }

    public Genre (String name, String description){
        this.name = name;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
