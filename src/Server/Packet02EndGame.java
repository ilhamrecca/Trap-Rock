package Server;

import java.net.InetAddress;

public class Packet02EndGame extends Packet {
	public Packet02EndGame(String data) {
        super("02"+data);
    }
    
    public Packet02EndGame(String data, String player,InetAddress ipAddress, int port) {
    	super("02"+player+data, ipAddress, port);
    }
}
