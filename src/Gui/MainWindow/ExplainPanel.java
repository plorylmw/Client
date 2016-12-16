package Gui.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 12/03/2016.
 */
public class ExplainPanel extends JPanel {
    public BaiduExPanel baiduPanel;
    public BingExPanel bingPanel;
    public YoudaoExPanel youdaoPanel;

    ExplainPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        baiduPanel = new BaiduExPanel(this);
        bingPanel = new BingExPanel(this);
        youdaoPanel = new YoudaoExPanel(this);

        JTextArea exArea = new JTextArea();
        exArea.setPreferredSize(new Dimension(300, 300));
        //add(exArea);
    }
}
