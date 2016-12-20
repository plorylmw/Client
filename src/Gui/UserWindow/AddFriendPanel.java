package Gui.UserWindow;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 12/14/2016.
 */
public class AddFriendPanel extends JPanel {
    JPanel namePanel;
    public JTextField username;

    public AddFriendPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        namePanel = new JPanel();
        namePanel.add(new JLabel("friend's name:"));
        username = new JTextField();
        username.setPreferredSize(new Dimension(50, 20));
        namePanel.add(username);

        add(namePanel);
    }
}
