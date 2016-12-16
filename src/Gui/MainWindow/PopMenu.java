package Gui.MainWindow;

import NonGui.ClientLogic.DoSearch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Administrator on 12/04/2016.
 */
public class PopMenu extends JPopupMenu {
    public SelectedListener selectedListener;
    public int chosenNo = 0;
    public int num = 0;
    PopMenu() {
        selectedListener = new SelectedListener();
    }

    @Override
    public void paintComponent(final Graphics g) {
        g.setColor(new Color(53, 53, 53));
        g.fillRect(0,0,getWidth(), getHeight());
    }

    String getChosenText()
    {
        if (num == 0)
            return "";
        if (chosenNo == 0)
            return "";

        return ((JMenuItem)getComponent(chosenNo - 1)).getText();
    }

    class SelectedListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            DoSearch.doIt(actionEvent.getActionCommand());
        }
    }
}
