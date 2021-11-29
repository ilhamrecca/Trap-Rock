package GameState;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import Assets.Assets;
import Client.Client;
import Handlers.AudioPlayer;
import Handlers.Handlers;
import Handlers.Mouse;
import Main.GamePanel;
import Server.Packet00Login;
import Server.Server;
import Utils.ImageLoader;

public class MenuState extends GameState {
	private BufferedImage bg;
	private Color titleColor;
//	private Font titleFont;
	private Font font;
	private float alpha;
	private float rate;
	private boolean selected;
//	private BufferedImage start;
	private int currentChoice = 0;
	private BufferedImage start, highscore, quit;

	private BufferedImage name;
	private int startXSize;
	private int startYSize;

	public MenuState(Handlers handler) {
		super(handler);
		this.start = ImageLoader.loadImage("Button/play.png");
		this.alpha = 0f;
		this.rate = 0.02f;
		startXSize = 150;
		startYSize = 144;
		selected = false;
		init();

	}

	@Override
	public void init() {
		titleColor = Color.WHITE;

		font = new Font("Arial", Font.PLAIN, 28);
		bg = ImageLoader.loadImage("Background/menuBG.png");
		name = ImageLoader.loadImage("Background/title.png");

		AudioPlayer.loop("menu", 600, AudioPlayer.getFrames("menu") - 44000);
	}

	@Override
	public void update() {
		handleInput();
		getCurrentChoice();
	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// draw from hiarr

		g.drawImage(bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g.setColor(Color.GREEN);
		g.setColor(titleColor);
		g.setFont(font);

		g.drawImage(name, (GamePanel.WIDTH / 2) - (682 / 2), (GamePanel.HEIGHT / 2) - 300, 682, 162, null);

		g.drawImage(start, (GamePanel.WIDTH / 2) - (startXSize / 2), (GamePanel.HEIGHT / 2) - (startYSize / 2) + 75,
				startXSize, startYSize, null);

		// to hiar

		alpha += rate;

		if (alpha >= 1.0f) {
			alpha = 1.0f;
		} else if (alpha <= 0f && rate < 0f && selected) {

			int choice = handler.getDisplay().startServer();
			Packet00Login packetLogin;
			Client client = null;
			switch (choice) {
			case JOptionPane.YES_OPTION:

				handler.setPlayer("1");

				try {

					handler.setServer(new Server(handler));

				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}
				handler.getServer().start();
				select();
				handler.getDisplay().waitingPlayer2();

				break;
			case JOptionPane.NO_OPTION:
				System.out.println("you selected No");
				String serverAddress = handler.getDisplay().selectServer();
				System.out.println("server address = " + serverAddress);

				try {
					client = new Client(handler, serverAddress);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				handler.setClient(client);
				handler.getClient().start();

				handler.setPlayer("2");
				try {
					client = new Client(handler, serverAddress);
				} catch (IOException e) {
					e.printStackTrace();
				}

				select();
				handler.getGsm().setState(GameStateManager.PLAYSTATE);
				break;
			case JOptionPane.CANCEL_OPTION:
				handler.getGsm().setState(GameStateManager.MENUSTATE);
			}

		}

	}

	private void select() {
		AudioPlayer.stop("menu");
	}

	@Override
	public void handleInput() {

		if (Mouse.isPressed(Mouse.LEFT)) {

			currentChoice = checkHoverMouse(Mouse.getMouseX(), Mouse.getMouseY());
			System.out.println(currentChoice);
			if (currentChoice != -1) {
				if (currentChoice == 1) {
					if (!selected) {
						startXSize = 150;
						startYSize = 144;
						rate = -rate;
						selected = true;
					}
				}

			}
		}
	}

	private Rectangle getCollisionBound(int x, int y, int width, int height) {
		return new Rectangle(x, y, width, height);
	}

	private int checkHoverMouse(int x, int y) {

		if (getCollisionBound((GamePanel.WIDTH / 2) - (startXSize / 2),
				(GamePanel.HEIGHT / 2) - (startYSize / 2) + (75), startXSize, startYSize)
						.contains(getCollisionBound(x, y, 1, 1)))
			return 1;

		return -1;

	}

	private void getCurrentChoice() {
		int choice = checkHoverMouse(Mouse.getMouseX(), Mouse.getMouseY());
		// System.out.println(choice);
		if (choice != -1) {

			if (choice == 1 && !selected) {
				startXSize = 190;
				startYSize = 183;
			}
		} else if (!selected) {
			startXSize = 150;
			startYSize = 144;

		}

	}

}
