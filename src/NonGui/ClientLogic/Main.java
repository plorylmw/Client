package NonGui.ClientLogic;

import Gui.MainWindow.MainGui;
import Gui.UserWindow.FriendList;
import NonGui.InfoIO.InfoSend;
import NonGui.InfoIO.Listen;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Administrator on 12/04/2016.
 */

public class Main {
    public static MainGui mainGui;
    public static FriendList friendList;
    public static Thread threadListen;

    static void init()
    {
        MakePopMenu.init();
        mainGui.searchPanel.init();
        DoSearch.init();
        mainGui.resultPanel.choosePanel.init();

        // TODO login

        // to be deleted
        mainGui.resultPanel.choosePanel.check_baidu.setSelected(true);
        mainGui.resultPanel.choosePanel.check_youdao.setSelected(true);
        mainGui.resultPanel.choosePanel.check_bing.setSelected(true);

        State.init();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        FontUIResource f = new FontUIResource("default", Font.PLAIN, 12);
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }

        mainGui = new MainGui();
        friendList = new FriendList();
        init();

        if (State.isDebug) {
            String fonts[] =
                    GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
            for (int i = 0; i < fonts.length; i++) {
                System.out.println(fonts[i]);
            }

            Vector<String> friends = new Vector<>();
            friends.add("xiaohong");
            friends.add("xiaoming");
            friends.add("xiaofang");
            Vector<Boolean> isOnline = new Vector<>();
            isOnline.add(true);
            isOnline.add(true);
            isOnline.add(true);
            State.userName = "windspe";
            InfoReceive.login("1997", friends, isOnline);
            Vector<String> exs = new Vector<>();
            exs.add("bed");
            exs.add("bed");
            exs.add("bed");
            DoSearch.showResult(exs, "012", "000");
            exs.clear();
            exs.add("床");
            exs.add("卧室是是是是分为发");
            exs.add("分为发而我访问");
            DoSearch.showResult(exs, "012", "000");

        }
    }
}