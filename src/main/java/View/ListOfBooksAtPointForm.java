package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ListOfBooksAtPointForm {
    private JPanel listOfBooksAtPointPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel listOfBooksAtPointSmallPanel;
    private JLabel booksAtPointListLabel;

    public ListOfBooksAtPointForm(String[][] data){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);

        //рисуем таблицу
        JTable booksAtPointTable;
        String[] columns = {"id пункта выдачи", "id книги", "Название"};
        booksAtPointTable = new JTable(data, columns);
        booksAtPointTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        booksAtPointTable.setFillsViewportHeight(true);
        listOfBooksAtPointSmallPanel.add(booksAtPointTable);
    }

    public JPanel getListOfBooksAtPointPanel() {
        return listOfBooksAtPointPanel;
    }
}
