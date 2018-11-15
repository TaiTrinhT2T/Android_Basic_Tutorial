package example.com.soundrecordandanalysis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collection;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

import android.os.Handler;
import android.util.Log;

public class MainActivity extends Thread{
    public static final String HOST = "talk.google.com";
    public static final int PORT = 5222;
    public static final String SERVICE = "gmail.com";
    public static final String USERNAME = "tavistu@gmail.com";
    public static final String PASSWORD = "";
    private XMPPConnection connection=null;

    public MainActivity()
    {
        System.out.print("okkkkkkkkkk");
        start();
    }

    public void run() {
        try
        {
            System.out.print("XMPPChatDemoActivity");
            ConnectionConfiguration connConfig = new ConnectionConfiguration(
                    HOST, PORT, SERVICE);
            System.out.print(connConfig+"");
            connection = new XMPPConnection(connConfig);
            System.out.print(connection+"");
        }
        catch(Exception e){
            System.out.print("XMPPChatDemoActivity cf1"+e);

        }

        try {
            connection.connect();

        } catch (Exception ex) {

        }
        try {
            // SASLAuthentication.supportSASLMechanism("PLAIN", 0);
            connection.login(USERNAME, PASSWORD);
            //sendMSG("n");



        } catch (XMPPException ex) {

        }

    }



    public void sendMSG(String text)
    {
        String to = "tavistuandroid@gmail.com";
        Message msg = new Message(to, Message.Type.chat);
        msg.setBody(text);
        if (connection != null) {
            connection.sendPacket(msg);
        }
    }



}
