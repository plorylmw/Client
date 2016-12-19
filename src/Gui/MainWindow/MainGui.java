package Gui.MainWindow;

import NonGui.ClientLogic.State;
import NonGui.InfoIO.InfoSend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Administrator on 12/02/2016.
 */
public class MainGui extends JFrame {
    Container contentPane;
    JPanel mainPanel;
    public SearchPanel searchPanel;
    public ResultPanel resultPanel;
    public PopMenu popMenu;
    Image backgroundImage;
    Image backgroundOn;
    Image backgroundOff;
    public Boolean isMainG = true;
    public void setIsMainG(Boolean i) {
        isMainG = i;
        if (i)
            backgroundImage = backgroundOn;
        else
            backgroundImage = backgroundOff;
        mainPanel.updateUI();
    }

    public void showPopMenu(int n) {
        popMenu.setPopupSize(650, n * 40);
        popMenu.show(getContentPane(), 110, 80);
    }

    public MainGui()
    {
        setLocation(200, 100);
        setPreferredSize(new Dimension(1000, 750));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("我的词典");
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        setIconImage(icon);
        addWindowListener(new CloseListener());

        contentPane = getContentPane();
        setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        mainPanel = new MainPanel();

        try {
            backgroundOn = ImageIO.read(new File("src/images/MainBgOn.png"));
            backgroundOff = ImageIO.read(new File("src/images/MainBgOff.png"));
            setIconImage(ImageIO.read(new File("src/images/MainFrameIcon.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        backgroundImage = backgroundOn;

        SpringLayout layout = new SpringLayout();
        mainPanel.setLayout(layout);

        searchPanel = new SearchPanel();
        mainPanel.add(searchPanel);
        resultPanel = new ResultPanel();
        mainPanel.add(resultPanel);

        layout.putConstraint(SpringLayout.WEST, searchPanel, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, searchPanel, 0, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, resultPanel, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, resultPanel, 0, SpringLayout.SOUTH, searchPanel);

        add(mainPanel);

        pack();
        setVisible(true);

        searchPanel.textField.requestFocus();
        popMenu = new PopMenu();
    }

    class MainPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, null);
        }
        MainPanel() {

        }
    }

    class CloseListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            if (State.isLogIn)
                InfoSend.logout();
            if (State.isOnLine) {
                try {
                    State.socket.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            super.windowClosing(e);
        }
    }
}
