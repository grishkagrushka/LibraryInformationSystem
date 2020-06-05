package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class EditReadersProfileForm {
    private JPanel editReadersProfilePanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel addNewReaderSmallPanel;
    private JLabel editLabel;
    private JLabel surnameLabel;
    public JTextField surnameTextField;
    private JLabel nameLabel;
    public JTextField nameTextField;
    private JLabel fathernameLabel;
    public JTextField fathernameTextField;
    private JLabel positionLabel;
    public JComboBox positionComboBox;
    private JLabel departmentLabel;
    public JComboBox departmentComboBox;
    private JLabel chairLabel;
    public JTextField chairTextField;
    private JLabel groupLabel;
    public JTextField groupTextField;
    private JLabel warningLabel;
    public JButton editButton;
    private JPanel northPanel;
    private JLabel readerCardNumberLabel;
    public JTextField readerCardNumberTextField;

    public EditReadersProfileForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getEditReadersProfilePanel() {
        return editReadersProfilePanel;
    }
}
