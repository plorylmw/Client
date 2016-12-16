package Gui.MainWindow;

import NonGui.ClientLogic.State;
import NonGui.InfoIO.InfoSend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Administrator on 12/04/2016.
 */
public class BingExPanel extends DemoExPanel implements AddIntoExPanel {
    BingExPanel(ExplainPanel f)
    {
        super(f);
        isLove.setText("presented by bing");
        isLove.addActionListener(new BoxListener());
    }

    public void add(String ExText, Boolean i)
    {
        setCheckBox(i);
        Ex.setText(ExText);
        fatherPanel.add(fatherPanel.bingPanel);
    }

    class BoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            if (isSetBox)
                return;
            if (isLove.isSelected())
                InfoSend.love(State.currentWord, 1);
            else
                InfoSend.dislove(State.currentWord, 1);
        }
    }
}
