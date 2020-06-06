package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BookInformationForm {
    private JPanel bookInformationPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel readerInformationSmallPanel;
    private JLabel bookInformationLabel;
    public JButton booksButton;
    public JButton readersBooksButton;
    public JButton booksAtPointButton;
    public JButton booksOrderedButton;
    public JButton pointButton;

    public BookInformationForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getBookInformationPanel() {
        return bookInformationPanel;
    }
}
