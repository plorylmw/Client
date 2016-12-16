package NonGui.InfoIO;

import NonGui.ClientLogic.DoSearch;
import NonGui.ClientLogic.InfoReceive;
import NonGui.ClientLogic.State;

import javax.sound.midi.MidiDevice;
import javax.swing.*;
import java.util.Vector;

/**
 * Created by Administrator on 12/11/2016.
 */

public class Listen implements Runnable {
    @Override
    public void run() {
        try
        {
            while (true)
            {
                if (!State.getIsOnLine())
                    return;
                String str = State.input.readUTF();
                System.out.println("-" + str);

                String[] ins = str.split("&");

                switch (ins[0])
                {
                    case "register":
                        if (ins[1].equals("success"))
                            InfoReceive.register(true);
                        else
                            InfoReceive.register(false);
                        break;
                    case "login":
                        if (ins[1].equals("fail"))
                            InfoReceive.loginFail();
                        else {
                            Vector<String> names = new Vector<>();
                            Vector<Boolean> isOnline = new Vector<>();
                            for (int i = 2; i < ins.length; i += 2) {
                                names.add(ins[i]);
                                if (ins[i + 1].equals("0"))
                                    isOnline.add(false);
                                else
                                    isOnline.add(true);
                            }
                            InfoReceive.login(ins[1], names, isOnline);
                        }
                        break;
                    case "getword":
                        Vector<String> exs = new Vector<>();
                        for (int i = 1; i < 4; i++)
                            exs.add(ins[i]);
                        DoSearch.showResult(exs, ins[4], ins[5]);
                        break;
                    case "nosuchuser":
                        InfoReceive.userNotFound();
                        break;
                    case "addrequest":
                        InfoReceive.addRequest(ins[1]);
                        break;
                    case "agree":
                        InfoReceive.agree(ins[1], true);
                        break;
                    case "newfriend":
                        if (ins[2].equals("0"))
                            InfoReceive.newFriend(ins[1], false);
                        else
                            InfoReceive.newFriend(ins[1], true);
                        break;
                    case "decline":
                        InfoReceive.agree(ins[1], false);
                        break;
                    case "delete":
                        InfoReceive.delete(ins[1]);
                        break;
                    case "online":
                        InfoReceive.setOnline(ins[1], true);
                        break;
                    case "offline":
                        InfoReceive.setOnline(ins[1], false);
                        break;
                    case "message":
                        InfoReceive.message(ins[1], ins[2]);
                        break;
                    default:
                        System.out.println("error: no such instruction");
                }

/*
                if (ins[0].equals("message"))
                {
                    State.addChatRecord(ins[1], ins[2], 1);
                    continue;
                }
                if (ins[0].equals("online"))
                {
                    State.setOnline(ins[1]);
                    continue;
                }
                if (ins[0].equals("offline"))
                {
                    State.setOffline(ins[1]);
                    continue;
                }
                if (ins[0].equals("addrequest"))
                {
                    int buttonPressed = JOptionPane.showConfirmDialog(null, "friend request", "Will you accept " + ins[1] +
                            "'s friend request?", JOptionPane.OK_CANCEL_OPTION);
                    String sentStr = Code.encode(State.sessionId) + "&" + Code.encode(ins[1]);
                    if (buttonPressed == 0)
                        State.getOutput().writeUTF("agree&" + sentStr);
                    else
                        State.getOutput().writeUTF("decline&" + sentStr);
                    continue;
                }
                if (ins[0].equals("agree"))
                {
                    JOptionPane.showMessageDialog(new JFrame(), ins[1] + " accepted your friend request");
                    State.addNewFriend(ins[1], true);
                    continue;
                }
                if (ins[0].equals("decline"))
                {
                    JOptionPane.showMessageDialog(new JFrame(), ins[1] + " declined your friend request");
                    continue;
                }
                System.out.print("error\n");*/
            }
        }
        catch (Exception ex)
        {
            State.disConnect();
            return;
        }
    }
}
