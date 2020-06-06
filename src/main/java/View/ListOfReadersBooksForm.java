package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ListOfReadersBooksForm {
    private JPanel listOfReadersBooksPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel listOfReadersBooksSmallPanel;

    public ListOfReadersBooksForm(String[][] data){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);

        //рисуем таблицу
        JTable readersBooksTable;
        String[] columns = {"id читателя", "Фамилия", "Имя", "Отчество", "id книги", "Получение", "Возврат"};
        readersBooksTable = new JTable(data, columns);
        readersBooksTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        readersBooksTable.setFillsViewportHeight(true);
        listOfReadersBooksSmallPanel.add(readersBooksTable);
    }

    public JPanel getListOfReadersBooksPanel() {
        return listOfReadersBooksPanel;
    }
}
