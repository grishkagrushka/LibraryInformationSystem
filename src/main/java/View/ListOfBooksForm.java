package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ListOfBooksForm {
    private JPanel listOfBooksPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel listOfBooksSmallPanel;
    private JLabel booksListLabel;

    public ListOfBooksForm(String[][] data){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);

        //рисуем таблицу
        JTable booksTable;
        String[] columns = {"id", "Название", "Автор", "Год издания",
                "Дата поступления", "Допустимый срок хранения", "Статус утерянности", "Дата утери", "Стоимость"};
        booksTable = new JTable(data, columns);
        booksTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        booksTable.setFillsViewportHeight(true);
        listOfBooksSmallPanel.add(booksTable);
    }

    public JPanel getListOfBooksPanel() {
        return listOfBooksPanel;
    }
}
