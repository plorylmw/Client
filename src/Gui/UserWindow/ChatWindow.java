package Gui.UserWindow;

import Gui.MainWindow.MainGui;
import Gui.MainWindow.ResultPanel;
import Gui.MainWindow.SearchPanel;
import NonGui.ClientLogic.Main;
import NonGui.ClientLogic.State;
import NonGui.InfoIO.InfoSend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Administrator on 12/12/2016.
 */
public class ChatWindow extends JFrame {
    String strUsername;
    Boolean lastIsSentToMe;
    String strChat = new String();
    JPanel mainPanel;
    JLabel nameLabel;
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
        setLocation(400, 300);
        setPreferredSize(new Dimension(450, 600));
        setResizable(false);
        addWindowListener(new CloseListener());
        contentPane = getContentPane();
        setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        try {
            setIconImage(ImageIO.read(new File("src/images/MainFrameIcon.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        SpringLayout layout = new SpringLayout();
        mainPanel = new JPanel(layout);
        mainPanel.setBackground(new Color(40, 50, 68));

        nameLabel = new JLabel(strUsername);
        nameLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        nameLabel.setForeground(new Color(3, 170, 232));
        mainPanel.add(nameLabel);

        SpringLayout scrollLayout = new SpringLayout();
        JPanel scrollPanel = new JPanel(scrollLayout);
        scrollPanel.setBackground(new Color(20, 25, 34));
        chat = new JTextArea();
        chat.setEditable(false);
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        chat.setBackground(new Color(20, 25, 34));
        chat.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        chat.setForeground(new Color(190, 190, 190));
        scrollPane = new JScrollPane(chat);
        scrollPane.setPreferredSize(new Dimension(423, 330));
        scrollPane.setBorder(null);
        scrollPanel.add(scrollPane);
        scrollLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, scrollPanel);
        scrollLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, scrollPanel);
        scrollLayout.putConstraint(SpringLayout.EAST, scrollPanel, 450, SpringLayout.WEST, scrollPanel);
        scrollLayout.putConstraint(SpringLayout.SOUTH, scrollPanel, 350, SpringLayout.NORTH, scrollPanel);
        mainPanel.add(scrollPanel);

        SpringLayout messageLayout = new SpringLayout();
        JPanel messagePanel = new JPanel(messageLayout);
        messagePanel.setBackground(new Color(20, 25, 34));
        messageSent = new JTextArea();
        messageSent.setPreferredSize(new Dimension(408, 95));
        messageSent.setLineWrap(true);
        messageSent.setWrapStyleWord(true);
        messageSent.addKeyListener(new WordInputListener());
        messageSent.setBackground(new Color(20, 25, 34));
        messageSent.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        messageSent.setForeground(new Color(190, 190, 190));
        messageSent.setCaretColor(Color.WHITE);
        messagePanel.add(messageSent);
        messageLayout.putConstraint(SpringLayout.WEST, messageSent, 10, SpringLayout.WEST, messagePanel);
        messageLayout.putConstraint(SpringLayout.NORTH, messageSent, 10, SpringLayout.NORTH, messagePanel);
        messageLayout.putConstraint(SpringLayout.EAST, messagePanel, 425, SpringLayout.WEST, messagePanel);
        messageLayout.putConstraint(SpringLayout.SOUTH, messagePanel, 115, SpringLayout.NORTH,
                messagePanel);
        mainPanel.add(messagePanel);

        layout.putConstraint(SpringLayout.WEST, nameLabel, 15, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, nameLabel, 10, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, scrollPanel, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, scrollPanel, 10, SpringLayout.SOUTH, nameLabel);

        layout.putConstraint(SpringLayout.WEST, messagePanel, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, messagePanel, 10, SpringLayout.SOUTH, scrollPanel);

        //layout.putConstraint(SpringLayout.SOUTH, mainPanel, 600, SpringLayout.NORTH, this);
        //layout.putConstraint(SpringLayout.EAST, mainPanel, 500, SpringLayout.SOUTH, this);

        add(mainPanel);
        pack();
    }

    class WordInputListener implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (Main.friendList.isOnline.get(strUsername))
                    InfoSend.sendMessage(strUsername, messageSent.getText());
                else
                    addChat("", "", 3);
            }
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
        strChat += "- " + content;
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
