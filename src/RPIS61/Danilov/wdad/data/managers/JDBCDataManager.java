package RPIS61.Danilov.wdad.data.managers;

import RPIS61.Danilov.wdad.data.storage.DataSourceFactory;
import RPIS61.Danilov.wdad.learn.xml.*;

import javax.sql.DataSource;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class JDBCDataManager implements DataManager {

    private DataSource dataSource;

    public JDBCDataManager(){
        this.dataSource = DataSourceFactory.createDataSource();
    }

    public ArrayList<Reader> getReaders(){
        ArrayList<Reader> result = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT id, first_name, second_name, birth_date FROM readers");
            ResultSet set = statement.executeQuery();
            while (set.next()){
                Reader reader = new Reader();
                reader.setID(set.getInt(1));
                reader.setFirstName(set.getString(2));
                reader.setSecondName(set.getString(3));
                reader.setBooks(getBooks(reader, connection));
                result.add(reader);

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private Reader createReader (ResultSet set, Connection connection){
        Reader result = new Reader();
        try {
            result.setID(set.getInt(1));
            result.setFirstName(set.getString(2));
            result.setSecondName(set.getString(3));
            result.setBirthDay(set.getDate(4));
            result.setBooks(getBooks(result, connection));
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private ArrayList<Book> getBooks(Reader reader, Connection connection){
        ArrayList<Book> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT books_dictionary_id, take_date, name, description, print_year from books_readers " +
                    "JOIN books ON (books.ID = books_readers.books_dictionary_id) " +
                    "WHERE readers_id = ?");
            statement.setInt(1, reader.getID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Book book = createBook(resultSet, connection);
                result.add(book);
            }

        } catch (Exception e){
            e.printStackTrace();;
        }
        return result;
    }

    private Book createBook(ResultSet set, Connection connection){
        Book result = new Book();
        try {
            result.setID(set.getInt(1));
            result.setTakedate(set.getDate(2));
            result.setName(set.getString(3));
            result.setDescription(set.getString(4));
            result.setPrintYear(set.getInt(5));
            result.setGenre(getGenre(result, connection));
            result.setAuthor(getAuthor(result, connection));
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private Genre getGenre(Book book, Connection connection){
        Genre result = new Genre();
        try {
            PreparedStatement statement = connection.prepareStatement("select authors_id, first_name, second_name, birth_date " +
                    "FROM books_authors " +
                    "JOIN authors " +
                    "ON (authors.ID = books_authors.authors_id) " +
                    "WHERE books_id = ?");
            statement.setInt(1, book.getID());
            ResultSet set = statement.executeQuery();
            set.next();
            result = createGenre(set, connection);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private Genre createGenre (ResultSet set, Connection connection){
        Genre result = new Genre();
        try {
            result.setID(set.getInt(1));
            result.setName(set.getString(2));
            result.setDescription(set.getString(3));
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private Author getAuthor (Book book, Connection connection){
        Author result = new Author();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT authors_id, first_name, second_name, birth_date " +
                    "FROM books-authors " +
                    "JOIN authors " +
                    "ON (authors.ID = books-authors.authors_id) " +
                    "WHERE books_id = ?");
            statement.setInt(1, book.getID());
            ResultSet set = statement.executeQuery();
            set.next();
            result = createAuthor(set, connection);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private Author createAuthor (ResultSet set, Connection connection){
        Author result = new Author();
        try {
            result.setID(set.getInt(1));
            result.setFirstName(set.getString(2));
            result.setSecondName(set.getString(3));
            result.setBirthDate(set.getDate(4));
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<Reader> negligentReaders() throws RemoteException {
        ArrayList<Reader> result = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT readers_id, first_name, second_name, birth_date " +
                    "FROM readers " +
                    "JOIN books-readers " +
                    "ON (readers.ID = books_readers.readers_id) " +
                    "WHERE (CURRENT_DATE - take_date) > ?");
            int countDat = 14;
            statement.setInt(1, countDat);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                Reader reader = createReader(set, connection);
                result.add(reader);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public void addReader(Reader reader){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO readers " +
                    "VALUES (default, ?, ?, ?");
            statement.setString(1, reader.getFirstName());
            statement.setString(2, reader.getSecondName());
            statement.setDate(3, (Date) reader.getBirthDay());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removeReader(Reader reader){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM readers " +
                    "WHERE id = ?");
            statement.setInt(1, reader.getID());
            statement.executeUpdate();
            statement = connection.prepareStatement("DELETE FROM books_readers " +
                    "WHERE readers_id = ?");
            statement.setInt(1, reader.getID());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addBook(Reader reader, Book book) throws RemoteException {
        try{
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO books-readers " +
                    "VALUES (default, ?, ,? current_date, " +
                    "DATE_ADD(current_date, INTERVAL 14 DAY");
            statement.setInt(1, book.getID());
            statement.setInt(2, reader.getID());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeBook(Reader reader, Book book) throws RemoteException {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM books-readers " +
                    "WHERE readers_id = ? AND books_dictionary_id = ?");
            statement.setInt(1, reader.getID());
            statement.setInt(2, book.getID());
            statement.executeUpdate();
            reader.removeBook(book);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<Book, java.util.Date> bookReturnDates(Reader reader) throws RemoteException {
        HashMap<Book, java.util.Date> result = new HashMap<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT take_date, books_dictionary_id, name, description, print_year, return_date " +
                    "FROM books_readers " +
                    "JOIN books " +
                    "ON (books_readers.books_dictionary_id=books.id) " +
                    "WHERE reader_ID = ?");
            statement.setInt(1, reader.getID());
            ResultSet set = statement.executeQuery();
            while (set.next()){
                Book book = createBook(set, connection);
                result.put(book, set.getDate(6));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
