package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AuthorizationForm {
    private JPanel authorizationPanel;
    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel authorizationSmallPanel;
    private JLabel authorizationLabel;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    public JTextField passwordTextField;
    public JButton entryButton;
    public JTextField loginTextField;

    public AuthorizationForm(){
        //устанавливаем границу для левой панели
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        leftPanel.setBorder(border);
    }

    public JPanel getAuthorizationPanel() {
        return authorizationPanel;
    }
}
