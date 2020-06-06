package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RemoveReadersForm {
    private JPanel removeReadersPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel removeReaderSmallPanel;
    private JLabel removeLabel;
    private JLabel cardNumberLabel;
    public JTextField cardNumberTextField;
    public JButton removeButton;

    public RemoveReadersForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getRemoveReadersPanel() {
        return removeReadersPanel;
    }
}
