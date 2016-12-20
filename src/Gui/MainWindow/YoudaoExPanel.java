package Gui.MainWindow;

import NonGui.ClientLogic.State;
import NonGui.InfoIO.InfoSend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Administrator on 12/04/2016.
 */
public class YoudaoExPanel extends DemoExPanel implements AddIntoExPanel {
    YoudaoExPanel(ExplainPanel f)
    {
        super(f);
        isLove.setText("presented by youdao, love it?");
        isLove.addActionListener(new BoxListener());
    }

    public void add(String ExText, Boolean i)
    {
        setCheckBox(i);
        Ex.setText(ExText);
        fatherPanel.add(fatherPanel.youdaoPanel);
    }

    class BoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            if (isSetBox)
                return;
            if (!State.isLogIn) {
                JOptionPane.showMessageDialog(null, "please log in first");
                setCheckBox(false);
            }

            if (isLove.isSelected())
                InfoSend.love(State.currentWord, 2);
            else
                InfoSend.dislove(State.currentWord, 2);
        }
    }
}
