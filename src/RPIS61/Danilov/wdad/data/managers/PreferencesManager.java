package RPIS61.Danilov.wdad.data.managers;

import RPIS61.Danilov.wdad.utils.PreferencesManagerConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PreferencesManager {
    private static final String XML_FILE_PATH = "src\\wdad\\resources\\configuration\\appconfig.xml";

    private Document document;
    private Properties properties;
    private static PreferencesManager instance;
    private XPath path;


    private PreferencesManager() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(XML_FILE_PATH);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            this.path = xPathFactory.newXPath();
            this.properties = new Properties();
            createProperties();
        } catch (SAXException | IOException | ParserConfigurationException e){
            e.printStackTrace();
        }
    }

    public static PreferencesManager getInstance()  {
        if (instance == null){
            return instance = new PreferencesManager();
        } else
            return instance;
    }

    private void createProperties() {
        String[] keys = { PreferencesManagerConstants.classprovider, PreferencesManagerConstants.createregistry,
        PreferencesManagerConstants.policypath, PreferencesManagerConstants.registryaddress, PreferencesManagerConstants.registryport,
        PreferencesManagerConstants.usecodebaseonly };
        for (String k : keys){
            try {
                properties.setProperty(k, path.evaluate(k, document));
            } catch (XPathExpressionException e){
                e.printStackTrace();
            }
        }
    }

    public void setProperty(String key, String value){
        document.getElementsByTagName(key).item(0).setTextContent(value);
        properties.setProperty(key, value);
    }

    public String getProperty (String key){
        return this.properties.getProperty(key);
    }

    public void setProperties (Properties prop){
        for (String p:prop.stringPropertyNames()){
            setProperty(p, prop.getProperty(p));
        }
    }

    public Properties getProperties(){
        return  this.properties;
    }

    public void addBindedObject (String name, String className){
        Element element = document.createElement("bindedobject");
        element.setAttribute("name", name);
        element.setAttribute("class", className);
        document.getElementsByTagName("server").item(0).appendChild(element);
        saveXML();
    }

    public void removeBindedObject (String name){
        NodeList bindedObjects = document.getElementsByTagName("bindedobject");
        for (int i = 0; i < bindedObjects.getLength(); i++) {
            Element element = (Element) bindedObjects.item(i);
            if (element.getAttribute("name").equals(name)) {
                element.removeChild(element);
            }
        }
        saveXML();
    }

    @Deprecated
    public String getCreateRegistry(){
        return document.getElementsByTagName("createregistry").item(0).getTextContent();
    }

    @Deprecated
    public void setCreateRegistry(String param){
        document.getElementsByTagName("createregistry").item(0).setTextContent(param);
    }

    @Deprecated
    public String getRegistryAddress(){
        return document.getElementsByTagName("registryaddress").item(0).getTextContent();
    }

    @Deprecated
    public void setRegistryAddress(String param){
        document.getElementsByTagName("registryaddress").item(0).setTextContent(param);
    }

    @Deprecated
    public String getRegistryPort(){
        return document.getElementsByTagName("registryport").item(0).getTextContent();
    }

    @Deprecated
    public void setRegistryPort(String param){
        document.getElementsByTagName("registryport").item(0).setTextContent(param);
    }

    @Deprecated
    public String getPolicyPath(){
        return document.getElementsByTagName("policypath").item(0).getTextContent();
    }

    @Deprecated
    public void setPolicyPath(String param){
        document.getElementsByTagName("policypath").item(0).setTextContent(param);
    }

    @Deprecated
    public String getUseCodeBaseOnly(){
        return document.getElementsByTagName("usecodebaseonly").item(0).getTextContent();
    }

    @Deprecated
    public void setUseCodeBaseOnly(String param){
        document.getElementsByTagName("usecodebaseonly").item(0).setTextContent(param);
    }

    @Deprecated
    public String getClassProvider(){
        return document.getElementsByTagName("classprovider").item(0).getTextContent();
    }

    @Deprecated
    public void setClassProvider(String param){
        document.getElementsByTagName("classprovider").item(0).setTextContent(param);
    }


    private void saveXML(){
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(new File(XML_FILE_PATH))));
        }
        catch (FileNotFoundException | TransformerException e){
            e.printStackTrace();
        }
    }
}
