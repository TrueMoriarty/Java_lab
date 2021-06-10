import javax.swing.JFrame;
import java.awt.FileDialog;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;

public class FileLoad extends JFrame{
    static String fileNameOpen = null;
    public FileLoad(String str, MainForm form, DefaultTableModel model)
    {
        try{
            FileDialog open = new FileDialog(this,str,FileDialog.LOAD);
            open.setFile("*.txt");// default filename
            open.setVisible(true);

//get user response
            fileNameOpen = open.getDirectory() + open.getFile();
            if(fileNameOpen==null||fileNameOpen.equals("nullnull"))
                return;
            BufferedReader reader = new BufferedReader(new FileReader(fileNameOpen));
            List<DataTable.Line> lines = new ArrayList<DataTable.Line>();
            String line;
            while((line= reader.readLine()) != null)
            {
                String[] splitted = line.split(FileSave.columnSeparator);
                if(splitted.length == DataTable.columns.length)
                    lines.add(new DataTable.Line(splitted));
            }
            form.data = new DataTable(lines);
            form.data.SetDataInTable(model);
            reader.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
