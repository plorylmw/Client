package Gui.UserWindow;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 12/06/2016.
 */
public class LoginPanel extends JPanel {
    JPanel namePanel;
    public JTextField username;
    JPanel passwordPanel;
    public JTextField password;

    public LoginPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        namePanel = new JPanel();
        namePanel.add(new JLabel("username:"));
        username = new JTextField();
        username.setPreferredSize(new Dimension(50, 20));
        namePanel.add(username);

        passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("password:"));
        password = new JTextField();
        password.setPreferredSize(new Dimension(50, 20));
        passwordPanel.add(password);

        add(namePanel);
        add(passwordPanel);
    }
}
