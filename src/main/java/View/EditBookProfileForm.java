package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class EditBookProfileForm {
    private JPanel editBookProfilePanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel editBookSmallPanel;
    private JLabel editBookLabel;
    private JLabel bookNameLabel;
    public JTextField bookNameTextField;
    private JLabel authorNameLabel;
    public JTextField authorNameTextField;
    private JLabel yearOfPublishingLabel;
    public JTextField yearOfPublishingTextField;
    private JLabel bookArrivalDateLabel;
    public JTextField bookArrivalDateTextField;
    private JLabel bookAllowPeriodLabel;
    public JTextField bookAllowPeriodTextField;
    private JLabel bookCostLabel;
    public JTextField bookCostTextField;
    private JLabel warningLabel;
    public JButton editButton;
    private JPanel northPanel;
    private JLabel bookIdLabel;
    public JTextField bookIdTextField;

    public EditBookProfileForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getEditBookProfilePanel() {
        return editBookProfilePanel;
    }
}
