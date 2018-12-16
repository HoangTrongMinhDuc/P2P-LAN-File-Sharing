package GUI.CustomComponent;

import Controller.DownloadController;
import GUI.Tab.DownloadingTab;
import Model.FileDownload;
import Model.FileSeed;
import util.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DownloadingList  extends JPanel implements ListCellRenderer<DownloadController> {
    private JLabel lbName;
    private JProgressBar progressBar;
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

        Dimension dimension = new Dimension(350, 50);
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.WHITE);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setMaximumSize(dimension);
        namePanel.setMinimumSize(dimension);
        namePanel.setPreferredSize(dimension);
        lbName = new JLabel();
        lbName.setMaximumSize(new Dimension(200, 25));
        lbName.setMinimumSize(lbName.getMaximumSize());
        lbName.setPreferredSize(lbName.getMaximumSize());
        lbName.setAlignmentX(JLabel.LEFT);
        namePanel.add(lbName);
        progressBar = new JProgressBar();
        progressBar.setMaximumSize(new Dimension(350, 10));
        progressBar.setMinimumSize(progressBar.getMaximumSize());
        progressBar.setPreferredSize(progressBar.getMaximumSize());
        namePanel.add(progressBar);

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
        lbDel.setIcon(new ImageIcon(new ImageIcon(Constant.PAUSE_ICON).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        lbDel.setMinimumSize(new Dimension(25, 50));
        lbDel.setMaximumSize(lbDel.getMinimumSize());
        lbDel.setPreferredSize(lbDel.getMaximumSize());
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
        FileDownload fileDownload = value.getFileDownload();
        this.lbName.setText(fileDownload.getName());
        long percent = (fileDownload.getDownloadedSize()*100/fileDownload.getSize());
        this.lbSize.setText((percent) + "% / " + fileDownload.getMegaSize() + " MB / " + fileDownload.getSpeed() + " MB/s");
//        this.lbMd5.setText(fileDownload.getMd5());
        this.progressBar.setValue((int)percent);
        String src = Constant.PAUSE_ICON;
        switch (fileDownload.getStatus()){
            case Constant.DOWNLOADING:
            {
                src = Constant.PAUSE_ICON;
                break;
            }
            case Constant.DOWNLOADED:
            {
                src = Constant.DONE_ICON;
                break;
            }
            case Constant.PAUSED:
            {
                src = Constant.PLAY_ICON;
                break;
            }
        }
        lbDel.setIcon(new ImageIcon(new ImageIcon(src).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        return this;
    }
}
