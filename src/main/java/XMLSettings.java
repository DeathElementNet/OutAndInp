import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLSettings {
    final boolean isLoad;
    final String loadFile;
    final String loadFormat;

    final boolean isSave;
    final String saveFile;
    final String saveFormat;

    final boolean isLog;
    final String logFile;

    public XMLSettings(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);

        Element root = doc.getDocumentElement();
        Element setLoad = (Element) root.getElementsByTagName("load").item(0);
        Element setSave = (Element) root.getElementsByTagName("save").item(0);
        Element setLog = (Element) root.getElementsByTagName("log").item(0);

        isLoad = Boolean.parseBoolean(setLoad.getElementsByTagName("enabled").item(0).getTextContent());
        loadFile = setLoad.getElementsByTagName("fileName").item(0).getTextContent();
        loadFormat = setLoad.getElementsByTagName("format").item(0).getTextContent();

        isSave = Boolean.parseBoolean(setSave.getElementsByTagName("enabled").item(0).getTextContent());
        saveFile = setSave.getElementsByTagName("fileName").item(0).getTextContent();
        saveFormat = setSave.getElementsByTagName("format").item(0).getTextContent();

        isLog = Boolean.parseBoolean(setLog.getElementsByTagName("enabled").item(0).getTextContent());
        logFile = setLog.getElementsByTagName("fileName").item(0).getTextContent();
    }
}

