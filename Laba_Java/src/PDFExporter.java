import java.awt.FileDialog;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFrame;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Class for exporting TableData to PDF format via iText library
 * and save it into selected directory
 * @author Arslan
 *
 */

public class PDFExporter extends JFrame {
    /**
     * path to target pdf font
     */
    public static final String FONT = "./fonts/arial.ttf";
    /**
     * Constructor initializing export PDF operation
     * @param data - target TableData to export
     */
    public PDFExporter(DataTable data)
    {
        FileDialog fileDial = new FileDialog(this, "Export PDF", FileDialog.SAVE);
        fileDial.setFile("*.pdf");
        fileDial.setVisible(true);
        String fileFullName = fileDial.getDirectory() + fileDial.getFile();
        if(fileFullName != null && fileFullName != "nullnull")
        {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfPTable pdfTable = new PdfPTable(DataTable.columns.length);

            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileFullName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            BaseFont bf = null;

            try {
                bf=BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (DocumentException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            Font font = new Font(bf, 12);

            for(int i =0; i < DataTable.columns.length; i++)
                pdfTable.addCell(new PdfPCell(new Phrase(DataTable.columns[i],font)));
            for(int i = 0; i < data.lines.size(); i++){
                data.lines.get(i).AddToPdfTable(pdfTable, font);
            }

            document.open();

            try {
                document.add(pdfTable);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            document.close();
        }
    }
}
