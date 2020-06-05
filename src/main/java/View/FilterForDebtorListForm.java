package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FilterForDebtorListForm {
    private JPanel filterForDebtorListPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel filterForDebtorListSmallPanel;
    private JLabel filterLabel;
    private JLabel pointIdLabel;
    private JTextField pointIdTextField;
    private JLabel chairLabel;
    private JTextField chairTextField;
    private JLabel departmentLabel;
    private JComboBox departmentComboBox;
    private JLabel groupLabel;
    private JTextField groupTextField;
    private JButton showButton;
    private JLabel positionLabel;
    private JComboBox positionComboBox;
    private JLabel timeLabel;
    private JRadioButton timeButton;

    public FilterForDebtorListForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getFilterForDebtorListPanel() {
        return filterForDebtorListPanel;
    }
}
