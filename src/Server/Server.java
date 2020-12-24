package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import Handlers.Handlers;



public class Server extends Thread {

	private DatagramSocket socket;
	private Handlers handler;
	public static final String INVALID = "03", LOGIN = "00", MOVE = "01", ENDGAME= "02";
	boolean play1 = false, play2 = false;
	String player1Name = null, player2Name = null, comp = null, time = null;
	InetAddress player1Address, player2Address; 
	int player1Port, player2Port;
	public Server(Handlers handler) {
		this.handler = handler;
		try {
			this.socket = new DatagramSocket(1331);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	InetAddress player1, player2;

	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
//			System.out.println("Client "+ packet.getAddress()+ " => says "+ new String(packet.getData(), StandardCharsets.UTF_8));
			String packetData = new String(packet.getData(), StandardCharsets.UTF_8);
			
			String packetType = packetData.substring(0,2);
			String player = packetData.substring(2,3);
//			System.out.println("skdjlsd" + player);
			
			
			switch(packetType) {
				case LOGIN:
					if(player1Address == null) {
						player1Address = packet.getAddress();
						player1Port = packet.getPort();
						Packet00Login loginPacket = new Packet00Login("", player, player1Address, player1Port);
						loginPacket.writeData(this);
					}
					else {
						player2Address = packet.getAddress();
						player2Port = packet.getPort();
						Packet00Login loginPacket = new Packet00Login("", player, player2Address, player2Port);
						Packet00Login loginPacket2 = new Packet00Login("", player, player1Address, player1Port);
						loginPacket.writeData(this);
						loginPacket2.writeData(this);
					}
					
					break;
				case MOVE:
//					System.out.println("player o server : " + player);
					if(player.equalsIgnoreCase("1")) {
						packetData = packetData.substring(2);
						Packet01Move movePacket = new Packet01Move(packetData, player2Address, player2Port);
//						System.out.println("packet on server : " + packetData);
						movePacket.writeData(this);
					}
					else if(player.equalsIgnoreCase("2")) {
						packetData = packetData.substring(2);
						Packet01Move movePacket = new Packet01Move(packetData, player1Address, player1Port);
//						System.out.println("packet on server : " + packetData);
						movePacket.writeData(this);
					}
					break;
				case ENDGAME:
					System.out.println("player submit hisname"+player);
					packetData = packetData.substring(3);
					packetData = packetData.trim();
					if(player.equals("1")) {
						System.out.println("player 1 submitted");
						String[] hasil = packetData.split(",");
						player1Name = hasil[0];
						time = hasil[1];
						comp = hasil[2];
						play1 = true;
						
					}else if(player.equals("2")) {
						System.out.println("player 2 submitted");
						player2Name = packetData.trim();
						play2 = true;
					}
					boolean temp = play1 && play2;
					System.out.println("masukan ke database ? : " + temp);
					if(play1 && play2) {
						System.out.println("masukan ke dataabse");
						handler.getDatabase().addData(player1Name, player2Name, time, comp);
					}
					
			}
//			this.sendData(("pong").getBytes(), packet.getAddress(), packet.getPort());
//			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) {

		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			this.socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendDataToAllClients(byte[] data) {
		
	}

}