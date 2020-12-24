package GameState;


import java.awt.Graphics2D;

import Handlers.Handlers;


public class GameStateManager {
	private GameState[] gameState;
	private int currentState;
	private Handlers handler;
	public static final int SUM_LEVEL = 10;
	public static final int MENUSTATE = 0;
	public static final int PLAYSTATE = 1;
	public static final int LEVELCREATION = 2;
	public static final int SPLASHSCREEN = 3;
	
	
	public GameStateManager(Handlers handler) {
		gameState = new GameState[SUM_LEVEL];
		this.handler = handler;
		currentState = SPLASHSCREEN;
		handler.setGsm(this);
		loadState(currentState);
		
	}
	
	private void unloadState(int state) {
		gameState[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE) {
			System.out.println("LOADING MENU STATE");
			gameState[state] = new MenuState(handler);
		}
		else if(state == PLAYSTATE) {
			System.out.println("LOADING PLAY STATE");
			gameState[state] = new PlayState(handler);
		}
		else if(state == LEVELCREATION) {
			System.out.println("LOADING LEVEL CREATION STATE");
			gameState[state] = new LevelCreation(handler);
		}
		else if(state == SPLASHSCREEN) {
			System.out.println("LOADING SPLASHSCREEN..");
			gameState[state] = new SplashScreen(handler);
		}
	}
	
	public void update() {
		gameState[currentState].update();
	}
	
	public void draw(Graphics2D g) {
		gameState[currentState].draw(g);
	}
	
	//GETTER SETTER
	public int getCurrentState() {
		return currentState;
	}
	

	
	
}
