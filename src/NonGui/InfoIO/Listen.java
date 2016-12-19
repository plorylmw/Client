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
                        if (ins.length == 6)
                            DoSearch.showResult(exs, ins[4], ins[5]);
                        else
                            DoSearch.showResult(exs, ins[4], "000");
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
            }
        }
        catch (Exception ex)
        {
            State.disConnect();
            return;
        }
    }
}
