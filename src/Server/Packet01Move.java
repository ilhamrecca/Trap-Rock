package Server;

import java.net.InetAddress;

public class Packet01Move extends Packet {
	
	Double x, y;
	String fromWho;
	public Packet01Move(String data) {
		super("01"+data);
//		data = data.substring(2);
		this.fromWho = data.substring(0,1);
//		System.out.println("data from who : " + fromWho);
		data = data.substring(1);
//		System.out.println("data that will be sent : "+ data);
		String[] position = data.split(",");
		this.x = Double.valueOf(position[0]);
		this.y = Double.valueOf(position[1]);
		
	}
	
	public Packet01Move(String data, InetAddress ipAddress, int port) {
		super("01"+data, ipAddress, port);
//		data = data.substring(2);
		this.fromWho = data.substring(0,1);
//		System.out.println("data from who : " + fromWho);
		data = data.substring(1);
//		System.out.println("data that will be sent : "+ data);
		String[] position = data.split(",");
		this.x = Double.valueOf(position[0]);
		this.y = Double.valueOf(position[1]);
	}
	
}