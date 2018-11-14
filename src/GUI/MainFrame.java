package GUI;

import Model.Computer;
import Model.FileSeed;
import Model.FileShare;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    public static  void main(String args[]){
        new MainFrame();
    }

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
        this.setLocationRelativeTo(null);
    }

    private void initLayout(){
        leftPanel = new JPanel();
        leftPanel.setBorder(new TitledBorder("List connection"));
        leftPanel.setPreferredSize(new Dimension(300, 500));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        try {
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setPreferredSize(new Dimension(leftPanel.getWidth(), leftPanel.getHeight() * 8 / 10));
            DefaultListModel<Computer> models = new DefaultListModel<>();
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

            JList<Computer> listComputer = new JList<Computer>(models);
            listComputer.setCellRenderer(new ComputerList());
            listComputer.setVisibleRowCount(10);
            listComputer.setVisible(true);
//            scrollPane.add(listComputer, 0);
            leftPanel.add(new JScrollPane(listComputer));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnAddComputer = new JButton("Add");
        btnPanel.add(btnAddComputer);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnRefresh = new JButton("Refresh");
//        btnRefresh.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10,20,20,20)));
        btnPanel.add(btnRefresh);
        btnSetting = new JButton("Setting");
        btnPanel.add(btnSetting);
        leftPanel.add(btnPanel);
        this.add(leftPanel, BorderLayout.LINE_START);

        tabbedPane = new JTabbedPane();
//        tabbedPane.addTab("Download", new JPanel());
        JPanel allFilesPanel = new JPanel();
        DefaultListModel<FileSeed> modelSeed = new DefaultListModel<>();
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hellretyryo.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.rtytreyplanet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hellwertwerterto.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Helloewtr.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Helretyeytrlo.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Helewrterlo.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hellshterhreho.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.plgesrtanet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));
        modelSeed.addElement(new FileSeed("Hello.planet", "world", 1024, "C://", null, 4));

        JList<FileSeed> listFileSeed = new JList<>(modelSeed);
        listFileSeed.setCellRenderer(new FileList());
        allFilesPanel.setLayout(new BorderLayout());
        allFilesPanel.add(new JScrollPane(listFileSeed), BorderLayout.CENTER);
        tabbedPane.addTab("All files",null,allFilesPanel, "All files");
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    void setListener(){
        btnRefresh.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource();
        if(btn == btnRefresh){
            SettingFrame settingFrame = new SettingFrame(this);
//            this.setEnabled(false);
        }
    }
}
