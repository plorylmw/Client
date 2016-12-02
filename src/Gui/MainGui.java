package Gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 12/02/2016.
 */
public class MainGui extends JFrame {
    private Container contentPane;
    private JLabel label;
    private JTextField textField;

    MainGui()
    {
        setLocation(200, 100);
        setPreferredSize(new Dimension(1000, 750));
        setResizable(false);
        setTitle("My dictionary");

        contentPane = getContentPane();

        setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        add(new SearchPanel());




/*
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        label = new JLabel("Label: ");
        add(label);
        textField = new JTextField("Text field", 15);
        add(textField);
        contentPane = getContentPane();
        //Adjust constraints for the label so it's at (5,5).
        layout.putConstraint(SpringLayout.WEST, label,
                5,
                SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, label,
                5,
                SpringLayout.NORTH, contentPane);

        //Adjust constraints for the text field so it's at
//(<label's right edge> + 5, 5).
        layout.putConstraint(SpringLayout.WEST, textField,
                5,
                SpringLayout.EAST, label);
        layout.putConstraint(SpringLayout.NORTH, textField,
                5,
                SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.EAST, contentPane,
                5,
                SpringLayout.EAST, textField);
        layout.putConstraint(SpringLayout.SOUTH, contentPane,
                5,
                SpringLayout.SOUTH, textField);*/

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainGui();
    }

}
