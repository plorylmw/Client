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
        {
            Main.friendList.setVisible(true);
        }
        else
        {
            String[] buttons = new String[] { "LOGIN", "REGISTER" };
            int buttonPressed = JOptionPane.showOptionDialog(
                    null, loginPanel, "login", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    buttons, buttons[0]);
            State.userName = loginPanel.username.getText();
            State.userPassword = loginPanel.password.getText();

            switch (buttonPressed)
            {
                case 0:
                    InfoSend.login();
                    break;
                case 1:
                    InfoSend.register();
                    break;
                default:
            }
        }
    }
}
