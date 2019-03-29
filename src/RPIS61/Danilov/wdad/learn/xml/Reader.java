package RPIS61.Danilov.wdad.learn.xml;

import java.util.ArrayList;
import java.util.Date;

public class Reader {
    private int ID;
    private String firstName;
    private String secondName;
    private Date birthDay;
    private ArrayList<Book> books;

    public Reader(){
        this(null, null);
    }

    public Reader(String firstName, String secondName){
        this(firstName, secondName, null, new ArrayList<>());
    }

    public Reader(String firstName, String secondName, Date birthDay, ArrayList<Book> books){
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDay = birthDay;
        this.books = books;
    }

    public int getID(){ return ID; }

    public String getFirstName(){
        return firstName;
    }

    public String getSecondName(){
        return secondName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public void addBook(Book book){
        books.add(book);
    }

    public boolean removeBook(Book book){
        return books.remove(book);
    }

    public ArrayList<Book> getBooks(){
        return this.books;
    }

    public void setBooks(ArrayList<Book> books) { this.books = books; }

    @Override
    public boolean equals(Object obj){
        Reader reader = (Reader) obj;
        if (!reader.getFirstName().equals(firstName) ||
                !reader.getSecondName().equals(secondName)){
            return false;
        }
        ArrayList<Book> books = reader.getBooks();
        for (int i = 0; i < books.size(); i++){
            if (!books.get(i).equals(this.books.get(i))){
                return false;
            }
        }
        return true;
    }
}
