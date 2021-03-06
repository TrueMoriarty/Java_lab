import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.lang.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;

public class DataTable {

    public static String[] columns = {"ID", "Producer", "Name", "Year", "Rating", "Fees"};
    private static Boolean[] columnAscending = {false, false, false, true, true, true};
    public static String[] defaultColumnValues = { "Producer", "Name", "Year", "Rating", "Fees"};

    public static class Line{
        Object[] data;
        public Line(Object[] ln) throws IllegalArgumentException
        {
            if(ln==null || ln.length == 0)
                throw new IllegalArgumentException("argument was null or empty!");
            if(ln.length != columns.length)
                throw new IllegalArgumentException("array length doesn't match column count!");
            data = ln;
        }
        public Line(Node nod)
        {
            Object[] values = new Object[columns.length];
            NamedNodeMap attrs = nod.getAttributes();
            for(int i =0; i < columns.length; i++)
            {
                values[i] = attrs.getNamedItem(columns[i]).getNodeValue();
            }
            data = values;
        }


        int GetID()
        {
            return Integer.parseInt(data[0].toString());
        }

        void SetValue(int columnIndex, Object value) throws IndexOutOfBoundsException
        {
            if(columnIndex >= columns.length || columnIndex < 0)
                throw new IndexOutOfBoundsException(columnIndex);
            data[columnIndex] = value;
        }

        Object[] GetData()
        {
            return data;
        }

        Boolean MeetsCondition(int columnIndex, String value) throws IndexOutOfBoundsException
        {
            if(columnIndex >= columns.length || columnIndex < 0)
                throw new IndexOutOfBoundsException(columnIndex);
            if(data[columnIndex]==null && value == null)
                return true;
            else
            {
                if((data[columnIndex]==null && value != null)
                        ||(data[columnIndex] !=null && value == null))
                    return false;
                if(data[columnIndex].toString().equals(value.toString()))
                    return true;
            }
            return false;
        }

        public Node ApplyDataToXMLDoc(Node nod, Document doc)
        {
            Element record = doc.createElement("record");
            nod.appendChild(record);
            for(int i =0; i < columns.length; i++)
            {
                String dat = (data[i] == null? "" : data[i].toString());
                record.setAttribute(columns[i], dat);
            }
            return nod;
        }

        /**
         * Add line to pdf table
         * @param pdfTable - target table
         * @param font - line font
         */
        public void AddToPdfTable(PdfPTable pdfTable, Font font)
        {
            for(int i =0; i < data.length; i++)
            {
                String dat = (data[i] == null? "" : data[i].toString());
                pdfTable.addCell(new Phrase(dat, font));
            }
        }

        /**
         * Get html code corresponding one row in \<table\>
         * @return
         */
        public String GetHTMLTableString()
        {
            String res = "<tr>";
            for(int i = 0; i < data.length; i++)
            {
                String dat = (data[i] == null? "" : data[i].toString());
                res+= "<td>" + dat;
            }
            return res;
        }
    }

    List<Line> lines = new ArrayList<Line>();

    public DataTable(DefaultTableModel model)
    {
        try {
            for(int i = 0; i < model.getRowCount(); i++)
                lines.add(new Line(GetRowAt(i, model)));
        }
        catch(IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    public DataTable(List<Line> lns)
    {
        lines = lns;
    }

    public DataTable()
    {}

    /**
     * Get line from given table model
     * @param row - target line
     * @param model - inspected model
     * @return
     */
    Object[] GetRowAt(int row, DefaultTableModel model)
    {
        Object[] result = new Object[model.getColumnCount()];
        for (int i = 0; i < model.getColumnCount(); i++) {
            result[i] = model.getValueAt(row, i);
        }
        return result;
    }

    /**
     * Clear all rows
     * @param model - affected table model
     */
    private void ClearTable(DefaultTableModel model)
    {
        if(model != null)
            while(model.getRowCount() > 0)
                model.removeRow(0);
    }

    /**
     * Reflect data to table model
     * @param model - target table
     */
    public void SetDataInTable(DefaultTableModel model)
    {
        Object[][] array = new Object[lines.size()][];
        for(int i = 0; i < lines.size(); i++)
            array[i] = lines.get(i).GetData();
        model.setDataVector(array, columns);
    }

    /**
     * Find all lines, which column[columnIndex] equals target value
     * @param columnIndex - inspected column index
     * @param value - target value
     */
    public DataTable Search(int columnIndex, String value) throws IndexOutOfBoundsException
    {
        if(columnIndex >= columns.length || columnIndex < 0)
            throw new IndexOutOfBoundsException(columnIndex);
        List<Line> res = new ArrayList<Line>();
        for(int i = 0; i < lines.size();i++)
            if(lines.get(i).MeetsCondition(columnIndex, value))
                res.add(lines.get(i));
        return new DataTable(res);
    }

    /**
     * Sort all lines, by column[columnIndex] value
     * @param columnIndex - inspected column index
     */
    public DataTable Sort(int columnIndex)
    {
        if(lines.size() > 0)
        {
            List<Line> res = new ArrayList<Line>(lines);
            Collections.sort(res, new Comparator<Line>() {
                @Override
                public int compare(Line o1, Line o2) {
                    Object data1 = o1.GetData()[columnIndex],
                            data2 = o2.GetData()[columnIndex];
                    if(data1==null)
                        if(data2==null)
                            return 0;
                        else
                            return -1;
                    else
                    if(data2==null)
                        return 1;
                    else
                        return data1.toString().compareTo(
                                data2.toString());
                }});
            if(columnAscending[columnIndex])
                Collections.reverse(res);
            return new DataTable(res);
        }
        return new DataTable();
    }

    /**
     * Add row to data list
     * @param values - source values (ID NOT INCLUDED)
     * @return instantiated values (including ID)
     */
    public Object[] AddRow(Object[] values)
    {
        int newID = 0;
        for(int i = 0; i < lines.size(); i++)
        {
            int id = lines.get(i).GetID();
            if(id > newID)
                newID = id;
        }
        newID++;
        Object[] nvls = new Object[columns.length];
        System.arraycopy(values, 0, nvls, 1, values.length);
        nvls[0] = newID;
        lines.add(new Line(nvls));
        return nvls;
    }

    /**
     * Remove row from data list
     * @param index - target index (not id)
     * @param model - model to remove line from too
     * @throws IndexOutOfBoundsException - index out of range
     */

    public void RemoveRow(int index, DefaultTableModel model) throws IndexOutOfBoundsException
    {
        if(index < 0 || index >= model.getRowCount())
            throw new IndexOutOfBoundsException(index);
        model.removeRow(index);
        lines.remove(index);
    }

    /**
     * Change one value in one row
     * @param rowId - inspected row id
     * @param column - inspected column index
     * @param newValue - value to replace old one with
     * @throws IllegalArgumentException - target row not found - no changes made
     */

    public void UpdateRow(int rowId, int column, Object newValue) throws IllegalArgumentException
    {
        for(int i = 0; i < lines.size(); i++)
            if(lines.get(i).GetID() == rowId)
            {
                lines.get(i).SetValue(column, newValue);
                return;
            }
        throw new IllegalArgumentException("row with requested rowId not found in source data!");
    }
}
