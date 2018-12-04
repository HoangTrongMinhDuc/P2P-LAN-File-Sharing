package GUI.CustomComponent;

import Model.Computer;
import util.Constant;

import javax.swing.*;
import java.awt.*;

public class ComputerList extends JPanel implements ListCellRenderer<Computer> {
    private JLabel lbStatus;
    private JLabel lbComputerName;
    private JLabel lbComputerAddress;
    JPanel comPanel;
    public ComputerList(){
        setOpaque(true);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
//        this.setBackground(Color.WHITE);
        lbStatus = new JLabel();
        this.add(lbStatus);
        lbStatus.setIcon(new ImageIcon("src/res/active.png"));
        this.add(Box.createHorizontalStrut(10));
        comPanel = new JPanel();
        comPanel.setBackground(Color.WHITE);
        comPanel.setLayout(new BoxLayout(comPanel, BoxLayout.Y_AXIS));
        lbComputerName = new JLabel();
        comPanel.add(lbComputerName);
        lbComputerAddress = new JLabel();
        comPanel.add(lbComputerAddress);
        this.add(comPanel);
        this.setBackground(Color.WHITE);

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Computer> list, Computer value, int index, boolean isSelected, boolean cellHasFocus) {
        String src = "";
        switch (value.getStatus()){
            case Constant.ONLINE:{
                src = Constant.ONLINE_ICON;
                break;
            }
            case Constant.PENDING:{
                src = Constant.PENDING_ICON;
                break;
            }
            case Constant.OFFLINE:{
                src = Constant.OFFLINE_ICON;
                break;
            }
            case Constant.CHECKING:{
                src = Constant.CHECKING_ICON;
                break;
            }
        }
        lbStatus.setIcon(new ImageIcon(src));
        this.lbComputerName.setText(value.getName());
        this.lbComputerAddress.setText(value.getIp() + ":" + value.getPort());
        if(isSelected){
            this.setBackground(new Color(255, 231, 217));
            this.comPanel.setBackground(new Color(255, 231, 217));
        }else {
            this.setBackground(Color.WHITE);
        }
        return this;
    }
}
