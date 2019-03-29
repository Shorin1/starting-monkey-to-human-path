package RPIS61.Danilov.wdad.learn.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class XMLReader {

    public static Document getDocument(String xmlFilePath) {
        try {
            File inputFile = new File(xmlFilePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            document.normalize();
            return document;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Reader> getReaders(Document document) {
        NodeList readers =  document.getElementsByTagName("reader");
        ArrayList<Reader> bookReaders = new ArrayList<>();
        for (int i = 0; i < readers.getLength(); i++) {
            Element reader = (Element) readers.item(i);
            Reader bookReader = new Reader(reader.getAttribute("firstname"), reader.getAttribute("secondname"));
            for (Book book:getBooks(reader)){
                bookReader.addBook(book);
            }
            bookReaders.add(bookReader);
        }
        return bookReaders;
    }

    private static ArrayList<Book> getBooks(Element reader){
        NodeList bookElements = reader.getElementsByTagName("book");
        NodeList takeDateElements = reader.getElementsByTagName("takedate");
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < bookElements.getLength(); i++){
            Element book = (Element) bookElements.item(i);
            Element takeDateE = (Element) takeDateElements.item(i);
            String name = book.getElementsByTagName("name").item(0).getTextContent();
            int printYear = Integer.parseInt(book.getElementsByTagName("printyear").item(0).getTextContent());
            String genre = book.getElementsByTagName("genre").item(0).getTextContent();
            Author author = getAuthor((Element) book.getElementsByTagName("author").item(0));
            LocalDate takedate = getTakeDate(takeDateE);
            books.add(new Book(name, printYear, genre, author, takedate));
        }
        return books;
    }

    private static Author getAuthor(Element author){
        String firstName = author.getElementsByTagName("firstname").item(0).getTextContent();
        String secondName = author.getElementsByTagName("secondname").item(0).getTextContent();
        return new Author(firstName, secondName);
    }


    private static LocalDate getTakeDate(Element takedate) {
        int day = Integer.parseInt(takedate.getElementsByTagName("day").item(0).getTextContent());
        int month = Integer.parseInt(takedate.getElementsByTagName("month").item(0).getTextContent());
        int year = Integer.parseInt(takedate.getElementsByTagName("year").item(0).getTextContent());
        return LocalDate.of(year, month, day);
    }
}
