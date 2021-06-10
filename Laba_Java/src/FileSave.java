import java.awt.FileDialog;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class FileSave extends JFrame {
    public static String columnSeparator = "\\|";
    public FileSave(String str, DataTable data){
        FileDialog fdialog = new FileDialog(this,str,FileDialog.SAVE);
        fdialog.setFile("*.txt");// Set default
        fdialog.setVisible(true);

//Get user result
        String fileFullName = fdialog.getDirectory() + fdialog.getFile();
        if(fileFullName != null) //if not aborted
        {
            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileFullName));
                for(int i = 0; i < data.lines.size(); i++)
                {
                    var ln = data.lines.get(i).GetData();
                    for(int j = 0; j < ln.length; j++){
                        writer.write(ln[j].toString());
                        if(j!=DataTable.columns.length - 1)
                            writer.write("|");
                    }
                    writer.write("\r\n");
                }
                writer.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
