import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

public class MainForm extends JFrame {
    private JFrame cinemaList;
    private DefaultTableModel model;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem saveMenu;
    JMenuItem openMenu;
    JMenuItem addMenu;
    JMenuItem exitMenu;
    private JToolBar toolBar;
    private JButton saveButton;
    private JButton loadButton;
    private JButton addButton;
    private JButton removeButton;
    private JScrollPane scroll;
    private JTable cinema;
    private JComboBox producer;
    private JTextField cinemaName;
    private JButton filter;

    public MainForm(DataTable dat) {
        this.cinemaList = new JFrame("List of films");
        this.cinemaList.setSize(700, 500);
        this.cinemaList.setLocation(200, 100);
        this.cinemaList.setDefaultCloseOperation(3);

        this.menuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.openMenu = new JMenuItem("Open");
        this.saveMenu = new JMenuItem("Save");
        this.addMenu = new JMenuItem("Add");
        this.exitMenu = new JMenuItem("Exit");

        this.fileMenu.add(this.openMenu);
        this.fileMenu.add(this.saveMenu);
        this.fileMenu.add(this.addMenu);
        this.fileMenu.add(this.exitMenu);
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(Box.createHorizontalGlue());
        this.cinemaList.setJMenuBar(this.menuBar);

        this.saveButton = new JButton(new ImageIcon("./images/save.png"));
        this.loadButton = new JButton(new ImageIcon("./images/load.png"));
        this.addButton = new JButton(new ImageIcon("./images/add.png"));
        this.removeButton = new JButton(new ImageIcon("./images/remove.png"));

        this.saveButton.setPreferredSize(new Dimension(32, 32));
        this.loadButton.setPreferredSize(new Dimension(32, 32));
        this.addButton.setPreferredSize(new Dimension(32, 32));
        this.removeButton.setPreferredSize(new Dimension(32, 32));

        this.saveButton.setToolTipText("Save Movie List");
        this.loadButton.setToolTipText("Upload a movie");
        this.addButton.setToolTipText("Add a Movie");
        this.removeButton.setToolTipText("Delete a movie");
        this.toolBar = new JToolBar("The toolbar");

        this.toolBar.add(this.saveButton);
        this.toolBar.add(this.loadButton);
        this.toolBar.add(this.addButton);
        this.toolBar.add(this.removeButton);
        this.cinemaList.setLayout(new BorderLayout());

        this.cinemaList.add(this.toolBar, "North");
        this.producer = new JComboBox(new String[]{"Director"});
        this.cinemaName = new JTextField("Movie Title");
        this.filter = new JButton("Search");

        JPanel filterPanel = new JPanel();
        filterPanel.add(this.producer);
        filterPanel.add(this.cinemaName);
        filterPanel.add(this.filter);

        this.cinemaList.add(filterPanel, "South");
        this.cinemaList.setVisible(true);
    }
}