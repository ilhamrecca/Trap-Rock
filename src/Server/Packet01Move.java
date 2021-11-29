package Server;

import java.net.InetAddress;

public class Packet01Move extends Packet {
	
	int where, to;
	public Packet01Move(String data) {
		super("01"+data);
		where = Integer.parseInt(data.substring(0,1)); 
		to = Integer.parseInt(data.substring(1,2));
	}
	
}