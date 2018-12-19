package GUI.Frame;

import Controller.MyComputer;
import GUI.CustomComponent.ComputerList;
import GUI.CustomComponent.FileList;
import GUI.Tab.DownloadingTab;
import GUI.Tab.SharingTab;
import Model.Computer;
import Model.FileSeed;
import util.Constant;

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

    private final int FRAME_WIDTH = 1024;
    private final int FRAME_HEIGHT = 680;
    private JPanel leftPanel;
    private JTabbedPane tabbedPane;
    private JButton btnAddComputer;
    private JButton btnRefresh;
    private JButton btnSetting;
    private DownloadingTab downloadingTab;
    private  DefaultListModel<Computer> models;
    volatile private JList<Computer> listComputer;
    private JList<FileSeed> listFileSeed;
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
        tabbedPane.addTab("All files",new ImageIcon(new ImageIcon(Constant.ALLFILE_ICON).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)),allFilesPanel, "All files");
    }

    private void initSecTab(){
        downloadingTab = new DownloadingTab();
        tabbedPane.addTab("Downloading",new ImageIcon(new ImageIcon(Constant.DOWNLOAD_ICON).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)),downloadingTab, "Your files downloading");
        MyComputer.getInstance().setJListDownloading(downloadingTab.getList());
    }

    private void iniThirdTab(){
        SharingTab sharingTab = new SharingTab(this);
        tabbedPane.addTab("Sharing", new ImageIcon(new ImageIcon(Constant.SHARING_ICON).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)), sharingTab, "Your sharing files");
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

    public void updateDownloadingUI(){
        if(this.downloadingTab != null){
            if(this.tabbedPane.getSelectedIndex() == 1)
                this.downloadingTab.updateListUI();
        }
    }
}
