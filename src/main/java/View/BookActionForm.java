package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BookActionForm {
    private JPanel bookActionPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel readerActionSmallPanel;
    private JLabel readerActionLabel;
    public JButton signBookAtPoint;
    public JButton giveOrderButton;
    public JButton editProfileButton;
    public JButton markBookLostButton;
    public JButton getBookButton;
    public JButton buttonInformationButtom;

    public BookActionForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getBookActionPanel() {
        return bookActionPanel;
    }
}
