package GUI.CustomComponent;

import Controller.DownloadController;
import GUI.Tab.DownloadingTab;
import Model.FileSeed;
import util.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DownloadingList  extends JPanel implements ListCellRenderer<DownloadController> {
    private JLabel lbName;
    private JLabel lbPath;
    private JLabel lbSize;
    private JLabel lbMd5;
    private JLabel lbDel;
    public DownloadingList(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Component s = Box.createRigidArea(new Dimension(0, 10));
        this.add(s);
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
        wrapper.add(Box.createRigidArea(new Dimension(10, 0)));

        Dimension dimension = new Dimension(150, 50);
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.WHITE);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setMaximumSize(dimension);
        namePanel.setMinimumSize(dimension);
        lbName = new JLabel();
        lbName.setMaximumSize(new Dimension(200, 50));
        lbName.setMinimumSize(lbName.getMaximumSize());
        namePanel.add(lbName);
        lbPath = new JLabel();
        lbPath.setMaximumSize(new Dimension(350, 50));
        lbPath.setMinimumSize(lbPath.getMaximumSize());
        namePanel.add(lbPath);

        wrapper.add(namePanel);

        wrapper.add(Box.createHorizontalGlue());
        lbSize = new JLabel();
        wrapper.add(lbSize);
        wrapper.add(Box.createRigidArea(new Dimension(10, 0)));
        lbMd5 = new JLabel();
        lbMd5.setMaximumSize(new Dimension(200, 50));
        lbMd5.setPreferredSize(lbMd5.getMaximumSize());
        lbMd5.setMinimumSize(lbMd5.getMaximumSize());
        wrapper.add(lbMd5);
        wrapper.add(Box.createRigidArea(new Dimension(10, 0)));
        lbDel = new JLabel();
        lbDel.setIcon(new ImageIcon(new ImageIcon(Constant.REMOVE_ICON).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        wrapper.add(lbDel);
        wrapper.add(Box.createRigidArea(new Dimension(10, 0)));
        wrapper.setBackground(Color.WHITE);
        this.add(wrapper);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel line = new JPanel();
        line.setPreferredSize(new Dimension(600, 1));
        line.setMinimumSize(line.getPreferredSize());
        line.setMaximumSize(line.getPreferredSize());
        this.add(line);
        this.setBackground(Color.WHITE);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends DownloadController> list, DownloadController value, int index, boolean isSelected, boolean cellHasFocus) {

        return this;
    }
}
