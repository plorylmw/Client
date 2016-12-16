package NonGui.ClientLogic;

import Gui.UserWindow.ChatWindow;
import Gui.UserWindow.FriendList;
import NonGui.InfoIO.Listen;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Administrator on 12/04/2016.
 */

public class State {
    public enum Names {
        baidu, bing, youdao
    }

    public static Boolean isDebug = true;
    public static final String IP_ADDR = "114.212.130.32";
    public static final int PORT = 8000;
    public static Socket socket;
    public static DataInputStream input;
    public static DataOutputStream output;
    public static Thread listenThread;
    public static Boolean isOnLine = false;
    public static Boolean getIsOnLine() {
        synchronized (isOnLine)
        {
            return isOnLine;
        }
    }
    public static boolean tryConnect() {
        if (isOnLine || isDebug)
            return true;

        try {
            socket = new Socket(State.IP_ADDR, State.PORT);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            isOnLine = true;
            listenThread = new Thread(new Listen());
            listenThread.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "You're offline!");
            return false;
        }
    }
    public static void disConnect() {
        synchronized (isOnLine)
        {
            isOnLine = false;
            isLogIn = false;
            if (listenThread != null) {
                listenThread.stop();
                listenThread = null;
            }
        }
    }
    public static void logout() {
        isLogIn = false;
        FriendList f = Main.friendList;
        for (ChatWindow c : f.chatWindows.values())
            c.setVisible(false);
        Main.friendList.setVisible(false);
    }

    public static Boolean isLogIn = false;
    public static Boolean getIsLogIn() {
        synchronized (isLogIn)
        {
            return isLogIn;
        }
    }
    public static void setIsLogIn(Boolean i) {
        synchronized (isLogIn)
        {
            isLogIn = i;
        }
    }
    public static String userName = new String();
    public static String userPassword = new String();
    public static String sessionId = new String();

    public static Vector<Boolean> isOn = new Vector<>();
    public static String currentWord = new String();

    public static void init() {

        isOn.add(true);
        isOn.add(true);
        isOn.add(true);

        tryConnect();
    }
}