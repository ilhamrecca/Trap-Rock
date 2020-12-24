package Handlers;

import Client.Client;
import Entities.Entity;
import Entities.EntityManager;
import GameState.GameStateManager;
import GameState.PlayState;
import Level.Level;
import Main.Display;
import Server.Server;
import TileMap.WhiteBlock;
import Utils.Database;

public class Handlers {
	private GameStateManager gsm;
	private Level level;
	private PlayState playState;
	private WhiteBlock tile;
	private Display display;
	Database database;
	private Client client;
	private Server server;
	private String player;
	
	
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

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
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

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public EntityManager getEntityManager() {
		return getPlayState().getEntityManager();
	}

	public WhiteBlock getTile() {
		return tile;
	}

	public void setTile(Entity e) {
		this.tile = (WhiteBlock) e;
	}

	
	
	
}
