package Gui.UserWindow;

import NonGui.ClientLogic.*;
import NonGui.InfoIO.InfoSend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 12/10/2016.
 */

class MyCellRenderer implements ListCellRenderer {
    private JPanel p;
    private JTextArea ta;

    public MyCellRenderer() {
        p = new JPanel();
        p.setLayout(new BorderLayout());

        // text
        ta = new JTextArea();
        p.add(ta, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(
            final JList list, final Object value, final int index,
            final boolean isSelected, final boolean hasFocus) {

        ta.setText((String) value);
        //int width = list.getWidth();
        // this is just to lure the ta's internal sizing mechanism into action
        //if (width > 0)
        ta.setSize(300, Short.MAX_VALUE);

        if (isSelected)
            ta.setBackground(new Color(193, 200, 66));
        else
            ta.setBackground(new Color(255, 255, 255));

        return p;
    }
}

public class FriendList extends JFrame {
    JPanel mainPanel;
    JButton addFriend;
    JButton deleteFriend;
    JButton logout;
    JLabel label;
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
        setSize(300, 600);
        setResizable(false);

        mainPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        mainPanel.setLayout(layout);
        mainPanel.setBackground(new Color(29, 34, 44));

        addFriend = new JButton("add new friend");
        addFriend.addActionListener(new AddFriendListener());
        mainPanel.add(addFriend);

        deleteFriend = new JButton("delete chosen friend");
        deleteFriend.addActionListener(new DeleteFriendListener());
        mainPanel.add(deleteFriend);

        logout = new JButton("log out");
        logout.addActionListener(new LogoutListener());
        add(logout);

        label = new JLabel("friends:");
        add(label);

        friends = new JList<>();
        friends.addMouseListener(new ChatDetector());
        friends.setPreferredSize(new Dimension(300, 300));
        friends.setCellRenderer(new MyCellRenderer());
        friends.setListData(new String[]{"aa", "bb", "windspe      14:32\n1 new message"});
        //add(friends);
    }

    public void refresh() {
        setTitle(State.userName + "'s friends");
        ArrayList<String> texts = new ArrayList<>();

        for (String name : corresFriends) {
            String text = name;
            ChatWindow chatWindow = getChatWindow(name);
            if (isOnline.get(name))
                text += "\tonline";
            else
                text += "\toffline";
            if (chatWindow.newMessageNum > 0) {
                text += "\n" + chatWindow.lastTime;
                if (chatWindow.newMessageNum == 1)
                    text += "      1 new message";
                else
                    text += "  " + chatWindow.newMessageNum + " new messages";
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
            String[] buttons = new String[] { "LOGIN", "REGISTER" };
            int buttonPressed = JOptionPane.showOptionDialog(
                    null, addFriendPanel, "login", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
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
}
