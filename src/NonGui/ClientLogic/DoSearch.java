package NonGui.ClientLogic;

import Gui.MainWindow.AddIntoExPanel;
import Gui.MainWindow.ExplainPanel;
import NonGui.InfoIO.InfoSend;

import java.util.Vector;

/**
 * Created by Administrator on 12/04/2016.
 */
public class DoSearch {
    static AddIntoExPanel[] addFuncs;

    public static void init()
    {
        ExplainPanel p = Main.mainGui.resultPanel.explainPanel;
        addFuncs = new AddIntoExPanel[]{p.baiduPanel, p.bingPanel, p.youdaoPanel };
    }

    public static void doIt(String word) {
        if (!State.tryConnect())
            return;

        word = word.trim();
        if (word.equals(""))
            return;

        Main.mainGui.searchPanel.setTextField(word);
        Main.mainGui.popMenu.setVisible(false);
        State.currentWord = word;
        InfoSend.getWord(word);
    }

    public static void showResult(Vector<String> exs, String priority, String isLoved) {
        Main.mainGui.setIsMainG(false);
        Main.mainGui.resultPanel.explainPanel.removeAll();

        for (int i = 0; i < 3; i++)
        {
            int type = priority.charAt(i) - '0';
            if (!State.isOn.get(type))
                continue;
            if (isLoved.charAt(type) == '0')
                addFuncs[type].add(exs.elementAt(type), false);
            else
                addFuncs[type].add(exs.elementAt(type), true);
        }

        Main.mainGui.resultPanel.explainPanel.updateUI();
    }
}
