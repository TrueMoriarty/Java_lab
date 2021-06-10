import java.awt.FileDialog;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Class used to save table data to XML save file
 * @author Arslan
 *
 */
public class XMLExporter extends JFrame {
    public static String recordTag = "record";
    /**
     * Constructor saving TableData data
     * @param data - data to save
     * @throws ParserConfigurationException - thrown on parser initialization error
     */
    public XMLExporter(DataTable data) throws ParserConfigurationException
    {
        FileDialog fileDial = new FileDialog(this, "Save XML", FileDialog.SAVE);
        fileDial.setFile("*.xml");
        fileDial.setVisible(true);
        String fileFullName = fileDial.getDirectory() + fileDial.getFile();
        if(fileFullName != null && fileFullName != "nullnull")
        {
            Document doc;
            try {
                doc = DocumentBuilderFactory
                        .newInstance()
                        .newDocumentBuilder().
                                newDocument();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                throw e;
            }
            Node recList = doc.createElement("recordsList");
            doc.appendChild(recList);
            for(int i =0; i < data.lines.size(); i++)
                data.lines.get(i).ApplyDataToXMLDoc(recList, doc);
            try{
                Transformer trans = TransformerFactory.newInstance().newTransformer();
                trans.setOutputProperty(OutputKeys.METHOD, "xml");
                trans.setOutputProperty(OutputKeys.INDENT, "yes");
                var fs = new FileOutputStream(fileFullName);
                trans.transform(new DOMSource(doc),
                        new StreamResult(fs));
                fs.close();
            }
            catch(TransformerConfigurationException e){
                e.printStackTrace();
            }
            catch(TransformerException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}