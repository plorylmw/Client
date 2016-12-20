package Gui.UserWindow;

import NonGui.ClientLogic.*;
import NonGui.InfoIO.InfoSend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 12/10/2016.
 */

class MyCellRenderer extends JTextField implements ListCellRenderer {

    public MyCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            final JList list, final Object value, final int index,
            final boolean isSelected, final boolean hasFocus) {

        setText(value.toString());
        setPreferredSize(new Dimension(350, 50));
        setFont(new Font("SimHei", Font.BOLD, 20));
        setForeground(new Color(3, 170, 232));

        if (isSelected) {
            setBackground(new Color(25, 45, 71));
            setBorder(BorderFactory.createLineBorder(new Color(36, 101, 159)));
        }
        else {
            setBackground(new Color(29, 34, 45));
            setBorder(null);
        }


        return this;
    }
}

public class FriendList extends JFrame {
    public JLabel nameLabel;
    JPanel mainPanel;
    JButton addFriend;
    JButton deleteFriend;
    JButton logout;
    JButton sendImage;
    JList<String> friends;

    Vector<String> corresFriends = new Vector<>();
    public final Map<String, Boolean> isOnline = new HashMap<>();
    public final Map<String, ChatWindow> chatWindows = new HashMap<>();
    public ChatWindow getChatWindow(String name) {
        if (!chatWindows.containsKey(name))
            chatWindows.put(name, new ChatWindow(name));
        return chatWindows.get(name);
    }
    public void addFriend(String f, Boolean i) {
        synchronized (corresFriends) {
            corresFriends.add(f);
        }
        synchronized (isOnline) {
            isOnline.put(f, i);
        }
    }
    public void addChatRecord(String user, String content, int sentToMe) {
        ChatWindow current = getChatWindow(user);
        String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        current.addChat(timeStamp, content, sentToMe);
        if (!current.isWindowOpened)
            current.newMessageNum++;
        else
            System.out.println("window open");
        refresh();
    }
    public void clearFriendInfos() {
        synchronized (corresFriends) {
            corresFriends.clear();
        }
        synchronized (isOnline) {
            isOnline.clear();
        }
    }
    public void addNewFriend(String f, Boolean i) {
        addFriend(f, i);
        refresh();
    }
    public void deleteFriend(String f) {
        corresFriends.remove(f);
        if (chatWindows.containsKey(f)) {
            chatWindows.get(f).setVisible(false);
            chatWindows.remove(f);
        }
        refresh();
    }
    public void setOnline(String f) {
        synchronized (isOnline) {
            isOnline.replace(f, true);
            addChatRecord(f, "", 2);
        }
    }
    public void setOffline(String f) {
        synchronized (isOnline) {
            isOnline.replace(f, false);
            addChatRecord(f, "", 3);
        }
    }

