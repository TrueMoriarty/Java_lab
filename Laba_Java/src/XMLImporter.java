import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Class used to load table data from XML save file
 * @author Arslan
 */
public class XMLImporter extends JFrame{
    /**
     * Constructor initializing path
     */
    private String currentFileFullName;

    public XMLImporter()
    {
        FileDialog open = new FileDialog(this, "XML import", FileDialog.LOAD);
        open.setFile("*.xml");// default filename
        open.setVisible(true);
        //get user response
        currentFileFullName = open.getDirectory() + open.getFile();
    }

    public DataTable ReadData()
    {
        if(currentFileFullName==null||currentFileFullName.equals("nullnull"))
            return null;
        Document doc = null;
        try
        {
            DocumentBuilder dBuilder = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            doc = dBuilder.parse(new File(currentFileFullName));
            doc.getDocumentElement().normalize();
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NodeList nlRecords = doc.getElementsByTagName(XMLExporter.recordTag);
        List<DataTable.Line> dataList = new ArrayList<DataTable.Line>();
        for(int i = 0; i < nlRecords.getLength(); i++)
        {
            dataList.add(new DataTable.Line(nlRecords.item(i)));
        }
        return new DataTable(dataList);
    }
}
