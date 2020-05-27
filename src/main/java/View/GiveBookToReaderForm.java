package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GiveBookToReaderForm {
    private JPanel giveBookToReaderPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel giveBookToReaderSmallPanel;
    private JLabel giveBookToReaderLabel;
    private JLabel cardNumberLabel;
    private JTextField cardNumberTextField;
    private JLabel bookNameLabel;
    private JTextField bookNameTextField;
    private JButton giveButton;
    private JLabel bookAuthorLabel;
    private JTextField bookAuthorTextField;

    public GiveBookToReaderForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getGiveBookToReaderPanel() {
        return giveBookToReaderPanel;
    }
}
