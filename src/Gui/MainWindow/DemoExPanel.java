package Gui.MainWindow;

import javax.swing.*;
import java.awt.*;

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

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(new Color(106, 106, 106));

        Ex = new JTextArea();
        Ex.setLineWrap(true);
        Ex.setWrapStyleWord(true);
        Ex.setBackground(new Color(106, 106, 106));
        Ex.setForeground(new Color(255, 255, 255));
        Ex.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        Ex.setColumns(43);
        add(Ex);
        isLove = new JCheckBox();
        isLove.setBackground(new Color(106, 106, 106));
        isLove.setForeground(new Color(255, 255, 255));
        isLove.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        isLove.setFocusPainted(false);
        add(isLove);
    }
}
