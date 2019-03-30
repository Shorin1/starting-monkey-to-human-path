package RPIS61.Danilov.wdad.learn.xml;

import java.util.ArrayList;

public class TestXml {
    public static void main(String[] args) {
        Library library = new Library("src\\RPIS61\\Danilov\\wdad\\learn\\xml\\library.xml");
        ArrayList<Reader> readers = library.getReaders();
        for (Reader reader:readers){
            System.out.println(reader.getFirstName());
            ArrayList<Book> books = reader.getBooks();
            for (Book book:books){
                System.out.println(book.getName());
            }
        }
    }
}
