package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ListOfPointsForm {
    private JPanel listPointsPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel listOfPointsSmallPanel;
    private JLabel pointListLabel;


    public ListOfPointsForm(String[][] data){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);

        //рисуем таблицу
        JTable pointsTable;
        String[] columns = {"Номер пункта выдачи", "Статус"};
        pointsTable = new JTable(data, columns);
        pointsTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        pointsTable.setFillsViewportHeight(true);
        listOfPointsSmallPanel.add(pointsTable);

    }

    public JPanel getListPointsPanel() {
        return listPointsPanel;
    }
}
