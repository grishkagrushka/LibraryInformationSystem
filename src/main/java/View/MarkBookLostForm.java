package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MarkBookLostForm {
    private JPanel markBookLostPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel markBookLostrSmallPanel;
    private JLabel markLabel;
    private JLabel bookIdLabel;
    public JTextField bookIdTextField;
    public JButton markButton;

    public MarkBookLostForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getMarkBookLostPanel() {
        return markBookLostPanel;
    }
}
