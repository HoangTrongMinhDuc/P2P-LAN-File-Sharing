package GUI.Frame;

import Controller.MyComputer;
import GUI.CustomComponent.ComputerList;
import GUI.CustomComponent.FileList;
import GUI.Tab.DownloadingTab;
import GUI.Tab.SharingTab;
import Model.Computer;
import Model.FileSeed;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class MainFrame extends JFrame implements ActionListener {
//    public static MainFrame instance = new MainFrame();
//    public static MainFrame getInstance(){return instance;}

    private final int FRAME_WIDTH = 1024;
    private final int FRAME_HEIGHT = 680;
    private JPanel leftPanel;
    private JMenuBar menuBar;
    private JTabbedPane tabbedPane;
    private JButton btnAddComputer;
    private JButton btnRefresh;
    private JButton btnSetting;
    private DownloadingTab downloadingTab;
    private  DefaultListModel<Computer> models;
    volatile private JList<Computer> listComputer;
    private JList<FileSeed> listFileSeed;
    private Thread updateThread = null;
    public MainFrame(DefaultListModel<Computer> model){
        this.models = model;
        initLayout();
        setListener();
        initMainFrame();
    }

    private void initMainFrame(){
        this.setTitle("P2P Local Area Network - File Sharing");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(this.FRAME_WIDTH, this.FRAME_HEIGHT));
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.LIGHT_GRAY);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void initLayout(){
        initLeftSide();
        tabbedPane = new JTabbedPane();
        initFirstTab();
        initSecTab();
        iniThirdTab();
        this.add(tabbedPane, BorderLayout.CENTER);

    }

    private void initLeftSide(){
        leftPanel = new JPanel();
        leftPanel.setBorder(new TitledBorder("List connection"));
        leftPanel.setPreferredSize(new Dimension(300, 500));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        try {
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setPreferredSize(new Dimension(leftPanel.getWidth(), leftPanel.getHeight() * 8 / 10));
            listComputer = new JList<Computer>(models);
            listComputer.setCellRenderer(new ComputerList());
            listComputer.setVisibleRowCount(10);
            listComputer.setVisible(true);
            if(updateThread == null){
                updateThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            updateList(MyComputer.getInstance().listConnected);
                            try{
                                Thread.sleep(1000);
                            }catch (Exception e){
                                System.out.println("2323"+e.getMessage());
                            }

                        }
                    }
                });
//                updateThread.start();
            }
            leftPanel.add(new JScrollPane(listComputer));
        }catch (Exception e){
            System.out.println("Left panel: " + e.getMessage());
        }
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnAddComputer = new JButton("Add");
        btnPanel.add(btnAddComputer);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnPanel.add(Box.createRigidArea(new Dimension(10,0)));
        btnRefresh = new JButton("Refresh");
        btnPanel.add(btnRefresh);
        btnPanel.add(Box.createRigidArea(new Dimension(10,0)));
        btnSetting = new JButton("Setting");
        btnPanel.add(btnSetting);
        leftPanel.add(btnPanel);
        this.add(leftPanel, BorderLayout.LINE_START);
    }

    private void initFirstTab(){
        JPanel allFilesPanel = new JPanel();
        this.listFileSeed = new JList<>(MyComputer.getInstance().getSharedList());
        listFileSeed.setCellRenderer(new FileList());
        allFilesPanel.setLayout(new BorderLayout());
        allFilesPanel.add(new JScrollPane(listFileSeed), BorderLayout.CENTER);
        tabbedPane.addTab("All files",null,allFilesPanel, "All files");
//        listFileSeed.setSelectionModel(new DefaultListSelectionModel(){
//            private static final long serialVersionUID = 1L;
//
//            boolean gestureStarted = false;
//
//            @Override
//            public void setSelectionInterval(int index0, int index1) {
//                if(!gestureStarted){
//                    if (isSelectedIndex(index0)) {
//                        super.removeSelectionInterval(index0, index1);
//                    } else {
//                        super.addSelectionInterval(index0, index1);
//                    }
//                }
//                gestureStarted = true;
//            }
//
//            @Override
//            public void setValueIsAdjusting(boolean isAdjusting) {
//                if (isAdjusting == false) {
//                    gestureStarted = false;
//                }
//            }
//
//        });
    }

    private void initSecTab(){
        downloadingTab = new DownloadingTab();
        tabbedPane.addTab("Downloading",null,downloadingTab, "Your files downloading");
        MyComputer.getInstance().setJListDownloading(downloadingTab.getList());
    }

    private void iniThirdTab(){
        SharingTab sharingTab = new SharingTab();
        tabbedPane.addTab("Sharing", null, sharingTab, "Your sharing files");
    }


    private void setListener(){
        btnAddComputer.addActionListener(this);
        btnRefresh.addActionListener(this);
        btnSetting.addActionListener(this);
        listFileSeed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(listFileSeed.getModel().getSize() == 0)  return;
                super.mouseClicked(e);
                int index = listFileSeed.locationToIndex(e.getPoint());
                openFileDetail(index);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource();
        if(btn == btnSetting){
            new SettingFrame(this);
        }
        if(btn == btnAddComputer){
            new AddComputerFrame(this, this.models);
        }
        if(btn == btnRefresh){
            this.listComputer.updateUI();
        }
    }

    private void openFileDetail(int index){
        System.out.println("open detail");
        new DetailFileSeedFrame(this, index);
    }
    public void updateList(DefaultListModel<Computer> model){
        if(model != null){
            System.out.println("update list1");
            this.listComputer.setModel(model);
            System.out.println("update list2");
            try{
//                this.listComputer.updateUI();

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println("update list");
        }
    }

    public JList<Computer> getJList() {
        listComputer.getModel().addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                System.out.println("DATA ADDDDD");
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {

            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                System.out.println("DATA CHANGEEEE");
            }
        });

        return this.listComputer;
    }

    public void updateDownloadingUI(){
        if(this.downloadingTab != null){
            this.downloadingTab.updateListUI();
        }
    }
}
