package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ApplySanctionsForm {
    private JPanel applySanctionsPanel;
    private JPanel leftPanel;
    public JButton buttonAddNewReader;
    public JButton buttonAddNewBook;
    public JButton buttonGiveBookToReader;
    public JButton buttonReaderAction;
    public JButton buttonBookAction;
    private JPanel centerPanel;
    private JPanel addNewReaderSmallPanel;
    private JLabel sunctionsLabel;
    private JLabel readerCardNumberLabel;
    public JTextField readerCardNumberTextField;
    private JLabel bookIdLabel;
    public JTextField bookIdTextField;
    private JLabel disqLabel;
    public JTextField periodOfDisqTextField;
    private JLabel penaltyLabel;
    public JTextField penaltyTextField;
    public JButton applyButton;
    private JPanel northPanel;

    public ApplySanctionsForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getApplySanctionsPanel() {
        return applySanctionsPanel;
    }
}
