package RPIS61.Danilov.wdad.learn.xml;

import java.util.ArrayList;

public class Reader {
    private String firstName;
    private String secondName;
    private ArrayList<Book> books;

    public Reader(String firstName, String secondName){
        this(firstName, secondName, new ArrayList<>());
    }

    public Reader(String firstName, String secondName, ArrayList<Book> books){
        this.firstName = firstName;
        this.secondName = secondName;
        this.books = books;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getSecondName(){
        return secondName;
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
