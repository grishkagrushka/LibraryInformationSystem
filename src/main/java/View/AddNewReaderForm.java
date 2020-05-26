package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AddNewReaderForm {
    private JPanel addNewReaderPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    private JButton buttonReaderAction;
    private JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel addNewReaderSmallPanel;
    private JLabel addNewReaderLabel;
    private JLabel surnameLabel;
    public JTextField surnameTextField;
    private JLabel nameLabel;
    public JTextField nameTextField;
    private JLabel fathernameLabel;
    public JTextField fathernameTextField;
    public JButton addButton;
    private JLabel seasonTicketLabel;
    public JComboBox seasonTicketComboBox;
    private JLabel positionLabel;
    public JComboBox positionComboBox;
    private JLabel departmentLabel;
    public JComboBox departmentComboBox;
    private JLabel chairLabel;
    public JComboBox chairComboBox;
    private JLabel groupLabel;
    public JComboBox groupComboBox;
    private JLabel warningLabel;
    private JButton buttonGiveBookToReader;
    private JPanel northPanel;

    public AddNewReaderForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getAddNewReaderPanel() {
        return addNewReaderPanel;
    }


}
