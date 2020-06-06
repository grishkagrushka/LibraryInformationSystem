package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SignBookAtPointForm {
    private JPanel signBookAtPointPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel signBookAtPointSmallPanel;
    private JLabel signLabel;
    private JLabel pointIdLabel;
    public JTextField pointIdTextField;
    private JLabel cardNumberLabel;
    public JTextField bookIdTextField;
    public JButton signButton;

    public SignBookAtPointForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getSignBookAtPointPanel() {
        return signBookAtPointPanel;
    }
}
