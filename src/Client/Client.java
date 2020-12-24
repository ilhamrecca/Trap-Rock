package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import Handlers.Handlers;
import Server.Packet00Login;

public class Client extends Thread {

	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Handlers handler;
	public static final String INVALID = "03", LOGIN = "00", MOVE = "01", ENDGAME= "02";
	public Client(Handlers handler, String ipAddress) {
		this.handler = handler;
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
//			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
//			System.out.println("Server "+ packet.getAddress()+ " => says "+ new String(packet.getData(), StandardCharsets.UTF_8));
			String packetData = new String(packet.getData(), StandardCharsets.UTF_8);
//			System.out.println(packetData);
			String packetType = packetData.substring(0,2);
			String player = packetData.substring(2,3);
//			System.out.println("packet type : " + packetType);
			switch(packetType) {
				case LOGIN:
					player = player.trim(); 
					if(handler.getPlayer().equalsIgnoreCase(player)) {
						System.out.println("You're Player "+player+"dfss");
					}
					if(player.equalsIgnoreCase("2")) {
						if(!handler.getPlayer().equalsIgnoreCase(player)) {
							System.out.println("removing dialog pane");
							handler.getDisplay().player2Connected();
							System.out.println("Player 2 has joined the game");
						}
					}
					break;
				case MOVE:
					packetData = packetData.substring(3);
					String[] position = packetData.split(",");
					double x = Double.valueOf(position[0]);
					double y = Double.valueOf(position[1]);
//					
					
					if(handler.getPlayer().equals("2") && player.equals("1")) {
						handler.getEntityManager().getPlayer().setX(x);
						handler.getEntityManager().getPlayer().setY(y);
					}
					else if(handler.getPlayer().equals("1") && player.equals("2")) {
						System.out.println("player 2 set" );
						System.out.println("position x player 2: " + x);
						System.out.println("position y player 2: " + y);
						if(handler.getEntityManager() != null) {
							if(handler.getEntityManager().getPlayer2() !=null) {
								handler.getEntityManager().getPlayer2().setX(x);
								handler.getEntityManager().getPlayer2().setY(y);
							}
							
						}
						
					}
					
			}
			
		}
	}

	public void sendData(byte[] data) {

		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}