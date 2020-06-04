package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FilterForReadersList {
    private JPanel filterForReadersListPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel filterForReadersListSmallPanel;
    private JLabel filterLabel;
    private JLabel pointIdLabel;
    public JTextField pointIdTextField;
    private JLabel chairLabel;
    public JTextField chairTextField;
    private JLabel departmentLabel;
    public JComboBox departmentComboBox;
    private JLabel groupLabel;
    public JTextField groupTextField;
    public JButton showButton;

    public FilterForReadersList(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }


    public JPanel getFilterForReadersListPanel() {
        return filterForReadersListPanel;
    }
}
