import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class XMLSettings {
    private final boolean isLoad;
    private final String loadFile;
    private final String loadFormat;

    private final boolean isSave;
    private final String saveFile;
    private final String saveFormat;

    private final boolean isLog;
    private final String logFile;

    public XMLSettings(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);

        Element root = doc.getDocumentElement();
        Element setLoad = (Element) root.getElementsByTagName("load").item(0);
        Element setSave = (Element) root.getElementsByTagName("save").item(0);
        Element setLog = (Element) root.getElementsByTagName("log").item(0);

        this.isLoad = Boolean.parseBoolean(setLoad.getElementsByTagName("enabled").item(0).getTextContent());
        this.loadFile = setLoad.getElementsByTagName("fileName").item(0).getTextContent();
        this.loadFormat = setLoad.getElementsByTagName("format").item(0).getTextContent();

        this.isSave = Boolean.parseBoolean(setSave.getElementsByTagName("enabled").item(0).getTextContent());
        this.saveFile = setSave.getElementsByTagName("fileName").item(0).getTextContent();
        this.saveFormat = setSave.getElementsByTagName("format").item(0).getTextContent();

        this.isLog = Boolean.parseBoolean(setLog.getElementsByTagName("enabled").item(0).getTextContent());
        this.logFile = setLog.getElementsByTagName("fileName").item(0).getTextContent();
    }

    public boolean isLoadEnabled() {
        return isLoad;
    }

    public String getLoadFileName() {
        return loadFile;
    }

    public String getLoadFormat() {
        return loadFormat;
    }

    public boolean isSaveEnabled() {
        return isSave;
    }

    public String getSaveFileName() {
        return saveFile;
    }

    public String getSaveFormat() {
        return saveFormat;
    }

    public boolean isLogEnabled() {
        return isLog;
    }

    public String getLogFileName() {
        return logFile;
    }
}
