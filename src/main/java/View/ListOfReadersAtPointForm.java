package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ListOfReadersAtPointForm {
    private JPanel readersAtPointListPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel listReadersAtPointSmallPanel;
    private JLabel readersListLabel;

    public ListOfReadersAtPointForm(String[][] data){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);

        //рисуем таблицу
        JTable readersAtPointTable;
        String[] columns = {"id пункта выдачи", "№ чит.билета", "Фамилия", "Имя", "Отчество", "Дата отметки"};
        readersAtPointTable = new JTable(data, columns);
        readersAtPointTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        readersAtPointTable.setFillsViewportHeight(true);
        listReadersAtPointSmallPanel.add(readersAtPointTable);
    }

    public JPanel getReadersAtPointListPanel() {
        return readersAtPointListPanel;
    }
}
