package RPIS61.Danilov.wdad.learn.xml;

import java.sql.Date;

public class Book {
    private int ID;
    private String name;
    private String description;
    private int printYear;
    private Genre genre;
    private Author author;
    private Date takedate;

    public Book() {
        this(null, null, -1, null, null, null);
    }

    public Book(String name, String description, int printYear, Genre genre, Author author, Date takedate){
        this.author = author;
        this.name = name;
        this.description = description;
        this.printYear = printYear;
        this.genre = genre;
        this.takedate = takedate;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String dicription) {
        this.description = dicription;
    }

    public void setPrintYear(int printYear) {
        this.printYear = printYear;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setTakedate(Date takedate) {
        this.takedate = takedate;
    }

    public int getID() { return ID; }

    public String getName(){
        return name;
    }

    public String getDescription() {
        return description;
    }


    public int getPrintYear() {
        return printYear;
    }

    public Genre getGenre() {
        return genre;
    }

    public Author getAuthor(){
        return author;
    }

    public Date getTakeDate(){
        return takedate;
    }

    @Override
    public boolean equals(Object obj){
        Book book = (Book) obj;
        return  ((book.getName().equals(name)) && book.getGenre().equals(genre) &&
                book.getPrintYear() == printYear && book.getTakeDate().equals(takedate)
                && book.getAuthor().equals(author));
    }
}
