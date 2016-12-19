package NonGui.ClientLogic;

import Gui.MainWindow.PopMenu;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Administrator on 12/04/2016.
 */
public class MakePopMenu {
    static Map<String, String> wordAndWordEx;
    static public void init()
    {
        try {
            wordAndWordEx = new HashMap<>();
            FileReader fileReader = new FileReader("src/NonGui/dictionary.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] wordAndEx = line.split("\t");
                wordAndWordEx.put(wordAndEx[0], wordAndEx[1]);
            }
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file");
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    static boolean isSub(String str, String subStr) {
        int i = 0;
        for (int j = 0; j < subStr.length(); i++, j++)
            if (str.charAt(i) != subStr.charAt(j)) {
                if (i != j)
                    return false;
                else
                    i++;
            }
        return true;
    }
    static boolean similar(String wordA, String wordB) {
        if (Math.abs(wordA.length() - wordB.length()) > 1)
            return false;
        if (wordA.length() > wordB.length())
            return isSub(wordA, wordB);
        else if (wordA.length() < wordB.length())
            return isSub(wordB, wordA);
        int diff = 0;
        int diffLoc1 = 0, diffLoc2 = 0;
        for (int i = 0; i < wordA.length(); i++)
            if (wordA.charAt(i) != wordB.charAt(i)) {
                diff++;
                if (diff == 1)
                    diffLoc1 = i;
                else if (diff == 2)
                    diffLoc2 = i;
                else
                    return false;
            }
        if (diff == 1)
            return true;
        else if (wordA.charAt(diffLoc1) == wordB.charAt(diffLoc2) && wordA.charAt(diffLoc2) == wordB.charAt(diffLoc1))
            return true;
        else
            return false;
    }
    static Vector<String> getWordCorrected(String word) {
        Vector<String> result = new Vector<>();
        for (String i : wordAndWordEx.keySet())
            if (similar(word, i))
                result.add(i);
        Collections.sort(result);
        return result;
    }

    static Vector<String> getWordCompleted(String word) {
        Vector<String> result = new Vector<>();
        int wordLen = word.length();
        for (String i : wordAndWordEx.keySet())
            if (i.length() >= wordLen && word.equals(i.substring(0, wordLen)))
                result.add(i);
        Collections.sort(result);
        return result;
    }

    static Vector<String> getAltn(String input)
    {
        Vector<String> alternatives = getWordCompleted(input);
        if (alternatives.size() == 0)
            alternatives = getWordCorrected(input);

        Vector<String> result = new Vector<>();
        for (int i = 0; i < 5 && i < alternatives.size(); i++)
            result.add(alternatives.get(i));

        return result;
    }
    public static void make(String input)
    {
        input = input.trim();
        if (input.equals("")) {
            Main.mainGui.setIsMainG(true);
            Main.mainGui.popMenu.setVisible(false);
            Main.mainGui.resultPanel.explainPanel.removeAll();
            return;
        }

        Vector<String> alternatives = getAltn(input);
        PopMenu p = Main.mainGui.popMenu;
        p.removeAll();
        p.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        p.setBackground(new Color(53, 53, 53));
        for (String i : alternatives)
        {
            JMenuItem m = new JMenuItem(i);
            m.addActionListener(p.selectedListener);
            m.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 20));
            m.setForeground(new Color(255, 255, 255));
            m.setBackground(new Color(53, 53, 53));
            m.setBorder(BorderFactory.createLineBorder(new Color(53, 53, 53)));
            p.add(m);
        }

        p.setVisible(false);
        p.setVisible(true);
        p.num = p.getComponentCount();
        p.chosenNo = 0;

        Main.mainGui.showPopMenu(alternatives.size());
        Main.mainGui.searchPanel.textField.requestFocus();
    }
}
