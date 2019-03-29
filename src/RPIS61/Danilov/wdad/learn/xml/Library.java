package RPIS61.Danilov.wdad.learn.xml;

import org.w3c.dom.Document;

import java.time.LocalDate;
import java.util.ArrayList;

public class Library {
    private ArrayList<Reader> readers;
    private Document document;
    private String xmlFilePath;

    public Library(String xmlFilePath){
        this.readers = new ArrayList<>();
        this.document = XMLReader.getDocument(xmlFilePath);
        this.xmlFilePath = xmlFilePath;
        this.readers = XMLReader.getReaders(document);
    }

    public void addReader(Reader reader) {
        this.readers.add(reader);
        XMLWriter.addReader(reader, document, xmlFilePath);
    }

    public boolean removeReader(Reader reader) {
        if (this.readers.remove(reader)) {
            XMLWriter.removeReader(reader, document, xmlFilePath);
            return true;
        }
        return false;
    }

    public void addBook(Reader reader, Book book)  {
        for (Reader temp:readers){
            if (temp.equals(reader)){
                temp.addBook(book);
                XMLWriter.addBook(reader, book, document, xmlFilePath);
            }
        }
    }

    public boolean removeBook(Reader reader, Book book) {
        if (reader.removeBook(book)) {
            XMLWriter.removeBook(reader, book, document, xmlFilePath);
            return true;
        } else return false;
    }

    public ArrayList<Reader> getReaders(){
        return readers;
    }

    public Reader getReader(Reader reader){
        for (Reader r:readers){
            if (r.equals(reader)){
                return r;
            }
        }
        return null;
    }

    public ArrayList<Book> debtBooks(Reader reader){
        return reader.getBooks();
    }

    public ArrayList<Reader> negligentReader(){
        ArrayList<Reader> negligentReader = new ArrayList<>();
        for (Reader reader:readers){
            if (readerNegligent(reader)){
                negligentReader.add(reader);
            }
        }
        return negligentReader;
    }

    private boolean readerNegligent(Reader reader){
        ArrayList<Book> books = reader.getBooks();
        LocalDate currentDate = LocalDate.now();
        for (Book book:books){
            if (book.getTakeDate().toLocalDate().plusWeeks(2).isBefore(currentDate)){
                return true;
            }
        }
        return false;
    }
}
