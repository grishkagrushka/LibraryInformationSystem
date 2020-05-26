package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AddNewBookForm {
    private JPanel addNewBookPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    private JButton buttonGiveBookToReader;
    private JButton buttonReaderAction;
    private JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel addNewBookSmallPanel;
    private JLabel addNewBookLabel;
    private JLabel bookNameLabel;
    private JTextField bookNameTextField;
    private JLabel authorNameLabel;
    private JTextField authorNameTextField;
    private JLabel yearOfPublishingLabel;
    private JTextField yearOfPublishingTextField;
    private JLabel bookArrivalDateLabel;
    private JLabel bookAllowPeriodLabel;
    private JLabel bookCostLabel;
    private JLabel warningLabel;
    private JButton addButton;
    private JTextField bookArrivalDateTextField;
    private JTextField bookAllowPeriodTextField;
    private JTextField bookCostTextField;
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
