package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GiveOrderedBooksToReaderForm {
    private JPanel giveOrderedBooksToReaderPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel giveOrderedBookToReaderSmallPanel;
    private JLabel giveOrderedBookLabel;
    private JLabel pointIdLabel;
    public JTextField pointIdTextField;
    private JLabel cardNumberLabel;
    public JTextField cardNumberTextField;
    public JButton giveButton;
    private JLabel bookIdLabel;
    public JTextField bookIdTextField;

    public GiveOrderedBooksToReaderForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getGiveOrderedBooksToReaderPanel() {
        return giveOrderedBooksToReaderPanel;
    }
}
