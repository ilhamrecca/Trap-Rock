package GameState;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import Assets.Assets;
import Client.Client;
import Handlers.AudioPlayer;
import Handlers.Handlers;
import Handlers.Keys;
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

	private int currentChoice = 0;
	private BufferedImage start, highscore, quit;

	private BufferedImage name;

	public MenuState(Handlers handler) {
		super(handler);
		this.start = Assets.start;
		this.quit = Assets.quit;
		this.highscore = Assets.highscore;
		this.alpha = 0f;
		this.rate = 0.02f;
		selected = false;
		init();

	}

	@Override
	public void init() {
		titleColor = Color.WHITE;

		font = new Font("Arial", Font.PLAIN, 28);
		bg = ImageLoader.loadImage("Background/kami.png");
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

//		g.setColor(titleColor);
//		g.setFont(titleFont);
//		g.drawString("B U C I N  G A M E", 250, 300);

		g.drawImage(name, (GamePanel.WIDTH / 2) - (682 / 2), (GamePanel.HEIGHT / 2) - 300, 682, 162, null);

		g.drawImage(start, (GamePanel.WIDTH / 2) - (300 / 2), (GamePanel.HEIGHT / 2) - (50 / 2) + 75, 300, 50, null);
		g.drawImage(highscore, (GamePanel.WIDTH / 2) - (300 / 2), (GamePanel.HEIGHT / 2) - (50 / 2) + 150, 300, 50,
				null);
		g.drawImage(quit, (GamePanel.WIDTH / 2) - (300 / 2), (GamePanel.HEIGHT / 2) - (50 / 2) + 225, 300, 50, null);

		// to hiar

		alpha += rate;

		if (alpha >= 1.0f) {
			alpha = 1.0f;
		} else if (alpha <= 0f && rate < 0f && selected) {
//			handler.getClient().start();
//			handler.getClient().sendData(("Ping").getBytes());
			
			int choice = handler.getDisplay().startServer();
			Packet00Login packetLogin;
			Client client;
			switch (choice) {
			case JOptionPane.YES_OPTION:
				handler.setServer(new Server(handler));
				handler.getServer().start();
				client = new Client(handler, "localhost");
				handler.setClient(client);
				handler.getClient().start();
				packetLogin = new Packet00Login("1");
				handler.setPlayer("1");
				packetLogin.writeData(handler.getClient());
				handler.getDisplay().waitingPlayer2();
				select();
				break;
			case JOptionPane.NO_OPTION:
				System.out.println("you selected No");
				String serverAddress = handler.getDisplay().selectServer();
				System.out.println("server address = " + serverAddress);
				client = new Client(handler, serverAddress);
				handler.setClient(client);
				handler.getClient().start();
				packetLogin = new Packet00Login("2");
				handler.setPlayer("2");
				packetLogin.writeData(handler.getClient());
				
				select();
				break;
			case JOptionPane.CANCEL_OPTION:

			}

		}

	}

	private void select() {
		AudioPlayer.stop("menu");
		AudioPlayer.load("Music/jump.mp3", "jump");
		// AudioPlayer.load("Music/fall.mp3", "fall");
		handler.getGsm().setState(GameStateManager.PLAYSTATE);
	}

	@Override
	public void handleInput() {
//		if (Keys.isPressed(Keys.ENTER)) {
//			
//		}
		if (Mouse.isPressed(Mouse.LEFT)) {

			currentChoice = checkHoverMouse(Mouse.getMouseX(), Mouse.getMouseY());
			System.out.println(currentChoice);
			if (currentChoice != -1) {
				if (currentChoice == 1) {
					if (!selected) {
						start = Assets.startC;
						rate = -rate;
						selected = true;
					}
				} else if (currentChoice == 2) {
					highscore = Assets.highscoreC;
					selected = true;
					handler.getDisplay().highscore();
					selected = false;
				} else if (currentChoice == 3) {
					selected = true;
					quit = Assets.quitC;
					System.exit(0);
				}
			}

		}

		if (Keys.isHold(Keys.CTRL)) {
			if (Keys.isPressed(Keys.UP_PLAYER2)) {
				handler.getGsm().setState(GameStateManager.LEVELCREATION);
				AudioPlayer.stop("menu");
			}
		}

	}

	private Rectangle getCollisionBound(int x, int y, int width, int height) {
		return new Rectangle(x, y, width, height);
	}

	private int checkHoverMouse(int x, int y) {
		for (int i = 1; i <= 3; i++) {

			if (getCollisionBound((GamePanel.WIDTH / 2) - (300 / 2), (GamePanel.HEIGHT / 2) - (50 / 2) + (75 * i), 300,
					50).contains(getCollisionBound(x, y, 1, 1)))
				return i;
		}
		return -1;

	}

	private void getCurrentChoice() {
		int choice = checkHoverMouse(Mouse.getMouseX(), Mouse.getMouseY());
		// System.out.println(choice);
		if (choice != -1) {

			if (choice == 1 && !selected) {
				this.start = Assets.startH;
			} else if (choice == 2 && !selected) {
				this.highscore = Assets.highscoreH;
			} else if (choice == 3 && !selected) {
				this.quit = Assets.quitH;
			}
		} else if (!selected) {
			start = Assets.start;
			highscore = Assets.highscore;
			quit = Assets.quit;
		}

	}

}
