package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ReaderInformationForm {
    private JPanel readerInformationPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel readerInformationSmallPanel;
    private JLabel readerInformationLabel;
    public JButton readersList;
    private JButton giveOrderButton;
    private JButton editProfileButton;
    private JButton applySanctionsButton;
    private JButton removeReaderButton;
    private JButton getBookButton;
    private JButton readerInformationButtom;

    public ReaderInformationForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getReaderInformationPanel() {
        return readerInformationPanel;
    }
}
