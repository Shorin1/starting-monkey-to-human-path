package RPIS61.Danilov.wdad.learn;

import RPIS61.Danilov.wdad.learn.xml.Book;
import RPIS61.Danilov.wdad.learn.xml.Reader;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface XmlDataManager extends Remote {

    List<Reader> negligentReaders() throws RemoteException;

    List<Reader> getReaders() throws RemoteException;

    void addReader(Reader reader) throws RemoteException;

    void removeBook(Reader reader, Book book) throws RemoteException;

    void addBook(Reader reader, Book book) throws RemoteException;

    HashMap<Book, LocalDate> bookReturnDates (Reader reader) throws RemoteException;
}
