package GUI;

import Model.Computer;

import javax.swing.*;
import java.awt.*;

public class ComputerList extends JPanel implements ListCellRenderer<Computer> {
    private JLabel lbStatus;
    private JLabel lbComputerName;
    private JLabel lbComputerAddress;
    public ComputerList(){
        setOpaque(true);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
//        this.setBackground(Color.WHITE);
        lbStatus = new JLabel();
        this.add(lbStatus);
        System.out.println("C:\\Users\\HTML5\\IdeaProjects\\DoAnMang\\src\\res\\active.png");
        lbStatus.setIcon(new ImageIcon("C:\\Users\\HTML5\\IdeaProjects\\DoAnMang\\src\\res\\active.png"));
        this.add(Box.createHorizontalStrut(10));
        JPanel comPanel = new JPanel();
        comPanel.setLayout(new BoxLayout(comPanel, BoxLayout.Y_AXIS));
        lbComputerName = new JLabel();
        comPanel.add(lbComputerName);
        lbComputerAddress = new JLabel();
        comPanel.add(lbComputerAddress);
        this.add(comPanel);

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Computer> list, Computer value, int index, boolean isSelected, boolean cellHasFocus) {
        this.lbComputerName.setText(value.getName());
        this.lbComputerAddress.setText(value.getIp() + ":" + value.getPort());
        return this;
    }
}
