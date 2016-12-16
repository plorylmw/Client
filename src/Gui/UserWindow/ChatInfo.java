package Gui.UserWindow;

/**
 * Created by Administrator on 12/12/2016.
 */

public class ChatInfo {
    public String time;
    public String text;
    public int sentToMe;
    ChatInfo(String ti, String te, int s)
    {
        time = ti;
        text = te;
        sentToMe = s;
    }
}