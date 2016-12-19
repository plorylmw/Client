package NonGui.ClientLogic;

import NonGui.InfoIO.InfoSend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

/**
 * Created by Administrator on 12/14/2016.
 */

public class InfoReceive {
    public static void register(Boolean isSuccess) {
        int buttonPressed;
        if (isSuccess) {
            buttonPressed = JOptionPane.showConfirmDialog(null, "registered successfully, " +
                            "do you want to log in?", "info", JOptionPane.OK_CANCEL_OPTION);
            if (buttonPressed == 0)
                InfoSend.login();
        }
        else
            JOptionPane.showMessageDialog(null, "failed to register, the username you chose has been" +
                    "used by someone else");
    }

    public static void login(String sessionId, Vector<String> friends, Vector<Boolean> isOnline) {
        //JOptionPane.showMessageDialog(null, "log in successfully");
        State.setIsLogIn(true);
        State.sessionId = sessionId;
        Main.friendList.clearFriendInfos();
        for (int i = 0; i < friends.size(); i++)
            Main.friendList.addFriend(friends.elementAt(i), isOnline.elementAt(i));
        Main.friendList.nameLabel.setText(State.userName);
        Main.friendList.refresh();
    }

    public static void loginFail() {
        JOptionPane.showMessageDialog(null, "failed to login, check your username or password");
    }
    public static void userNotFound() {
        JOptionPane.showMessageDialog(null, "user doesn't exist, check your friend's username");
    }
    public static void addRequest(String username) {
        int buttonPressed = JOptionPane.showConfirmDialog(null, username + " wants to be friend with" +
                "you, do you agree?", "friend request", JOptionPane.OK_CANCEL_OPTION);
        if (buttonPressed == 0)
            InfoSend.replyRequest(username, true);
        else
            InfoSend.replyRequest(username, false);
    }
    public static void agree(String username, Boolean isAgree) {
        if (isAgree)
            JOptionPane.showMessageDialog(null, username + " accepted your friend request");
        else
            JOptionPane.showMessageDialog(null, username + " declined your friend request");
    }
    public static void newFriend(String username, Boolean isOnline) {
        Main.friendList.addNewFriend(username, isOnline);
    }
    public static void delete(String username) {
        Main.friendList.deleteFriend(username);
    }
    public static void setOnline(String username, Boolean isOnline) {
        if (isOnline)
            Main.friendList.setOnline(username);
        else
            Main.friendList.setOffline(username);
    }
    public static void message(String username, String message) {
        Main.friendList.addChatRecord(username, message, 1);
    }
    public static void picture(String username, String word, String baiduEx, String bingEx,
                               String youdaoEx) {
        int buttonPressed = JOptionPane.showConfirmDialog(null, username + " wants to send you a word " +
                "card, do you agree?", "word card", JOptionPane.OK_CANCEL_OPTION);
        if (buttonPressed != 0)
            return;
        int w = 900;
        int h = 180;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(new Color(43, 52, 69));
        graphics.fillRect(0, 0, w, h);
        graphics.setColor(Color.white);
        graphics.drawString(word, 50, 20);
        graphics.drawString("baidu:\n" + baiduEx, 50, 60);
        graphics.drawString("bing:\n" + bingEx, 50, 100);
        graphics.drawString("youdao:\n" + youdaoEx, 50, 140);
        try {
            ImageIO.write(image, "jpg", new File("F:\\test\\out.jpg"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "word card has been stored in F:\\test\\out.jpg");
    }
}