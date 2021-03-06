package GUI.CustomComponent;

import Model.FileSeed;
import util.Constant;

import javax.swing.*;
import java.awt.*;

public class FileList extends JPanel implements ListCellRenderer<FileSeed> {
    private JLabel lbName;
    private JLabel lbFileEx;
    private JLabel lbFileSize;
    private JLabel lbFileStatus;
    private JLabel lbNumCom;
    private JLabel lbMd5;
    private JPanel horiPanel;
    private JPanel firstPanel;
    private JPanel fileInfoPanel;
    private JPanel namePanel;
    private JPanel fileSizePanel;
    private JPanel downPanel;
    public FileList(){
        //create label contain file info
        lbName = new JLabel();
        lbFileSize = new JLabel();
        lbFileEx = new JLabel();
        lbFileStatus = new JLabel();
        lbNumCom = new JLabel();
        lbMd5 = new JLabel();
        //create main wrapper
        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(5, 10, 5,10));

        //create container for info labels
        horiPanel = new JPanel();
        horiPanel.setLayout(new BoxLayout(horiPanel, BoxLayout.X_AXIS));
        horiPanel.setBackground(Color.WHITE);
        Dimension fileInfoSize = new Dimension(100, 50);
        firstPanel = new JPanel();
        firstPanel.setLayout(null);
        Dimension firstCol = new Dimension(250, 100);
        firstPanel.setSize(firstCol);
        firstPanel.setPreferredSize(firstCol);
        firstPanel.setMaximumSize(firstCol);
        fileInfoPanel = new JPanel();
        fileInfoPanel.setLayout(new BoxLayout(fileInfoPanel, BoxLayout.Y_AXIS));
        fileInfoPanel.setSize(fileInfoSize);
        fileInfoPanel.setPreferredSize(fileInfoSize);
        namePanel = new JPanel();
        namePanel.setBackground(Color.WHITE);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(lbName);
        namePanel.add(Box.createHorizontalGlue());
        lbName.setPreferredSize(new Dimension(200, 10));
        lbName.setAlignmentX(Component.LEFT_ALIGNMENT);
        fileInfoPanel.add(namePanel);
        fileInfoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        fileSizePanel = new JPanel();
        fileSizePanel.setBackground(Color.WHITE);
        fileSizePanel.setLayout(new BoxLayout(fileSizePanel, BoxLayout.X_AXIS));
        lbFileEx.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbFileEx.setPreferredSize(new Dimension(70, 10));
        lbFileSize.setAlignmentX(Component.LEFT_ALIGNMENT);
        fileSizePanel.add(lbFileEx);
        fileSizePanel.add(Box.createRigidArea(new Dimension(20,0)));
        fileSizePanel.add(lbFileSize);
        fileSizePanel.add(Box.createHorizontalGlue());
        fileInfoPanel.add(fileSizePanel);
        fileInfoPanel.setBackground(Color.WHITE);
        fileInfoPanel.setMaximumSize(new Dimension(250, 40));
        fileInfoPanel.setPreferredSize(fileInfoPanel.getMaximumSize());
        fileInfoPanel.setMinimumSize(fileInfoPanel.getMaximumSize());
        horiPanel.add(fileInfoPanel);
        horiPanel.add(Box.createRigidArea(new Dimension(20,0)));
        downPanel = new JPanel();
        downPanel.setBackground(Color.WHITE);
        downPanel.setMaximumSize(new Dimension(100, 50));
        downPanel.setMinimumSize(downPanel.getMaximumSize());
        downPanel.setPreferredSize(downPanel.getMaximumSize());
        lbFileStatus.setIcon(new ImageIcon(Constant.SHARED_ICON));
        downPanel.add(lbFileStatus);
        horiPanel.add(downPanel);
        horiPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        lbNumCom.setIcon(new ImageIcon(new ImageIcon("src/res/com.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        horiPanel.add(lbNumCom);
        horiPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        horiPanel.add(Box.createHorizontalGlue());
        horiPanel.add(lbMd5);
        horiPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        this.add(horiPanel);


    }
    @Override
    public Component getListCellRendererComponent(JList<? extends FileSeed> list, FileSeed value, int index, boolean isSelected, boolean cellHasFocus) {
        switch (value.getStatus()){
            case Constant.DOWNLOADING:
            {
                lbFileStatus.setIcon(new ImageIcon(Constant.DOWNLOAD_ICON));
                break;
            }
            case Constant.SHARING:
            {
                lbFileStatus.setIcon(new ImageIcon(Constant.SHARING_ICON));
                break;
            }
            case Constant.SHARED:
            {
                lbFileStatus.setIcon(new ImageIcon(Constant.SHARED_ICON));
                break;
            }
        }
        String nameFile = value.getName();
        lbName.setText(nameFile);
        lbFileEx.setText(value.getExtension().toUpperCase());
        lbFileSize.setText(String.valueOf(value.getMegaSize()) + " MB");
        if(value.getListComputer() == null){
            lbNumCom.setText("0");
        }else{
            lbNumCom.setText(value.getListComputer().size() + "");
        }
        lbMd5.setText(value.getMd5().toUpperCase());
//        if(isSelected){
//            this.setBackground(Color.lightGray);
//            this.horiPanel.setBackground(Color.lightGray);
//            this.firstPanel.setBackground(Color.lightGray);
//            this.fileInfoPanel.setBackground(Color.lightGray);
//            this.namePanel.setBackground(Color.lightGray);
//            this.fileSizePanel.setBackground(Color.lightGray);
//            this.downPanel.setBackground(Color.lightGray);
//        }else{
//            this.setBackground(Color.WHITE);
//            this.horiPanel.setBackground(Color.WHITE);
//            this.firstPanel.setBackground(Color.WHITE);
//            this.fileInfoPanel.setBackground(Color.WHITE);
//            this.namePanel.setBackground(Color.WHITE);
//            this.fileSizePanel.setBackground(Color.WHITE);
//            this.downPanel.setBackground(Color.WHITE);
//        }
        return this;
    }
}
