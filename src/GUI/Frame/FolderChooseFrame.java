package GUI.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FolderChooseFrame extends JDialog implements ActionListener {
    private JFrame parentFrame;
    private final int WIDTH_FRAME = 680;
    private final int HEIGHT_FRAME = 480;
    private JFileChooser fileChooser;

    public FolderChooseFrame(JFrame parentFrame){
        super(parentFrame, "Choose Folder", false);
        this.parentFrame = parentFrame;
        initComponent();
        initFrame();
//        fileChooser.show


    }

    private void initComponent(){
        this.fileChooser = new JFileChooser();
        this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.setLayout(new BorderLayout());
        this.add(fileChooser, BorderLayout.CENTER);

    }

    private void initFrame(){
        this.setSize(WIDTH_FRAME, HEIGHT_FRAME);
        this.setTitle("Choose folder");
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
