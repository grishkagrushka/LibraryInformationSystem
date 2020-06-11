package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MyBookFormForReaders {
    private JPanel myBookPanel;
    private JPanel leftPanel;
    public JButton myBookButton;
    public JButton orderBookButton;
    public JButton exitButton;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel myBookSmallPanel;
    private JLabel myBookLabel;

    public MyBookFormForReaders(String[][] data){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);

        //рисуем таблицу
        JTable myBookTable;
        String[] columns = {"Пункт выдачи", "Название", "Автор", "Дата получения", "Срок хранения", "Дата реального возвращения книги"};
        myBookTable = new JTable(data, columns);
        myBookTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        myBookTable.setFillsViewportHeight(true);
        myBookSmallPanel.add(myBookTable);
    }

    public JPanel getMyBookPanel() {
        return myBookPanel;
    }
}
