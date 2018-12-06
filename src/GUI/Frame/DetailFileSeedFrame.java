package GUI.Frame;

import Controller.MyComputer;
import Model.Computer;
import Model.FileSeed;
import util.Constant;
import util.Helper;

import javax.swing.*;
import javax.swing.plaf.multi.MultiLabelUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailFileSeedFrame extends JDialog implements ActionListener {
    private final int WIDTH_FRAME = 640;
    private final int HEIGHT_FRAME = 300;
    private FileSeed fileSeed;
    private JFrame parentFrame;
    private JPanel container;
    private JButton downloadBtn;
    private JButton closeBtn;
    private JButton checkBtn;
    private TableDemo tableDemo;
    public DetailFileSeedFrame(JFrame parentFrame, int index){
        super(parentFrame, "", false);
        this.fileSeed = MyComputer.getInstance().getSharedList().getElementAt(index);
        this.parentFrame = parentFrame;
        initLayout();
        addListener();
        initFrame();
    }

    private void initLayout(){
        downloadBtn = new JButton("Download");
//        if(fileSeed.getStatus() == Constant.SHARING || fileSeed.getStatus() == Constant.DOWNLOADING){
//            downloadBtn.setEnabled(false);
//        }
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        createLine("File name: ", fileSeed.getName());
        createLine("MD5: ", fileSeed.getMd5());
        createLine("Size: ", fileSeed.getMegaSize() + " MB");
        createLine("Status: ", Helper.getStatus(fileSeed.getStatus()));
        container.add(Box.createVerticalGlue());
        Box lineBtn = Box.createHorizontalBox();
        checkBtn = new JButton("Uncheck All");
        lineBtn.add(Box.createHorizontalGlue());
        lineBtn.add(checkBtn);
        container.add(lineBtn);
        tableDemo = new TableDemo(fileSeed.getListComputer());
        container.add(tableDemo);

        Box lineButton = Box.createHorizontalBox();
        lineButton.add(downloadBtn);
        lineButton.add(Box.createRigidArea(new Dimension(50, 0)));
        closeBtn = new JButton("Close");
        parentFrame.getRootPane().setDefaultButton(closeBtn);
        closeBtn.requestFocus();
        lineButton.add(closeBtn);
        container.add(Box.createRigidArea(new Dimension(10, 0)));
        container.add(lineButton);
        this.add(container);

    }


    private void initFrame(){
        this.setTitle(this.fileSeed.getName());
        this.setModal(true);
        this.setResizable(false);
        this.setSize(WIDTH_FRAME, HEIGHT_FRAME);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void addListener(){
        this.closeBtn.addActionListener(this);
        this.checkBtn.addActionListener(this);
        this.downloadBtn.addActionListener(this);
    }


    private void createLine(String title, String content){
        Dimension col1 = new Dimension(128, 20);
        Dimension col2 = new Dimension(240, col1.height);
        Box line1 = Box.createHorizontalBox();
        JPanel c1 = new JPanel();
        c1.setLayout(new BoxLayout(c1, BoxLayout.X_AXIS));
        JLabel tName = new JLabel(title);
        c1.setPreferredSize(col1);
        c1.setMaximumSize(col1);
        c1.setMinimumSize(col1);
        c1.add(Box.createHorizontalGlue());
        c1.add(tName);
        line1.add(c1);
        JLabel name = new JLabel("<html><p>"+content+"</p></html>",SwingConstants.LEFT);
        line1.add(name);
        line1.add(Box.createHorizontalGlue());
        container.add(line1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if(button == this.closeBtn){
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }else{
            if(button == this.checkBtn){
                if(this.checkBtn.getText().equals("Check All")){
                    this.checkBtn.setText("Uncheck All");
                }else {
                    this.checkBtn.setText("Check All");
                }
                TableModel data = this.tableDemo.getTable().getModel();
                for(int i = 0; i < data.getRowCount(); i++){
                    boolean v = (boolean) data.getValueAt(i, 3);
                    data.setValueAt(!v, i, 3);
                }
            }else{
                if(button == this.downloadBtn){
                    ArrayList<Computer> listCom = new ArrayList<>();
                    TableModel data = this.tableDemo.getTable().getModel();
                    for(int i = 0; i < data.getRowCount(); i++){
                        boolean v = (boolean) data.getValueAt(i, 3);
                        if(v){
                            listCom.add(fileSeed.getListComputer().get(i));
                        }
                    }
                    if(listCom.size() == 0){
                        JOptionPane.showMessageDialog(this.parentFrame, "You need to select at least one computer to download the file");
                    }else{
                        MyComputer.getInstance().addNewDownloadTask(this.fileSeed.getMd5());
                        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                    }
                }
            }
        }
    }
}

class TableDemo extends JPanel {
    private boolean DEBUG = false;
    private JTable table;
    public TableDemo(ArrayList<Computer> list) {
        super(new GridLayout(1, 0));
        table = new JTable(new MyTableModel(list));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        TableColumn column = null;
        for(int i = 0; i < 4; i++){
            column = table.getColumnModel().getColumn(i);
            if(i == 0){
                column.setPreferredWidth(5);
            }else{
                if(i == 3){
                    column.setPreferredWidth(100);
                }else {
                    column.setPreferredWidth(150);
                }
            }
        }
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    public JTable getTable(){
        return this.table;
    }

    class MyTableModel extends AbstractTableModel {
        private ArrayList<Computer> list;
        private String[] columnNames = { "#", "Computer name", "IP", "Select" };
        private Object[][] data = {};
        public MyTableModel(ArrayList<Computer> list){
            this.list = list;
            data = new Object[this.list.size()][4];
            for(int i = 0; i < this.list.size(); i++){
                data[i][0] = (i+1)+"";
                data[i][1] = this.list.get(i).getName();
                data[i][2] = this.list.get(i).getIp();
                data[i][3] = new Boolean(true);
            }
        }
        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            if (col < 3) {
                return false;
            } else {
                return true;
            }
        }

        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value + " (an instance of "
                        + value.getClass() + ")");
            }

            data[row][col] = value;
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i = 0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j = 0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }
}

