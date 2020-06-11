package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainFormForReaders {
    private JPanel mainFormForReadersPanel;
    private JPanel leftPanel;
    public JButton myBookButton;
    public JButton orderBookButton;
    public JButton exitButton;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel smallRootPanel;
    private JLabel welcomeLabel;

    public MainFormForReaders(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getMainFormForReadersPanel() {
        return mainFormForReadersPanel;
    }
}
