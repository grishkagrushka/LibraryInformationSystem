package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GetBookBackForm {
    private JPanel getBookBackPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel getBookBackSmallPanel;
    private JLabel getBookLabel;
    private JLabel cardNumberLabel;
    public JTextField cardNumberTextField;
    public JButton getBookButton;
    private JLabel pointIdLabel;
    private JLabel bookIdLabel;
    public JTextField pointIdTextField;
    public JTextField bookIdTextField;

    public GetBookBackForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }


    public JPanel getGetBookBackPanel() {
        return getBookBackPanel;
    }
}
