package RPIS61.Danilov.wdad.learn.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.Date;
import java.time.LocalDate;

public abstract class XMLWriter {

    public static void addReader(Reader reader, Document document, String xmlFilePath) {
        Node library = document.getFirstChild();
        library.appendChild(getReader(reader, document));
        saveXML(document, xmlFilePath);
    }

    public static void removeReader(Reader reader, Document document, String xmlFilePath) {
        Node library = document.getFirstChild();
        NodeList nList = document.getElementsByTagName("reader");
        for (int i = 0; i < nList.getLength(); i++){
            Element element = (Element) nList.item(i);
            if (element.getAttribute("firstname").equals(reader.getFirstName()) &&
                    element.getAttribute("secondname").equals(reader.getSecondName())) {
                library.removeChild(nList.item(i));
                saveXML(document, xmlFilePath);
                return;
            }
        }
    }

    public static void addBook(Reader reader, Book book, Document document, String xmlFilePath)  {
        NodeList nList = document.getElementsByTagName("reader");
        for (int i = 0; i < nList.getLength(); i++) {
            Element element = (Element) nList.item(i);
            if (element.getAttribute("firstname").equals(reader.getFirstName()) &&
                    element.getAttribute("secondname").equals(reader.getSecondName())) {
                element.appendChild(getBook(book, document));
                element.appendChild(getTakeDate(book, document));
                saveXML(document, xmlFilePath);
                return;
            }
        }
    }

    public static void removeBook(Reader reader, Book book, Document document, String xmlFilePath) {
        NodeList nList = document.getElementsByTagName("reader");
        for (int i = 0; i < nList.getLength(); i++){
            Element readerE = (Element) nList.item(i);
            if (readerE.getAttribute("firstname").equals(reader.getFirstName()) && readerE.getAttribute("secondname").equals(reader.getSecondName())){
                NodeList bookNL = readerE.getElementsByTagName("book");
                NodeList takeDateNL = readerE.getElementsByTagName("takedate");
                for (int j = 0; j < bookNL.getLength(); j++){
                        if (bookEqual(book, (Element) bookNL.item(j)) && takeDateEqual(book, (Element) takeDateNL.item(j))){
                        readerE.removeChild(bookNL.item(i));
                        readerE.removeChild(takeDateNL.item(i));
                        saveXML(document, xmlFilePath);
                        return;
                    }
                }
            }
        }
    }

    private static boolean bookEqual(Book book, Element element){
        return  (authorEqual(book, element) &&
                element.getElementsByTagName("name").item(0).getTextContent().equals(book.getName())) &&
                element.getElementsByTagName("printyear").item(0).getTextContent().equals(book.getPrintYear()) &&
                element.getElementsByTagName("genre").item(0).getTextContent().equals(book.getGenre());
    }

    private static boolean authorEqual(Book book, Element element){
        Element nl = (Element) element.getElementsByTagName("author").item(0);
        String firstName = nl.getElementsByTagName("firstname").item(0).getTextContent();
        String secondName = nl.getElementsByTagName("secondname").item(0).getTextContent();
        Author author = book.getAuthor();
        return  (author.getFirstName().equals(firstName) && author.getSecondName().equals(secondName));
    }

    private static boolean takeDateEqual(Book book, Element element){
        int day = Integer.parseInt(element.getElementsByTagName("day").item(0).getTextContent());
        int month = Integer.parseInt(element.getElementsByTagName("month").item(0).getTextContent());
        int year = Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent());
        return (LocalDate.of(year, month, day).equals(book.getTakeDate()));
    }


    private static Element getReader(Reader reader, Document document){
        Element readerElement = document.createElement("reader");
        readerElement.setAttribute("firstname", reader.getFirstName());
        readerElement.setAttribute("secondname", reader.getSecondName());
        for(Book book:reader.getBooks()){
            readerElement.appendChild(getBook(book, document));
            readerElement.appendChild(getTakeDate(book, document));
        }
        return readerElement;
    }

    private static Element getBook(Book book, Document document){
        Element bookElement = document.createElement("book");
        bookElement.appendChild(getAuthor(book, document));
        Element name = document.createElement("name");
        name.setTextContent(book.getName());
        bookElement.appendChild(name);
        Element printyear = document.createElement("printyear");
        printyear.setTextContent(String.valueOf(book.getPrintYear()));
        bookElement.appendChild(printyear);
        Element genre = document.createElement("genre");
        genre.setTextContent(book.getGenre().toString());
        bookElement.appendChild(genre);
        return bookElement;
    }

    private static Element getAuthor(Book book, Document document){
        Element author = document.createElement("author");
        Element firstName = document.createElement("firstname");
        firstName.setTextContent(book.getAuthor().getFirstName());
        Element secondName = document.createElement("secondname");
        secondName.setTextContent(book.getAuthor().getSecondName());
        author.appendChild(firstName);
        author.appendChild(secondName);
        return author;
    }

    private static Element getTakeDate(Book book, Document document){
        Element takeDate = document.createElement("takedate");
        Element day = document.createElement("day");
        Date ld = book.getTakeDate();
        day.setTextContent(String.valueOf(ld.toLocalDate().getDayOfMonth()));
        takeDate.appendChild(day);
        Element month = document.createElement("month");
        month.setTextContent(String.valueOf(ld.toLocalDate().getMonthValue()));
        takeDate.appendChild(month);
        Element year = document.createElement("year");
        year.setTextContent(String.valueOf(ld.getYear()));
        takeDate.appendChild(year);
        return takeDate;
    }

    private static void saveXML(Document document, String xmlFilePath) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new File(xmlFilePath)));
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
