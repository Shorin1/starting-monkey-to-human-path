package RPIS61.Danilov.wdad.data.managers;

import RPIS61.Danilov.wdad.learn.xml.Book;
import RPIS61.Danilov.wdad.learn.xml.Reader;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface DataManager extends Remote {

    List<Reader> negligentReaders() throws RemoteException;

    void removeBook(Reader reader, Book book) throws RemoteException;

    void addBook(Reader reader, Book book) throws RemoteException;

    HashMap<Book, Date> bookReturnDates (Reader reader) throws RemoteException;
}
