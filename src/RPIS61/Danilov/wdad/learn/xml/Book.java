package RPIS61.Danilov.wdad.learn.xml;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Serializable {
    private String name;
    private String printYear;
    private String genre;
    private Author author;
    private LocalDate takedate;

    public Book(String name, String printYear, String genre, Author author, LocalDate takedate){
        this.author = author;
        this.name = name;
        this.printYear = printYear;
        this.genre = genre;
        this.takedate = takedate;
    }

    public String getName(){
        return name;
    }

    public String getPrintYear() {
        return printYear;
    }

    public String getGenre() {
        return genre;
    }

    public Author getAuthor(){
        return author;
    }

    public LocalDate getTakeDate(){
        return takedate;
    }

    @Override
    public boolean equals(Object obj){
        Book book = (Book) obj;
        return  ((book.getName().equals(name)) && book.getGenre().equals(genre) &&
                book.getPrintYear().equals(printYear) && book.getTakeDate().equals(takedate)
                && book.getAuthor().equals(author));
    }
}
