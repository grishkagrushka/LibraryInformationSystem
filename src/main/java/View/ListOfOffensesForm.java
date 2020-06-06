package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ListOfOffensesForm {
    private JPanel listOfOffensesPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel listOfOffensesSmallPanel;
    private JLabel offensesListLabel;

    public ListOfOffensesForm(String[][] data){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);

        //рисуем таблицу
        JTable offensesTable;
        String[] columns = {"id читателя", "Фамилия", "Имя", "Отчество",
                "id книги", "Срок дисквалификации", "Дата совершения", "Штраф"};
        offensesTable = new JTable(data, columns);
        offensesTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        offensesTable.setFillsViewportHeight(true);
        listOfOffensesSmallPanel.add(offensesTable);
    }

    public JPanel getListOfOffensesPanel() {
        return listOfOffensesPanel;
    }
}
