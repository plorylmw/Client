package Gui.MainWindow;

import NonGui.ClientLogic.Main;
import NonGui.ClientLogic.State;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Administrator on 12/02/2016.
 */
public class ChoosePanel extends JPanel {
    public JButton check_baidu;
    public JButton check_youdao;
    public JButton check_bing;
    ImageIcon baiduOn;
    ImageIcon bingOn;
    ImageIcon youdaoOn;
    ImageIcon baiduOff;
    ImageIcon bingOff;
    ImageIcon youdaoOff;
    ExplainPanel explainPanel;

    ChoosePanel()
    {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        try {
            baiduOn = new ImageIcon(ImageIO.read(new File("src/images/baiduOn.png")));
            baiduOff = new ImageIcon(ImageIO.read(new File("src/images/baiduOff.png")));
            check_baidu = new JButton(baiduOn);
            check_baidu.setSize(50, 50);
            check_baidu.setPreferredSize(new Dimension(50, 50));
            check_baidu.addActionListener(new BaiduListener());
            check_baidu.setFocusPainted(false);
            add(check_baidu);

            bingOn = new ImageIcon(ImageIO.read(new File("src/images/bingOn.png")));
            bingOff = new ImageIcon(ImageIO.read(new File("src/images/bingOff.png")));
            check_bing = new JButton(bingOn);
            check_bing.addActionListener(new BingListener());
            check_bing.setSize(50, 50);
            check_bing.setPreferredSize(new Dimension(50, 50));
            check_bing.setFocusPainted(false);
            add(check_bing);

            youdaoOn = new ImageIcon(ImageIO.read(new File("src/images/youdaoOn.png")));
            youdaoOff = new ImageIcon(ImageIO.read(new File("src/images/youdaoOff.png")));
            check_youdao = new JButton(youdaoOn);
            check_youdao.addActionListener(new YoudaoListener());
            check_youdao.setSize(50, 50);
            check_youdao.setPreferredSize(new Dimension(50, 50));
            check_youdao.setFocusPainted(false);
            add(check_youdao);
        } catch (Exception e) {
            e.printStackTrace();
        }

        layout.putConstraint(SpringLayout.NORTH, check_baidu, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, check_baidu, 0, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, check_bing, 0, SpringLayout.SOUTH, check_baidu);
        layout.putConstraint(SpringLayout.WEST, check_bing, 0, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, check_youdao, 0, SpringLayout.SOUTH, check_bing);
        layout.putConstraint(SpringLayout.WEST, check_youdao, 0, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.SOUTH, this, 0, SpringLayout.SOUTH, check_youdao);
        layout.putConstraint(SpringLayout.EAST, this, 0, SpringLayout.EAST, check_baidu);

    }

    public void init() {
        explainPanel = Main.mainGui.resultPanel.explainPanel;
    }

    class BaiduListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Boolean isOn = !State.isOn.get(0);
            State.isOn.set(0, isOn);
            if (isOn) {
                if (!Main.mainGui.isMainG)
                    explainPanel.add(explainPanel.baiduPanel);
                check_baidu.setIcon(baiduOn);
            }
            else {
                if (!Main.mainGui.isMainG)
                    explainPanel.remove(explainPanel.baiduPanel);
                check_baidu.setIcon(baiduOff);
            }
            Main.mainGui.resultPanel.updateUI();
        }
    }
    class BingListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Boolean isOn = !State.isOn.get(1);
            State.isOn.set(1, isOn);
            if (isOn) {
                if (!Main.mainGui.isMainG)
                    explainPanel.add(explainPanel.bingPanel);
                check_bing.setIcon(bingOn);
            }
            else {
                if (!Main.mainGui.isMainG)
                    explainPanel.remove(explainPanel.bingPanel);
                check_bing.setIcon(bingOff);
            }
            Main.mainGui.resultPanel.updateUI();
        }
    }
    class YoudaoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Boolean isOn = !State.isOn.get(2);
            State.isOn.set(2, isOn);
            if (isOn) {
                if (!Main.mainGui.isMainG)
                    explainPanel.add(explainPanel.youdaoPanel);
                check_youdao.setIcon(youdaoOn);
            }
            else {
                if (!Main.mainGui.isMainG)
                    explainPanel.remove(explainPanel.youdaoPanel);
                check_youdao.setIcon(youdaoOff);
            }
            Main.mainGui.resultPanel.updateUI();
        }
    }
}
