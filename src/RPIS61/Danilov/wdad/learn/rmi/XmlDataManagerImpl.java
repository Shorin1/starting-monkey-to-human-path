package RPIS61.Danilov.wdad.learn.rmi;

import RPIS61.Danilov.wdad.learn.XmlDataManager;
import RPIS61.Danilov.wdad.learn.xml.Book;
import RPIS61.Danilov.wdad.learn.xml.Library;
import RPIS61.Danilov.wdad.learn.xml.Reader;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class XmlDataManagerImpl implements XmlDataManager, Serializable {

    private Library library;

    public XmlDataManagerImpl(String xmlFilaPath){
        library = new Library(xmlFilaPath);
    }

    @Override
    public List<Reader> negligentReaders() {
        return this.library.negligentReader();
    }

    @Override
    public void removeBook(Reader reader, Book book)  {
        this.library.removeBook(reader, book);
    }

    @Override
    public void addBook(Reader reader, Book book)  {
        this.library.addBook(reader, book);
    }

    @Override
    public HashMap<Book, LocalDate> bookReturnDates(Reader reader) {
        Reader r = this.library.getReader(reader);
        Book[] books = (Book[]) r.getBooks().toArray();
        HashMap<Book, LocalDate> result = new HashMap<>();
        for (Book b:books){
            result.put(b, b.getTakeDate());
        }
        return result;
    }
}
