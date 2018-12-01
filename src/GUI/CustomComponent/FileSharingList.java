package GUI.CustomComponent;

import Model.FileSeed;
import Model.FileShare;

import javax.swing.*;
import java.awt.*;

public class FileSharingList  extends JPanel implements ListCellRenderer<FileShare>  {
    private JLabel lbName;
    public FileSharingList(){
        lbName = new JLabel();
        this.add(lbName);
        this.setBackground(Color.WHITE);
    }
    @Override
    public Component getListCellRendererComponent(JList<? extends FileShare> list, FileShare value, int index, boolean isSelected, boolean cellHasFocus) {
        lbName.setText("hello");
        return this;
    }
}
