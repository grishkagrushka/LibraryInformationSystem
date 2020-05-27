package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ReaderActionForm {
    private JPanel readerActionPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    private JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel readerActionSmallPanel;
    private JLabel readerActionLabel;
    private JButton signReaderAtPointButton;
    private JButton giveOrderButton;
    private JButton editProfileButton;
    private JButton applySanctionsButton;
    private JButton removeReader;
    private JButton readerInformationButtom;

    public ReaderActionForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getReaderActionPanel() {
        return readerActionPanel;
    }
}
