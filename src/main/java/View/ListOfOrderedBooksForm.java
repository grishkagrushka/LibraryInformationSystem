package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ListOfOrderedBooksForm {
    private JPanel listOfOrderedBooksForm;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel listOfOrderedBooksSmallPanel;
    private JLabel orderedBooksListLabel;

    public ListOfOrderedBooksForm(String[][] data){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);

        //рисуем таблицу
        JTable orderedBooksTable;
        String[] columns = {"Номер читательского билета", "Фамилия", "Имя", "Отчество",
                            "id книги", "Название", "Дата заказа", "Статус заказа"};
        orderedBooksTable = new JTable(data, columns);
        orderedBooksTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        orderedBooksTable.setFillsViewportHeight(true);
        listOfOrderedBooksSmallPanel.add(orderedBooksTable);
    }

    public JPanel getListOfOrderedBooksForm() {
        return listOfOrderedBooksForm;
    }
}
