package Handlers;

import Client.Client;

import GameState.GameStateManager;
import GameState.GameView;
import GameState.PlayState;

import Main.Display;
import Server.Server;

public class Handlers {
	private GameStateManager gsm;

	private PlayState playState;
	private Display display;
	private Client client;
	private Server server;
	private String player;
	private GameView gameView;
	
	
	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public PlayState getPlayState() {
		return playState;
	}

	public void setPlayState(PlayState playState) {
		this.playState = playState;
	}

	public Handlers(Display display) {
		this.display = display;
	}

	public GameStateManager getGsm() {
		return gsm;
	}

	public void setGsm(GameStateManager gsm) {
		this.gsm = gsm;
	}



	public GameView getGameView() {
		return gameView;
	}

	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}

	
	
	
}
