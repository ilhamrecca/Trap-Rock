package GameState;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Assets.Assets;
import Entities.EntityManager;
import Entities.HUD;
import Entities.Player;
import Handlers.Handlers;
import Handlers.Keys;
import Level.Level;
import Main.GamePanel;
import Server.Packet02EndGame;
import Utils.ImageLoader;

public class PlayState extends GameState {

	// testing opacity
	private float alpha;

	private Level level1;
	private HUD hud;
	private BufferedImage test;
	private EntityManager entityManager;
	private int currentLevel;
	private String level;
	private int lastLevel;
	private boolean finished;

	private long timer;
	private long lastTime;

	private float rate;

	public PlayState(Handlers handler) {
		super(handler);
		this.timer = 0;
		this.lastTime = 0;

		this.lastLevel = 1;
		this.currentLevel = 1;
		this.level = "Level/level";
		this.rate = 0.02f;
		hud = new HUD(handler);
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
		test = ImageLoader.loadImage("Background/entah1.jpg");
		System.out.println("LOADING " + level + currentLevel + ".txt");
		try {
			level1 = new Level(handler, level + currentLevel + ".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.setLevel(level1);
		entityManager = new EntityManager(handler,
				(new Player(handler, level1.getSpawnXP1(), level1.getSpawnYP1(), Player.size, Player.size)),
				(new Player(handler, level1.getSpawnXP2(), level1.getSpawnYP2(), Player.size, Player.size)));

		entityManager.setFinishTile(
				level1.CreateTiles(level1.getFinishTileType(), level1.getFinishTileX(), level1.getFinishTileY()));
		entityManager.getPlayer2().setTexture(Assets.player2);
		level1.populateWorld();

		this.alpha = 0;

		
		


	}

	@Override
	public void update() {
		// level1.update();

		handleInput();
		entityManager.update();
		isFinished();
	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		// set the opacity
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// do the drawing here

		g.drawImage(test, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);

		hud.draw(g);

		entityManager.draw(g);
		// increase the opacity and repaint
		alpha += rate;
		if (alpha >= 1.0f) {
			alpha = 1.0f;
		}
	}

//	@Override
//	public void draw(Graphics2D g) {
//		//g.setColor(Color.BLACK);
//		//g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
//		g.setColor(Color.WHITE);
//		
//		g.drawImage(test, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT,null);
//		
////		level1.draw(g);
//		
//		entityManager.draw(g);
//		
//	}

	private void isFinished() {

		if (entityManager.isPlayer1OnFinishTile() && entityManager.isPlayer2OnFinishTile()) {
			finished = true;
//			handler.getDisplay().setSecond(0);
//			handler.getDisplay().setMinute(0);
//			handler.getDisplay().setPause(true);
//			handler.getDisplay().getTimer().cancel(true);
			if (currentLevel < lastLevel) {
				if (timer > 2000000) {
					currentLevel++;
					handler.getEntityManager().getEntities().clear();
					this.init();
				}
			}

			else {
				String playerName = handler.getDisplay().endGame();
				if(handler.getPlayer().equals("1")) {
					int second = handler.getDisplay().getSecond();
					int minutes = handler.getDisplay().getMinute();
					String comp = "";
					if(minutes < 2) {
						comp = "Bucin Sejati";
					}
					else if(minutes == 2){
						comp = "Bucin Mainstream";
					}
					else if(minutes == 3){
						comp = "Bucin Noob";
					}
					else if(minutes == 4) {
						comp = "Terancam Putus";
					}
					else if(minutes > 4) {
						comp = "Katakan Putus";
					}
					
					String hasil = second < 10 ? minutes + ":0" + second : minutes + ":"+ second;
					Packet02EndGame endGame = new Packet02EndGame(handler.getPlayer()+playerName+","+hasil+","+comp);
					endGame.writeData(handler.getClient());
				}
				else if (handler.getPlayer().equals("2")) {
					Packet02EndGame endGame = new Packet02EndGame(handler.getPlayer()+playerName);
					endGame.writeData(handler.getClient());
				}
				
				handler.getDisplay().getTimer().cancel(true);
				handler.getDisplay().setMinute(0);
				handler.getDisplay().setSecond(0);
				handler.getGsm().setState(GameStateManager.MENUSTATE);
			}
			timer += System.nanoTime() - lastTime;
			lastTime = System.nanoTime();
			System.out.println(timer);
		} else {
			timer = 0;
			lastTime = 0;
			finished = false;
		}
	}

	@Override
	public void handleInput() {
		if (!finished) {
			if(handler.getPlayer().equals("1")) {
				entityManager.getPlayer().setLeft(Keys.keyState[Keys.LEFT]);
				entityManager.getPlayer().setRight(Keys.keyState[Keys.RIGHT]);
				entityManager.getPlayer().setDown(Keys.keyState[Keys.DOWN]);
				entityManager.getPlayer().setJumping(Keys.keyState[Keys.UP]);
			}
			else if(handler.getPlayer().equals("2")){
				entityManager.getPlayer2().setLeft(Keys.keyState[Keys.LEFT]);
				entityManager.getPlayer2().setRight(Keys.keyState[Keys.RIGHT]);
				entityManager.getPlayer2().setDown(Keys.keyState[Keys.DOWN]);
				entityManager.getPlayer2().setJumping(Keys.keyState[Keys.UP]);
			}
//			entityManager.getPlayer2().setLeft(Keys.keyState[Keys.LEFT_PLAYER2]);
//			entityManager.getPlayer2().setRight(Keys.keyState[Keys.RIGHT_PLAYER2]);
//			entityManager.getPlayer2().setDown(Keys.keyState[Keys.DOWN_PLAYER2]);
//			entityManager.getPlayer2().setJumping(Keys.keyState[Keys.UP_PLAYER2]);
		}
	}

	// GETTER SETTER
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Level getLevel1() {
		return level1;
	}

}
