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
	public static final int LOADINGSCREEN = 4;

	public GameStateManager(Handlers handler) {
		gameState = new GameState[SUM_LEVEL];
		this.handler = handler;
		currentState = MENUSTATE;
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
		if (state == MENUSTATE) {
			System.out.println("LOADING MENU STATE");
			gameState[state] = new MenuState(handler);
		} else if (state == PLAYSTATE) {
			System.out.println("LOADING PLAY STATE");
			gameState[state] = new PlayState(handler);
		} else if (state == LOADINGSCREEN) {
			System.out.println("LOADINGSREEN..");
			gameState[state] = new LoadingState(handler);
		}
	}

	public void update() {
		if (gameState[currentState] != null) {
			gameState[currentState].update();
		}

	}

	public void draw(Graphics2D g) {
		if (gameState[currentState] != null) {

			gameState[currentState].draw(g);
		}
	}

	// GETTER SETTER
	public int getCurrentState() {
		return currentState;
	}

}
