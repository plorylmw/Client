package Gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 12/02/2016.
 */
public class SearchPanel extends JPanel {
    SearchPanel()
    {
        JButton button_user;
        JTextField textField;
        JButton button_search;

        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        button_user = new JButton();
        button_user.setPreferredSize(new Dimension(50, 50));
        add(button_user);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 50));
        add(textField);

        button_search = new JButton();
        button_search.setPreferredSize(new Dimension(50, 50));
        add(button_search);

        layout.putConstraint(SpringLayout.WEST, button_user, 30, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, button_user, 40, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, textField, 50, SpringLayout.EAST, button_user);
        layout.putConstraint(SpringLayout.NORTH, textField, 40, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, button_search, 50, SpringLayout.EAST, textField);
        layout.putConstraint(SpringLayout.NORTH, button_search, 40, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.EAST, this, 500, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, this, 100, SpringLayout.NORTH, this);
    }
}
