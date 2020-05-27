package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainForm {
    private JPanel rootPanel;
    private JPanel leftPanel;
    private JPanel centerPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    public JButton buttonGiveBookToReader;
    private JPanel northPanel;
    private JPanel smallRootPanel;
    private JLabel welcomeLabel;

    public MainForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);

    }


    public JPanel getRootPanel() {
        return rootPanel;
    }
}
