package RPIS61.Danilov.wdad.learn.xml;

import java.util.Date;

public class Author {
    private int ID;
    private String firstName;
    private String secondName;
    private Date birthDate;

    public Author(){
        this ("", "", null);
    }

    public Author(String firstName, String secondName) {
        this(firstName, secondName, null);
    }

    public Author(String firstName, String secondName, Date birthDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDate = birthDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object obj) {
        Author author = (Author) obj;
        return (author.getFirstName().equals(firstName) && author.getSecondName().equals(secondName));
    }
}
