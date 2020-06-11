package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class OrderBooksFormForReaders {
    private JPanel orderBooksForReadersPanel;
    private JPanel leftPanel;
    public JButton myBookButton;
    public JButton orderBookButton;
    public JButton exitButton;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel orderBooksLabel;
    private JLabel giveOrderedBookLabel;
    private JLabel pointIdLabel;
    public JTextField pointIdTextField;
    private JLabel bookNameLabel;
    public JTextField bookNameTextField;
    private JLabel bookAuthorLabel;
    public JTextField bookAuthorTextField;
    public JButton orderButton;

    public OrderBooksFormForReaders(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getOrderBooksForReadersPanel() {
        return orderBooksForReadersPanel;
    }
}
