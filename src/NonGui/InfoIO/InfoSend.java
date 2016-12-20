package NonGui.InfoIO;

import Gui.UserWindow.FriendList;
import NonGui.ClientLogic.Main;
import NonGui.ClientLogic.State;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by Administrator on 12/09/2016.
 */
public class InfoSend {
    static void send(String str) {
        System.out.println(str);
        if (State.isDebug)
            return;
        try {
            State.output.writeUTF(str);
        } catch (Exception e) {
            State.disConnect();
        }
    }

    public static void register()
    {
        String str = "register&" + Code.encode(State.userName) + "&" + Code.encode(State.userPassword);
        send(str);
    }

    public static void login() {
        String str = "login&" + Code.encode(State.userName) + "&" + Code.encode(State.userPassword);
        send(str);
    }

    public static void logout() {
        String str = "logout&" + Code.encode(State.sessionId);
        send(str);
        State.setIsLogIn(false);
    }

    public static void getWord(String word) {
        String str;
        if (State.isLogIn)
            str = "getword&" + Code.encode(State.sessionId) + "&" + Code.encode(word);
        else
            str = "getwordoffline&" + Code.encode(word);
        send(str);
    }

    public static void love(String word, int type) {
        String str = "love&" + Code.encode(State.sessionId) + "&" + Code.encode(word) + "&";
        switch (type) {
            case 0:
                str += "baidu";
                break;
            case 1:
                str += "bing";
                break;
            case 2:
                str += "youdao";
        }
        send(str);
    }

    public static void dislove(String word, int type) {
        String str = "dislove&" + Code.encode(State.sessionId) + "&" + Code.encode(word) + "&";
        switch (type) {
            case 0:
                str += "baidu";
                break;
            case 1:
                str += "bing";
                break;
            case 2:
                str += "youdao";
        }
        send(str);
    }

    public static void addRequest(String username) {
        String str = "addrequest&" + Code.encode(State.sessionId) + "&" + Code.encode(username);
        send(str);
    }

    public static void replyRequest(String username, Boolean isAgree) {
        String str = (isAgree) ? "agree&" : "decline&";
        str += Code.encode(State.sessionId) + "&" + Code.encode(username);
        send(str);
    }

    public static void deleteFriend(String username) {
        String str = "delete&" + Code.encode(State.sessionId) + "&" + Code.encode(username);
        send(str);
    }

    public static void sendMessage(String username, String message) {
        if (message.equals(""))
            return;
        String str = "message&" + Code.encode(State.sessionId) + "&" + Code.encode(username) + "&" +
                message;
        Main.friendList.addChatRecord(username, message, 0);
        send(str);
    }

    public static void sendImage(String username) {
        if (State.currentWord.equals(""))
            return;
        String str = "picture&" + Code.encode(State.sessionId) + "&" + Code.encode(username) + "&" +
                State.currentWord;
        send(str);
    }
}