package NonGui.ClientLogic;

import Gui.UserWindow.LoginPanel;
import NonGui.InfoIO.InfoSend;

import javax.swing.*;

/**
 * Created by Administrator on 12/06/2016.
 */
public class DoLogin {
    static LoginPanel loginPanel = new LoginPanel();

    public static void DoIt()
    {
        if (!State.tryConnect())
            return;

        if (State.getIsLogIn())
            Main.friendList.setVisible(true);
        else
            loginPanel.setVisible(true);
    }
}
