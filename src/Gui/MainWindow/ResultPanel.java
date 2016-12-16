package Gui.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 12/02/2016.
 */
public class ResultPanel extends JPanel {
    public ChoosePanel choosePanel;
    public ExplainPanel explainPanel;

    ResultPanel()
    {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setOpaque(false);

        choosePanel = new ChoosePanel();
        add(choosePanel);
        explainPanel = new ExplainPanel();
        add(explainPanel);

        layout.putConstraint(SpringLayout.NORTH, choosePanel, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, choosePanel, 0, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, explainPanel, 50, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, explainPanel, 100, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.SOUTH, this, 500, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, this, 1000, SpringLayout.WEST, this);
    }
}
