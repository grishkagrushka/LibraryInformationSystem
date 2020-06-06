package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ListOfReadersForm {
    private JPanel listOfReadersPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel listOfReadersSmallPanel;
    private JLabel readersListLabel;

    public ListOfReadersForm(String[][] data){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);

        //рисуем таблицу
        JTable readersTable;
        String[] columns = {"Фамилия", "Имя", "Отчество", "№ чит.билета", "Должность", "Пункт выдачи", "Кафедра", "Факультет", "Группа"};
        readersTable = new JTable(data, columns);
        readersTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        readersTable.setFillsViewportHeight(true);
        listOfReadersSmallPanel.add(readersTable);
    }

    public JPanel getListOfReadersPanel() {
        return listOfReadersPanel;
    }

}
