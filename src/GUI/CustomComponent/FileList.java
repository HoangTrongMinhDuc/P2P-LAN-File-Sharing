package GUI.CustomComponent;

import Model.FileSeed;

import javax.swing.*;
import java.awt.*;

public class FileList extends JPanel implements ListCellRenderer<FileSeed> {
    private JLabel lbName;
    private JLabel lbFileEx;
    private JLabel lbFileSize;
    private JProgressBar progressBar;
    private JLabel lbMoreInfo;
    private JLabel lbFileStatus;
    private JLabel lbNumCom;
    private JLabel lbMd5;
    private JLabel lbOpen;
    public FileList(){
        //create label contain file info
        lbName = new JLabel();
        lbFileSize = new JLabel();
        lbFileEx = new JLabel();
        lbMoreInfo = new JLabel();
        lbFileStatus = new JLabel();
        lbNumCom = new JLabel();
        lbMd5 = new JLabel();
        lbOpen = new JLabel();
        progressBar = new JProgressBar();
        //create main wrapper
        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(5, 10, 5,10));

        //create container for info labels
        JPanel horiPanel = new JPanel();
        horiPanel.setLayout(new BoxLayout(horiPanel, BoxLayout.X_AXIS));
        horiPanel.setBackground(Color.WHITE);
        Dimension fileInfoSize = new Dimension(100, 50);
        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(null);
        Dimension firstCol = new Dimension(250, 100);
        firstPanel.setSize(firstCol);
        firstPanel.setPreferredSize(firstCol);
        firstPanel.setMaximumSize(firstCol);
        JPanel fileInfoPanel = new JPanel();
        fileInfoPanel.setLayout(new BoxLayout(fileInfoPanel, BoxLayout.Y_AXIS));
        fileInfoPanel.setSize(fileInfoSize);
        fileInfoPanel.setPreferredSize(fileInfoSize);
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.WHITE);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(lbName);
        namePanel.add(Box.createHorizontalGlue());
        lbName.setPreferredSize(new Dimension(200, 10));
        lbName.setAlignmentX(Component.LEFT_ALIGNMENT);
        fileInfoPanel.add(namePanel);
        fileInfoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JPanel fileSizePanel = new JPanel();
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
        horiPanel.add(fileInfoPanel);
        lbMoreInfo.setIcon(new ImageIcon(new ImageIcon("src/res/icon-info.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        horiPanel.add(lbMoreInfo);
        horiPanel.add(Box.createRigidArea(new Dimension(20,0)));
        lbFileStatus.setIcon(new ImageIcon("src/res/down-icon.png"));
        horiPanel.add(lbFileStatus);
        horiPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        lbNumCom.setIcon(new ImageIcon(new ImageIcon("src/res/com.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        horiPanel.add(lbNumCom);
        horiPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        horiPanel.add(lbMd5);
        horiPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        lbOpen.setIcon(new ImageIcon("src/res/open.png"));
//        horiPanel.add(lbOpen);
        this.add(horiPanel);


    }
    @Override
    public Component getListCellRendererComponent(JList<? extends FileSeed> list, FileSeed value, int index, boolean isSelected, boolean cellHasFocus) {
        String nameFile = value.getName();
//        if(value.getName().length() >= 32){
//            nameFile = nameFile.substring(0,30) + "...";
//        }
        lbName.setText(nameFile);
        lbFileEx.setText(value.getExtension().toUpperCase());
        lbFileSize.setText(String.valueOf(value.getMegaSize()) + " MB");
        if(value.getListComputer() == null){
            lbNumCom.setText("0");
        }else{
            lbNumCom.setText(value.getListComputer().size() + "");
        }
        lbMd5.setText(value.getMd5());
        return this;
    }
}
