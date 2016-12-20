package Gui.MainWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Administrator on 12/04/2016.
 */
public class DemoExPanel extends JPanel {
    ExplainPanel fatherPanel;
    public JTextArea Ex;
    public JCheckBox isLove;
    boolean isSetBox = false;

    public void setCheckBox(boolean state) {
        isSetBox = true;
        isLove.setSelected(state);
        isSetBox = false;
    }

    DemoExPanel(ExplainPanel f)
    {
        fatherPanel = f;

        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setBackground(new Color(106, 106, 106));

        Ex = new JTextArea();
        Ex.setLineWrap(true);
        Ex.setWrapStyleWord(true);
        Ex.setBackground(new Color(106, 106, 106));
        Ex.setForeground(new Color(255, 255, 255));
        Ex.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        Ex.setColumns(43);
        //Ex.setMaximumSize(new Dimension(400, 300));
        add(Ex);
        isLove = new JCheckBox();
        isLove.setBackground(new Color(106, 106, 106));
        isLove.setForeground(new Color(210, 210, 210));
        isLove.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        isLove.setFocusPainted(false);
        isLove.setHorizontalTextPosition(SwingConstants.LEFT);
        try {
            isLove.setSelectedIcon(new ImageIcon(ImageIO.read(new File("src/images/t.png"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        add(isLove);

        layout.putConstraint(SpringLayout.NORTH, Ex, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, Ex, 10, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.SOUTH, this, 80, SpringLayout.SOUTH, Ex);
        layout.putConstraint(SpringLayout.EAST, this, 10, SpringLayout.EAST, Ex);

        layout.putConstraint(SpringLayout.NORTH, isLove, 20, SpringLayout.SOUTH, Ex);
        layout.putConstraint(SpringLayout.EAST, isLove, 0, SpringLayout.EAST, Ex);
    }
}
