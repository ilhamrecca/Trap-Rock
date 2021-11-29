package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import GameState.GameStateManager;
import Handlers.Handlers;

public class Server extends Thread {

	private ServerSocket socket;
	private Socket server;
	private Handlers handler;
	public static final String INVALID = "03", LOGIN = "00", MOVE = "01", ENDGAME = "02";
	boolean play1 = false, play2 = false;
	String player1Name = null, player2Name = null, comp = null, time = null;
	InetAddress player1Address, player2Address;
	int player1Port, player2Port;
	private BufferedReader buffer;
	private InputStreamReader input;
	private PrintWriter pr;

	public Server(Handlers handler) throws UnknownHostException, IOException {
		this.handler = handler;
		try {
			this.socket = new ServerSocket(1331);

		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	InetAddress player1, player2;

	public void run() {
		try {
			Socket server = socket.accept();
			input = new InputStreamReader(server.getInputStream());
			buffer = new BufferedReader(input);

			
			pr = new PrintWriter(server.getOutputStream());
//			handler.getDisplay().player2Connected();
			handler.getGsm().setState(GameStateManager.PLAYSTATE);
			System.out.println("Client Connnected");
		} catch (IOException e) {
			System.out.println("SocketServer Closed");
		}

		while (true) {
			String data = "";
			try {
				if (buffer != null)
					data = buffer.readLine();

			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("SocketServer Closed");
			}
			if (data != null && data.length() > 1) {
				String packet = data.substring(0, 2);
				switch (packet) {
				case "01":
					int where = Integer.parseInt(data.substring(2, 3));
					int to = Integer.parseInt(data.substring(3, 4));
					handler.getGameView().move(where, to);
					break;

				case "00":
					if (handler.getGsm().getCurrentState() == GameStateManager.LOADINGSCREEN) {
						handler.getGsm().setState(GameStateManager.PLAYSTATE);
						Packet00Login packet1 = new Packet00Login("");

						try {
							packet1.writeData(handler.getServer());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					break;
				}
			}

		}

	}

	public void sendData(String data) throws IOException {
		pr.println(data);
		pr.flush();
	}

	public void close() {
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}