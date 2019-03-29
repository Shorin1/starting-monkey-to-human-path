package RPIS61.Danilov.wdad.learn.xml;

import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
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

    public void addReader(Reader reader) throws TransformerException, FileNotFoundException {
        this.readers.add(reader);
        XMLWriter.addReader(reader, document, xmlFilePath);
    }

    public boolean removeReader(Reader reader) throws TransformerException, FileNotFoundException {
        if (this.readers.remove(reader)) {
            XMLWriter.removeReader(reader, document, xmlFilePath);
            return true;
        }
        return false;
    }

    public void addBook(Reader reader, Book book) throws TransformerException, FileNotFoundException {
        for (Reader temp:readers){
            if (temp.equals(reader)){
                temp.addBook(book);
                XMLWriter.addBook(reader, book, document, xmlFilePath);
            }
        }
    }

    public boolean removeBook(Reader reader, Book book) throws TransformerException, FileNotFoundException {
        if (reader.removeBook(book)) {
            XMLWriter.removeBook(reader, book, document, xmlFilePath);
            return true;
        } else return false;
    }

    public ArrayList<Reader> getReaders(){
        return readers;
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
            if (book.getTakeDate().plusWeeks(2).isAfter(currentDate)){
                return true;
            }
        }
        return false;
    }
}
