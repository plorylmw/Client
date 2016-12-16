package Gui.UserWindow;

import NonGui.ClientLogic.State;
import NonGui.InfoIO.InfoSend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Administrator on 12/12/2016.
 */
public class ChatWindow extends JFrame {
    String strUsername;
    Boolean lastIsSentToMe;
    String strChat = new String();
    JTextArea chat;
    JScrollPane scrollPane;
    JTextArea messageSent;
    Container contentPane;

    public int newMessageNum = 0;
    public Boolean isWindowOpened = false;
    public String lastTime;

    ChatWindow(String s) {
        strUsername = s;

        setTitle(strUsername);
        setSize(600, 900);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        addWindowListener(new CloseListener());

        chat = new JTextArea();
        chat.setEditable(false);
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        scrollPane = new JScrollPane(chat);
        scrollPane.setPreferredSize(new Dimension(480, 500));
        add(scrollPane);

        messageSent = new JTextArea();
        messageSent.setPreferredSize(new Dimension(480, 100));
        messageSent.setLineWrap(true);
        messageSent.setWrapStyleWord(true);
        messageSent.addKeyListener(new WordInputListener());
        add(messageSent);

        //contentPane = getContentPane();

        layout.putConstraint(SpringLayout.WEST, scrollPane, 30, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 40, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, messageSent, 30, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, messageSent, 40, SpringLayout.SOUTH, scrollPane);
    }

    class WordInputListener implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                InfoSend.sendMessage(strUsername, messageSent.getText());
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                messageSent.setText("");
        }
    }

    public void addChat(String timeStamp, String content, int sentToMe)
    {
        lastTime = timeStamp;
        Boolean isSentToMe;

        if (!strChat.equals(""))
            strChat += "\n";

        switch (sentToMe)
        {
            case 0:
                isSentToMe = false;
                break;
            case 1:
                isSentToMe = true;
                break;
            case 2:
                strChat += strUsername + " has come online";
                chat.setText(strChat);
                return;
            default:
                strChat += strUsername + " has gone offline";
                chat.setText(strChat);
                return;
        }

        if (strChat.equals(""))
            lastIsSentToMe = !isSentToMe;

        if (lastIsSentToMe != isSentToMe)
        {
            lastIsSentToMe = isSentToMe;
            if (isSentToMe)
                strChat += strUsername + "  " + timeStamp + "\n";
            else
                strChat += State.userName + "  " + timeStamp + "\n";
        }
        strChat += "·" + content;
        chat.setText(strChat);
    }

    class CloseListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            isWindowOpened = false;
            super.windowClosing(e);
        }
    }
}
