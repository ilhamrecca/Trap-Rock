package GameState;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import Assets.Assets;
import Handlers.AudioPlayer;
import Handlers.Handlers;
import Handlers.Keys;
import Handlers.Mouse;
import Main.GamePanel;
import Utils.ImageLoader;

public class LoadingState extends GameState {

	private int counter = 0;
	float initialXSize;
	float initialYSize;
	private int frameTiming = 0;
	private int closeIconX = 243;
	private int closeIconY = 45;
	private int closeIconX1 = 243;
	private int closeIconY1 = 45;
	BufferedImage loadingText;
	BufferedImage closeIcon;
	BufferedImage closeIcon1;
	BufferedImage temp;

	public LoadingState(Handlers handler) {
		super(handler);
		init();
	}

	@Override
	public void init() {
		int counter = 0;
		initialXSize = 365f;
		initialYSize = 282f;

		loadingText = ImageLoader.loadImage("Loading/loading.png");
		closeIcon = ImageLoader.loadImage("Button/cancel.png");
		temp = closeIcon;

		closeIcon1 = ImageLoader.loadImage("Button/cancel2.png");
	}

	@Override
	public void update() {
		handleInput();
		if (counter < 195) {
			if (frameTiming == 2)
				counter += 1;
		} else
			counter = 0;
		if (frameTiming > 2) {
			frameTiming = 0;
		}
		getCurrentChoice();

	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.drawImage(Assets.frames[counter], (int) (GamePanel.WIDTH / 2 - initialXSize / 2),
				(int) (GamePanel.HEIGHT / 2 - initialYSize / 2), (int) (initialXSize), (int) (initialYSize), null);

		frameTiming += 1;
		g.drawImage(closeIcon, (int) (GamePanel.WIDTH / 2 - closeIconX / 2),
				(int) (GamePanel.HEIGHT / 2 - closeIconY / 2) + 250, (int) (closeIconX), (int) (closeIconY), null);
		g.drawImage(loadingText, (int) (GamePanel.WIDTH / 2 - 497 / 2), (int) (GamePanel.HEIGHT / 2 - 21 / 2) + 300,
				(int) (497), (int) (21), null);
	}

	@Override
	public void handleInput() {
		int currentChoice;
		if (Mouse.isPressed(Mouse.LEFT)) {

			currentChoice = checkHoverMouse(Mouse.getMouseX(), Mouse.getMouseY());
			System.out.println(currentChoice);
			if (currentChoice == 1) {
				System.out.println("You Choose Cancel");
				handler.getServer().close();
				handler.getServer().interrupt();
				
				
				handler.setServer(null);
				handler.getGsm().setState(GameStateManager.MENUSTATE);
			}

		}
	}

	private Rectangle getCollisionBound(int x, int y, int width, int height) {
		return new Rectangle(x, y, width, height);
	}

	private int checkHoverMouse(int x, int y) {

		if (getCollisionBound((GamePanel.WIDTH / 2 - closeIconX / 2),
				(int) (GamePanel.HEIGHT / 2 - closeIconY / 2) + 250, (int) (closeIconX), (int) (closeIconY))
						.contains(getCollisionBound(x, y, 1, 1)))
			return 1;

		return -1;

	}

	private void getCurrentChoice() {
		int choice = checkHoverMouse(Mouse.getMouseX(), Mouse.getMouseY());
		// System.out.println(choice);
		if (choice == 1) {
			closeIcon = closeIcon1;
			closeIconX = 243;
			closeIconY = 60;
		} else {
			closeIcon = temp;
			closeIconX = closeIconX1;
			closeIconY = closeIconY1;
		}
	}

}