    public FriendList() {
        setLocation(300, 200);
        setSize(350, 700);
        setResizable(false);
        setTitle("好友");
        try {
            setIconImage(ImageIO.read(new File("src/images/MainFrameIcon.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        mainPanel.setLayout(layout);
        mainPanel.setBackground(new Color(40, 50, 68));
        mainPanel.setSize(350, 600);

        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        nameLabel.setForeground(new Color(3, 170, 232));
        mainPanel.add(nameLabel);

        addFriend = new JButton("添加好友");
        addFriend.addActionListener(new AddFriendListener());
        addFriend.setPreferredSize(new Dimension(140, 35));
        addFriend.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
        addFriend.setFocusPainted(false);
        addFriend.setBackground(new Color(34, 43, 58));
        addFriend.setForeground(new Color(255, 255, 255));
        mainPanel.add(addFriend);

        deleteFriend = new JButton("删除所选好友");
        deleteFriend.addActionListener(new DeleteFriendListener());
        deleteFriend.setPreferredSize(new Dimension(175, 35));
        deleteFriend.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
        deleteFriend.setFocusPainted(false);
        deleteFriend.setBackground(new Color(34, 43, 58));
        deleteFriend.setForeground(new Color(255, 255, 255));
        mainPanel.add(deleteFriend);

        logout = new JButton("登出");
        logout.addActionListener(new LogoutListener());
        logout.setPreferredSize(new Dimension(100, 35));
        logout.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
        logout.setFocusPainted(false);
        logout.setBackground(new Color(34, 43, 58));
        logout.setForeground(new Color(255, 255, 255));
        mainPanel.add(logout);

        sendImage = new JButton("给所选好友发送当前单词的单词卡");
        sendImage.addActionListener(new SendImageListener());
        sendImage.setPreferredSize(new Dimension(300, 35));
        sendImage.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
        sendImage.setFocusPainted(false);
        sendImage.setBackground(new Color(34, 43, 58));
        sendImage.setForeground(new Color(255, 255, 255));
        mainPanel.add(sendImage);

        SpringLayout friendsLayout = new SpringLayout();
        JPanel friendsPanel = new JPanel(friendsLayout);
        friendsPanel.setBackground(new Color(29, 34, 45));
        friends = new JList<>();
        friends.addMouseListener(new ChatDetector());
        friends.setPreferredSize(new Dimension(335, 1000));
        friends.setCellRenderer(new MyCellRenderer());
        friends.setListData(new String[]{"aa", "bb", "windspe      14:32\n1 new message"});
        friends.setBackground(new Color(29, 34, 45));
        friendsPanel.add(friends);
        friendsLayout.putConstraint(SpringLayout.NORTH, friends, 5, SpringLayout.NORTH, friendsPanel);
        friendsLayout.putConstraint(SpringLayout.WEST, friends, 5, SpringLayout.WEST, friendsPanel);
        friendsLayout.putConstraint(SpringLayout.SOUTH, friendsPanel, 700, SpringLayout.NORTH, friendsPanel);
        friendsLayout.putConstraint(SpringLayout.EAST, friendsPanel, 350, SpringLayout.WEST, friendsPanel);
        mainPanel.add(friendsPanel);

        add(mainPanel);

        layout.putConstraint(SpringLayout.NORTH, nameLabel, 15, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, nameLabel, 20, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, logout, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, logout, 200, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, addFriend, 10, SpringLayout.SOUTH, logout);
        layout.putConstraint(SpringLayout.WEST, addFriend, 10, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, deleteFriend, 10, SpringLayout.SOUTH, logout);
        layout.putConstraint(SpringLayout.WEST, deleteFriend, 10, SpringLayout.EAST, addFriend);

        layout.putConstraint(SpringLayout.NORTH, sendImage, 10, SpringLayout.SOUTH, addFriend);
        layout.putConstraint(SpringLayout.WEST, sendImage, 10, SpringLayout.WEST, addFriend);

        layout.putConstraint(SpringLayout.NORTH, friendsPanel, 10, SpringLayout.SOUTH, sendImage);
        layout.putConstraint(SpringLayout.WEST, friendsPanel, 0, SpringLayout.WEST, this);
    }

    public void refresh() {
        ArrayList<String> texts = new ArrayList<>();

        for (String name : corresFriends) {
            String text = " " + name;
            ChatWindow chatWindow = getChatWindow(name);
            if (chatWindow.newMessageNum > 0) {
                //text += "\t" + chatWindow.lastTime;
                if (chatWindow.newMessageNum == 1)
                    text += "\t1 new message";
                else
                    text += "\t" + chatWindow.newMessageNum + " new messages";
            }
            texts.add(text);
        }

        friends.setListData(texts.toArray(new String[]{}));
        revalidate();
    }

    class ChatDetector extends MouseAdapter {
        public void mouseClicked(MouseEvent e)
        {
            if (e.getClickCount() == 2)
            {
                int index = friends.locationToIndex(e.getPoint());
                String name = corresFriends.elementAt(index);
                ChatWindow chatWindow = getChatWindow(name);
                chatWindow.newMessageNum = 0;
                chatWindow.setVisible(true);
                chatWindow.isWindowOpened = true;
                refresh();
            }
        }
    }

    AddFriendPanel addFriendPanel = new AddFriendPanel();
    class AddFriendListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            String[] buttons = new String[] { "ADD", "CANCEL" };
            int buttonPressed = JOptionPane.showOptionDialog(
                    null, addFriendPanel, "add a friend", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, buttons, buttons[0]);
            String name = addFriendPanel.username.getText();

            InfoSend.addRequest(name);
        }
    }

    class DeleteFriendListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            int index = friends.getSelectedIndex();
            String name = corresFriends.get(index);
            int buttonPressed = JOptionPane.showConfirmDialog(null, "do you really want to delete" +
                    "this friend, " + name + "?", "alert", JOptionPane.OK_CANCEL_OPTION);
            if (buttonPressed == 0)
                InfoSend.deleteFriend(name);
        }
    }

    class LogoutListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            int buttonPressed = JOptionPane.showConfirmDialog(null, "are you sure to log out?", "info",
                    JOptionPane.OK_CANCEL_OPTION);
            if (buttonPressed == 0)
                InfoSend.logout();
        }
    }

    class SendImageListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            int index = friends.getSelectedIndex();
            InfoSend.sendImage(corresFriends.get(index));
        }
    }
}
