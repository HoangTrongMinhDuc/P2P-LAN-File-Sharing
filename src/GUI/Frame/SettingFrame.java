package GUI.Frame;

import Controller.MyComputer;
import Controller.PackageController;
import util.LocalSetting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingFrame extends JDialog implements ActionListener {
    private final int WIDTH_FRAME = 480;
    private final int HEIGHT_FRAME = 180;
    private JFrame parentFrame;
    private JTextField inTextPath;
    private JFileChooser fileChooser;
    private JButton btnFolderChoose;
    private JTextField inTextPort;
    private JCheckBox checkSpread;
    private JLabel lbEnableSpread;
    private JButton btnApply;
    private JButton btnCancel;
    private JButton btnDefault;
    public SettingFrame(JFrame parentFrame){
        super(parentFrame, "Setting", false);
        this.parentFrame = parentFrame;
        initComponent();
        importSetting();
        initLayout();
        addListener();
        initFrame();
    }

    private void initComponent(){
        this.inTextPath = new JTextField(32);
        this.inTextPath.setEditable(false);
        this.inTextPath.setMaximumSize(this.inTextPath.getPreferredSize());
        this.fileChooser = new JFileChooser(LocalSetting.getInstance().getDownFolderPath());
        this.btnFolderChoose = new JButton("Browse");
        this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.inTextPort = new JTextField(16);
        this.inTextPort.setMaximumSize(this.inTextPort.getPreferredSize());
        this.checkSpread = new JCheckBox();
        this.lbEnableSpread = new JLabel("Enable spread file");
        this.btnApply = new JButton("Apply");
        this.btnCancel = new JButton("Cancel");
        this.btnDefault = new JButton("Default");
    }

    private void initFrame(){
        this.setSize(WIDTH_FRAME, HEIGHT_FRAME);
        this.setTitle("Setting");
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    void initLayout(){
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));


        Box lineBox0 = Box.createHorizontalBox();
        lineBox0.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        JLabel lbLocalIp = new JLabel("Your local IP");
        lbLocalIp.setMaximumSize(lbLocalIp.getPreferredSize());
        lineBox0.add(lbLocalIp);

        lineBox0.add(Box.createRigidArea(new Dimension(40, 0)));
        lineBox0.add(new JLabel(MyComputer.getInstance().getIp()+"/"+MyComputer.getInstance().getSubnetMask()));
        lineBox0.add(Box.createHorizontalGlue());
        this.add(lineBox0);

        Box lineBox1 = Box.createHorizontalBox();
        lineBox1.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        JLabel lbFolder = new JLabel("Local folder");
        lbFolder.setMaximumSize(lbFolder.getPreferredSize());
        lineBox1.add(lbFolder);
        lineBox1.add(Box.createHorizontalGlue());

        lineBox1.add(this.inTextPath);
        lineBox1.add(Box.createRigidArea(new Dimension(5, 0)));
        lineBox1.add(this.btnFolderChoose);
        this.add(lineBox1);

        Box lineBox2 = Box.createHorizontalBox();
        lineBox2.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        JLabel lbPort = new JLabel("Your port");
        lbPort.setMaximumSize(lbPort.getPreferredSize());
        lineBox2.add(lbPort);
        lineBox2.add(Box.createRigidArea(new Dimension(45, 0)));

        lineBox2.add(inTextPort);
        lineBox2.add(Box.createHorizontalGlue());
        Box lineBox3 = Box.createHorizontalBox();
        lineBox3.add(checkSpread);
        lineBox3.add(Box.createRigidArea(new Dimension(5, 0)));
        lineBox3.add(lbEnableSpread);
        lineBox2.add(lineBox3);
        this.add(lineBox2);

        Box lineButtonBox = Box.createHorizontalBox();
        lineButtonBox.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        lineButtonBox.add(this.btnDefault);
        lineButtonBox.add(Box.createHorizontalGlue());
        lineButtonBox.add(this.btnApply);
        lineButtonBox.add(Box.createRigidArea(new Dimension(20, 0)));
        lineButtonBox.add(this.btnCancel);
        this.add(lineButtonBox);

    }

    private void addListener(){
        this.btnDefault.addActionListener(this);
        this.btnApply.addActionListener(this);
        this.btnCancel.addActionListener(this);
        this.btnFolderChoose.addActionListener(this);
        this.lbEnableSpread.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                SwitchEnable();
            }
        });
    }

    private void importSetting(){
        this.inTextPath.setText(LocalSetting.getInstance().getDownFolderPath());
        this.inTextPort.setText(LocalSetting.getInstance().getPort()+"");
        this.checkSpread.setSelected(LocalSetting.getInstance().isEnaleSpread());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if(button == this.btnApply){
            LocalSetting.getInstance().setDownFolderPath(this.inTextPath.getText());
            if(Integer.parseInt(this.inTextPort.getText())!=LocalSetting.getInstance().getPort()){
                System.out.println("restart");
                PackageController.getInstance().reStartSocket(Integer.parseInt(this.inTextPort.getText()));
            }
            LocalSetting.getInstance().setPort(Integer.parseInt(this.inTextPort.getText()), true);

            LocalSetting.getInstance().setEnaleSpread(this.checkSpread.isSelected());
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }

        if(button == this.btnDefault){
            LocalSetting.getInstance().resetSetting();
            this.importSetting();
        }

        if(button == this.btnCancel){
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }

        if(button == this.btnFolderChoose){
//            FolderChooseFrame folderChooseFrame = new FolderChooseFrame(this.parentFrame);
            int p = fileChooser.showDialog(this, "Choose folder");

            if(p == JFileChooser.APPROVE_OPTION){
                this.inTextPath.setText(fileChooser.getSelectedFile().toString());
            }
//            fileChooser.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//
//                }
//            });
        }
    }

    private void SwitchEnable(){
        this.checkSpread.setSelected(!this.checkSpread.isSelected());
    }
}
