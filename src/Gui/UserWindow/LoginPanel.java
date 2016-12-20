package Gui.UserWindow;

import NonGui.ClientLogic.DoSearch;
import NonGui.ClientLogic.State;
import NonGui.InfoIO.InfoSend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * Created by Administrator on 12/06/2016.
 */
public class LoginPanel extends JFrame {
    JPanel mainPanel;
    JLabel nameLabel;
    public JTextField username;
    JLabel passwordLabel;
    public JTextField password;
    JButton login;
    JButton register;

    public LoginPanel()
    {
        setPreferredSize(new Dimension(400, 300));
        setLocation(300, 200);
        setTitle("登陆");
        setResizable(false);
        try {
            setIconImage(ImageIO.read(new File("src/images/MainFrameIcon.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        SpringLayout layout = new SpringLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        mainPanel.setBackground(new Color(29, 34, 44));

        nameLabel = new JLabel("登录名：");
        nameLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        nameLabel.setForeground(new Color(190, 190, 190));
        mainPanel.add(nameLabel);

        username = new JTextField();
        username.setPreferredSize(new Dimension(220, 40));
        username.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        username.setBorder(BorderFactory.createLineBorder(new Color(36, 101, 159)));
        username.setBackground(new Color(14, 17, 22));
        username.setForeground(new Color(255, 255, 255));
        username.setCaretColor(Color.WHITE);
        mainPanel.add(username);

        passwordLabel = new JLabel("密码：");
        passwordLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        passwordLabel.setForeground(new Color(190, 190, 190));
        mainPanel.add(passwordLabel);

        password = new JTextField();
        password.setPreferredSize(new Dimension(220, 40));
        password.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        password.setBorder(BorderFactory.createLineBorder(new Color(36, 101, 159)));
        password.setBackground(new Color(14, 17, 22));
        password.setForeground(new Color(255, 255, 255));
        password.setCaretColor(Color.WHITE);
        password.addKeyListener(new WordInputListener());
        mainPanel.add(password);

        login = new JButton("登陆");
        login.addActionListener(new LoginListener());
        login.setPreferredSize(new Dimension(134, 40));
        login.setBackground(new Color(9, 138, 199));
        login.setForeground(new Color(255, 255, 255));
        login.setFont(new Font("SimHei", Font.PLAIN, 20));
        login.setFocusPainted(false);
        mainPanel.add(login);

        register = new JButton("注册");
        register.addActionListener(new RegisterListener());
        register.setPreferredSize(new Dimension(134, 40));
        register.setBackground(new Color(19, 26, 34));
        register.setForeground(new Color(9, 138, 199));
        register.setFont(new Font("SimHei", Font.PLAIN, 20));
        register.setFocusPainted(false);
        mainPanel.add(register);

        add(mainPanel);

        layout.putConstraint(SpringLayout.NORTH, nameLabel, 50, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, nameLabel, 40, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, username, -5, SpringLayout.NORTH, nameLabel);
        layout.putConstraint(SpringLayout.WEST, username, 10, SpringLayout.EAST, nameLabel);

        layout.putConstraint(SpringLayout.NORTH, passwordLabel, 20, SpringLayout.SOUTH, nameLabel);
        layout.putConstraint(SpringLayout.WEST, passwordLabel, 0, SpringLayout.WEST, nameLabel);

        layout.putConstraint(SpringLayout.NORTH, password, -5, SpringLayout.NORTH, passwordLabel);
        layout.putConstraint(SpringLayout.WEST, password, 0, SpringLayout.WEST, username);

        layout.putConstraint(SpringLayout.NORTH, password, -5, SpringLayout.NORTH, passwordLabel);
        layout.putConstraint(SpringLayout.WEST, password, 0, SpringLayout.WEST, username);

        layout.putConstraint(SpringLayout.NORTH, login, 55, SpringLayout.SOUTH, passwordLabel);
        layout.putConstraint(SpringLayout.WEST, login, 0, SpringLayout.WEST, passwordLabel);

        layout.putConstraint(SpringLayout.NORTH, register, 0, SpringLayout.NORTH, login);
        layout.putConstraint(SpringLayout.WEST, register, 40, SpringLayout.EAST, login);

        pack();
    }

    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            State.userName = username.getText();
            State.userPassword = password.getText();
            InfoSend.login();
            setVisible(false);
        }
    }

    class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            State.userName = username.getText();
            State.userPassword = password.getText();
            InfoSend.register();
            setVisible(false);
        }
    }

    class WordInputListener implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {

        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                State.userName = username.getText();
                State.userPassword = password.getText();
                InfoSend.login();
                setVisible(false);
            }
        }
    }
}
