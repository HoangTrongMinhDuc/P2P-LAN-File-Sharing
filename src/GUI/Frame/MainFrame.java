package GUI.Frame;

import GUI.CustomComponent.ComputerList;
import GUI.CustomComponent.FileList;
import Model.Computer;
import Model.FileSeed;
import mdlaf.shadows.DropShadowBorder;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    private final int FRAME_WIDTH = 1024;
    private final int FRAME_HEIGHT = 680;
    private JPanel leftPanel;
    private JMenuBar menuBar;
    private JTabbedPane tabbedPane;
    private JButton btnAddComputer;
    private JButton btnRefresh;
    private JButton btnSetting;
    private  DefaultListModel<Computer> models;
    private JList<Computer> listComputer;
    private JList<FileSeed> listFileSeed;
    public MainFrame(){
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
            this.models = new DefaultListModel<>();
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));
            models.addElement(new Computer("Hello", "Its me", 6969, 0, 1, null));

            listComputer = new JList<Computer>(models);
            listComputer.setCellRenderer(new ComputerList());
            listComputer.setVisibleRowCount(10);
            listComputer.setVisible(true);
            leftPanel.add(new JScrollPane(listComputer));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        JPanel btnPanel = new JPanel();
        btnPanel.setBorder(new DropShadowBorder(Color.RED, 5,5));
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
        DefaultListModel<FileSeed> modelSeed = new DefaultListModel<>();
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hellretyryo.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.rtytreyplanet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hellwerddddddddddddddddddddddddddddddddddddddddddddddddddddddtwerterto.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Helloewtr.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Helretyeytrlo.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Helewrterlo.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hellshterhreho.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.plgesrtanet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "2ceedd09fbf1c6e372e1b9a664b22a7574a78e51", 1024, "C://", null, 4));

        this.listFileSeed = new JList<>(modelSeed);

        listFileSeed.setCellRenderer(new FileList());
        allFilesPanel.setLayout(new BorderLayout());
        allFilesPanel.add(new JScrollPane(listFileSeed), BorderLayout.CENTER);
        tabbedPane.addTab("All files",null,allFilesPanel, "All files");
    }

    private void initSecTab(){
        JPanel downloadPanel = new JPanel();
        tabbedPane.addTab("Downloading",null,downloadPanel, "Your files downloading");
    }


    private void setListener(){
        btnAddComputer.addActionListener(this);
        btnRefresh.addActionListener(this);
        btnSetting.addActionListener(this);
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
    }
}