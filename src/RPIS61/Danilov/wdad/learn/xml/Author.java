package RPIS61.Danilov.wdad.learn.xml;

import java.io.Serializable;

public class Author implements Serializable {
    private String firstName;
    private String secondName;

    public Author(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    @Override
    public boolean equals(Object obj) {
        Author author = (Author) obj;
        return (author.getFirstName().equals(firstName) && author.getSecondName().equals(secondName));
    }
}
