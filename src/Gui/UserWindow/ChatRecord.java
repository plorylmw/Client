package Gui.UserWindow;

import Gui.UserWindow.ChatInfo;
import Gui.UserWindow.ChatWindow;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Vector;

/**
 * Created by Administrator on 12/12/2016.
 */

public class ChatRecord {
    public Boolean isOnline;
    public Vector<ChatInfo> records;
    public int newMessageNum = 0;
    public Boolean isWindowOpened = false;
    public ChatWindow chatWindow;

    ChatRecord(Boolean i) {
        isOnline = i;
    }
}