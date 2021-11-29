package GameState;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Assets.Assets;

import Entities.HUD;
import Handlers.Handlers;
import Handlers.Keys;
import Main.GamePanel;
import Utils.ImageLoader;

public class PlayState extends GameState {

	// testing opacity
	private float alpha;
	private HUD hud;
	private BufferedImage test;
	private int currentLevel;
	private String level;
	private int lastLevel;
	private boolean finished;

	private long timer;
	private GameView gameView;
	private long lastTime;

	private float rate;
	private BufferedImage bg;
	public PlayState(Handlers handler) {
		super(handler);
		this.timer = 0;
		this.lastTime = 0;

		this.lastLevel = 3;
		this.currentLevel = 1;
		this.level = "Level/level";
		this.rate = 0.02f;
		hud = new HUD(handler);
		gameView = new GameView(handler);
		handler.setGameView(gameView);
		bg = ImageLoader.loadImage("Background/bg.png");
		
		handler.getDisplay().getTimer().cancel(true);
		handler.getDisplay().time();
		
		init();
	}

	@Override
	public void init() {

		this.timer = 0;
		this.lastTime = 0;

		finished = false;
		System.out.println(timer);
		handler.setPlayState(this);

		


		this.alpha = 0;
	}

	@Override
	public void update() {
		// level1.update();
		gameView.update();
		gameView.handleInput();
		
		gameView.isFinished();
//		handleInput();
//		entityManager.update();
//		isFinished();
	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		// set the opacity
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// do the drawing here

		g.drawImage(this.bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);

		hud.draw(g);
		
		gameView.draw(g);

//		// increase the opacity and repaint
		alpha += rate;
		if (alpha >= 1.0f) {
			alpha = 1.0f;
		}
	}



	



	@Override
	public void handleInput() {
		
		
	}

}
