package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import GameState.GameStateManager;
import Handlers.Handlers;
import Server.Packet00Login;
import Server.Packet01Move;

public class Client extends Thread {

	private String ipAddress;
	private Socket socket;
	private Handlers handler;
	private BufferedReader buffer;
	private InputStreamReader input;
	public static final String INVALID = "03", LOGIN = "00", MOVE = "01", ENDGAME = "02";

	public Client(Handlers handler, String ipAddress) throws IOException {
		this.handler = handler;
		try {
			System.out.println(ipAddress);
			socket = new Socket(ipAddress, 1331);

		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			input = new InputStreamReader(socket.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		buffer = new BufferedReader(input);
		while (true) {

			String data = "";
			try {
				data = buffer.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
			if (data.compareTo("") == 0) {
				System.out.println("dasljkflkasfjlksjkfasdjkl;fdasjkl;f");
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
							packet1.writeData(handler.getClient());
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
		PrintWriter pr = new PrintWriter(socket.getOutputStream());
		pr.println(data);
		pr.flush();
	}

}