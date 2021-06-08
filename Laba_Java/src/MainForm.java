// Подключение графических библиотек
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class MainForm extends JFrame {
    //top menu
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem saveMenu;
    JMenuItem openMenu;
    JMenuItem addMenu;
    JMenuItem exitMenu;

    private JFrame cinemaList;

    //toolbar
    private JToolBar toolBar;
    private JButton saveButton;
    private JButton loadButton;
    private JButton addButton;
    private JButton removeButton;

    //search
    private JLabel searchByLabel;
    private JComboBox<String> searchPBox;
    private JTextField searchField;
    private JButton searchButton;

    //sorting
    private JLabel sortingByLabel;
    private JComboBox<String> sortParamBox;

    private DefaultTableModel model;
    private JTable recordsTable;
    private JScrollPane scroll;

    public DataTable data;
    private DataTable viewData;

    public MainForm(DataTable dat) {
        //window
        super("Cinema list");
        setSize(900, 600);
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openMenu = new JMenuItem("Open");
        saveMenu = new JMenuItem("Save");
        addMenu = new JMenuItem("Add");
        exitMenu = new JMenuItem("Exit");

        fileMenu.add(openMenu);
        fileMenu.add(saveMenu);
        fileMenu.add(addMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenu);

        //add menu
        menuBar.add(fileMenu);
        menuBar.add(Box.createHorizontalGlue());
        cinemaList.setJMenuBar(menuBar);

        //toolbar buttons
        saveButton = new JButton(new ImageIcon("./images/save.png"));
        loadButton = new JButton(new ImageIcon("./images/load.png"));
        addButton = new JButton(new ImageIcon("./images/add.png"));
        removeButton = new JButton(new ImageIcon("./images/remove.png"));

        saveButton.setBorderPainted(false);
        loadButton.setBorderPainted(false);
        addButton.setBorderPainted(false);
        removeButton.setBorderPainted(false);

        saveButton.setPreferredSize(new Dimension(32, 32));
        loadButton.setPreferredSize(new Dimension(32, 32));
        addButton.setPreferredSize(new Dimension(32, 32));
        removeButton.setPreferredSize(new Dimension(32, 32));

        saveButton.setToolTipText("Save Movie List");
        loadButton.setToolTipText("Upload a movie");
        addButton.setToolTipText("Add a Movie");
        removeButton.setToolTipText("Delete a movie");
        toolBar = new JToolBar("The toolbar");

        toolBar.add(saveButton);
        toolBar.add(loadButton);
        toolBar.add(addButton);
        toolBar.add(removeButton);
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(Box.createHorizontalStrut(5));

        //toolbar search
        searchByLabel = new JLabel("search by: ");
        searchPBox = new JComboBox<String>(DataTable.columns);
        searchField = new JTextField();
        searchButton = new JButton("search");

        toolBar.add(searchByLabel);
        toolBar.add(searchPBox);
        toolBar.add(searchField);
        toolBar.add(saveButton);
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(Box.createHorizontalStrut(5));

        //toolbar sort
        sortingByLabel = new JLabel("sort by: ");
        sortParamBox = new JComboBox<String>(DataTable.columns);
        toolBar.add(sortingByLabel);
        toolBar.add(sortParamBox);
        toolBar.setFloatable(false);
        getContentPane().add(toolBar, BorderLayout.NORTH);

        // table
        model = new DefaultTableModel(new String[][] {
                {"1","1","1","1","1","1"},
                {"2","2","2","2","2","2"},
                {"3","1","2","2","2","3"},
                {"4","1","2","2","2","5"}}, DataTable.columns) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //id cannot be changed!
                return column != 0;
            }
        };

        recordsTable = new JTable(model);
        scroll = new JScrollPane(recordsTable);
        add(scroll, BorderLayout.CENTER);

        //events
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search();
            }

        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Save();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Load();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Add();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Delete();
            }
        });

        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Exit();
            }});

        addMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewFile();
            }});

        openMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Load();
            }});

        saveMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Save();
            }});

        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                Edit(e.getFirstRow(), e.getColumn());
            }
        });

        sortParamBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    Sort();
            }
        });

        //data init
        if (dat == null)
            data = new DataTable(model);
        else
            data = dat;
        viewData = data;
        setVisible(true);
    }

    void Search() {
        String searchText = searchField.getText();
        // clear search
        if (searchText == null || searchText.equals("")) {
            viewData = data;
            Sort();
        }
        else {
            viewData = data.Search(searchPBox.getSelectedIndex(), searchField.getText()).Sort(sortParamBox.getSelectedIndex());
            viewData.SetDataInTable(model);
        }
    }

    String[] getRowAt(int row){
        String[] result = new String[model.getColumnCount()];
        for (int i = 0; i < model.getColumnCount(); ++i){
            result[i] = model.getValueAt(row, i).toString();
        }
        return result;
    }

    void Save() {
        new FileSave("Save table", data);
    }

    void Load() {
        new FileLoad("Load Table", this, model);
        viewData = data;
        Sort();
    }

    void NewFile() {
        viewData = data = new DataTable();
        viewData.SetDataInTable(model);
    }

    void Edit(int row, int column) {
        if(row > 0 && column > 0) {
            Object newValue = model.getValueAt(row, column);
            int id = Integer.parseInt(model.getValueAt(row, 0).toString());
            data.UpdateRow(id, column, newValue);
        }
    }

    void Add() {
        model.addRow(data.AddRow(DataTable.ColumnValuesDefault));
    }

    void Delete() {
        try {
            data.RemoveRow(recordsTable.getSelectedRow(), model);
        }
        catch (IndexOutOfBoundsException e){
            JOptionPane.showConfirmDialog(null, "Selected the row to delete!");
        }
    }

    void Sort() {
        viewData.Sort(sortParamBox.getSelectedIndex()).SetDataInTable(model);
    }

    void Exit() {
        dispose();
    }
}
