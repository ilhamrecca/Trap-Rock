package Server;

import java.net.InetAddress;

public class Packet00Login extends Packet {

    public Packet00Login(String data) {
        super("00"+data);
    }
    
    public Packet00Login(String data, String player,InetAddress ipAddress, int port) {
    	super("00"+player+data, ipAddress, port);
    }

}