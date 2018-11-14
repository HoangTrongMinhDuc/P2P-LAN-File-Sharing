package GUI;

import Model.FileSeed;
import Model.FileShare;

import javax.swing.*;
import java.awt.*;

public class FileList extends JPanel implements ListCellRenderer<FileSeed> {
    private JLabel lbName;
    private JLabel lbFileEx;
    private JLabel lbFileSize;
    public FileList(){
        lbName = new JLabel();
        lbName.setPreferredSize(new Dimension(200, 20));
        lbName.setAlignmentX(Label.LEFT_ALIGNMENT);
        lbName.setBackground(Color.RED);
        lbName.setForeground(Color.BLACK);
        lbName.setOpaque(true);
        lbFileEx = new JLabel();
        lbFileEx.setForeground(Color.darkGray);
        lbFileSize = new JLabel();
        lbFileSize.setForeground(Color.darkGray);
        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        Box horiBox = Box.createHorizontalBox();
        Box fileInfoBox = Box.createVerticalBox();
        Box fileNameBox = Box.createHorizontalBox();
        fileNameBox.add(Box.createHorizontalGlue());
        fileNameBox.add(lbName);
        fileNameBox.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
        fileInfoBox.add(fileNameBox);
        Box fileSizeBox = Box.createHorizontalBox();
        fileSizeBox.add(lbFileEx);
        fileSizeBox.add(Box.createHorizontalStrut(10));
        fileSizeBox.add(lbFileSize);
        fileInfoBox.add(fileSizeBox);
//        fileInfoBox.setMaximumSize(new Dimension(200, 100));
//        fileInfoBox.setMinimumSize(new Dimension(100, 100));
//        fileInfoBox.setPreferredSize(new Dimension(200,50));
        fileInfoBox.setAlignmentX(Box.LEFT_ALIGNMENT);
        horiBox.add(fileInfoBox);
        horiBox.add(Box.createHorizontalStrut(10));
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/res/icon-info.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JLabel icon = new JLabel("");
        icon.setIcon(imageIcon);
        horiBox.add(icon);
        horiBox.add(Box.createHorizontalStrut(10));
//        ImageIcon imageIconDown = new ImageIcon(new ImageIcon("src/res/down-icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JLabel iconDown = new JLabel("");
        iconDown.setIcon(new ImageIcon("src/res/down-icon.png"));
        horiBox.add(iconDown);
        JProgressBar progressBar = new JProgressBar(0,100);
        progressBar.setSize(20, 200);
        horiBox.add(progressBar);
        horiBox.setBackground(Color.GREEN);
        this.add(horiBox, BorderLayout.CENTER);

//        JPanel infoPanel = new JPanel();
//        infoPanel.setBackground(Color.BLUE);

    }
    @Override
    public Component getListCellRendererComponent(JList<? extends FileSeed> list, FileSeed value, int index, boolean isSelected, boolean cellHasFocus) {
        lbName.setText(value.getName());
        lbFileEx.setText(value.getExtension().toUpperCase());
        lbFileSize.setText(String.valueOf(value.getMegaSize()) + " MB");
        return this;
    }
}
