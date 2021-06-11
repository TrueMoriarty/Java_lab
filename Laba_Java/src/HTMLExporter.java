import java.awt.FileDialog;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFrame;

/**
 * Class for exporting TableData to HTML format and save it into selected directory
 * @author Arlsn
 *
 */
public class HTMLExporter extends JFrame {
    /**
     * Constructor initializing export HTML operation
     * @param data - target TableData to export
     */
    public HTMLExporter(DataTable data)
    {
        FileDialog fileDial = new FileDialog(this, "Export HTML", FileDialog.SAVE);
        fileDial.setFile("*.html");
        fileDial.setVisible(true);
        String fileFullName = fileDial.getDirectory() + fileDial.getFile();

        if(fileFullName != null && fileFullName != "nullnull")
        {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new FileWriter(fileFullName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            pw.println("<html><body><table border style=\"border-collapse: collapse;\"><tr>");

//table headers
            for(int i =0; i < DataTable.columns.length; i++)
                pw.println("<th>" + DataTable.columns[i]);
            for(int i =0; i < data.lines.size(); i++)
                pw.println(data.lines.get(i).GetHTMLTableString());
            pw.println("</table></body></html>");
            pw.close();
        }
    }
}
