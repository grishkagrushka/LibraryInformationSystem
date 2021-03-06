package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AddNewBookForm {
    private JPanel addNewBookPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel addNewBookSmallPanel;
    private JLabel addNewBookLabel;
    private JLabel bookNameLabel;
    public JTextField bookNameTextField;
    private JLabel authorNameLabel;
    public JTextField authorNameTextField;
    private JLabel yearOfPublishingLabel;
    public JTextField yearOfPublishingTextField;
    private JLabel bookArrivalDateLabel;
    private JLabel bookAllowPeriodLabel;
    private JLabel bookCostLabel;
    private JLabel warningLabel;
    public JButton addButton;
    public JTextField bookArrivalDateTextField;
    public JTextField bookAllowPeriodTextField;
    public JTextField bookCostTextField;
    private JPanel northPanel;

    public AddNewBookForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getAddNewBookPanel() {
        return addNewBookPanel;
    }
}
