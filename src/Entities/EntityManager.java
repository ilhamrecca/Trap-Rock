package Entities;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Handlers.Handlers;
import Server.Packet01Move;
import TileMap.Tile;

public class EntityManager {
	
	private Player player, player2;
	private ArrayList<Entity> entities;
	private Tile finishTile;
	private boolean player1OnFinishTile, player2OnFinishTile;  
	private Handlers handler;
	public EntityManager(Handlers handler, Player player, Player player2) {
		this.handler = handler;
		this.player = player;
		this.player2 = player2;
		entities = new ArrayList<Entity>();
		add(player);
		add(player2);
	}
	
	public void add(Entity e) {
		entities.add(e);
	}
	
	public void update() {
		for(int i = 0; i<entities.size(); i++) {
			Entity e = entities.get(i);
			if(handler.getPlayer().equals("1") && e.equals(player)) {
				String x = Double.toString(player.getX());
				String y = Double.toString(player.getY());
				
				Packet01Move packet = new Packet01Move("1"+x+","+y);
				packet.writeData(handler.getClient());
			}
//			else if(handler.getPlayer().equals("1") && e.equals(player2)) {
//				continue;
//			}
			else if(handler.getPlayer().equals("2") && e.equals(player2)) {
				String x = Double.toString(player2.getX());
				String y = Double.toString(player2.getY());
				
				Packet01Move packet = new Packet01Move("2"+x+","+y);
				packet.writeData(handler.getClient());
			}
//			else if(handler.getPlayer().equals("2") && e.equals(player)) {
//				continue;
//			}
			e.update();
		}
	}
	
	public void draw(Graphics2D g) {
		for(Entity e: entities) {
			if(e.equals(player))
				continue;
			e.draw(g);
		}
		player.draw(g);
		player2.draw(g);
	}
	
	
	
	public void reset() {
		for(Entity e: entities) {
			if(e.equals(player) || e.equals(player2))
				continue;
			e.reset();
		}
	}
	
	// SETTER GETTER
	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Tile getFinishTile() {
		return finishTile;
	}

	public void setFinishTile(Tile finishTile) {
		this.finishTile = finishTile;
		add(finishTile);
	}

	public boolean isPlayer1OnFinishTile() {
		return player1OnFinishTile;
	}

	public void setPlayer1OnFinishTile(boolean player1OnFinishTile) {
		this.player1OnFinishTile = player1OnFinishTile;
	}

	public boolean isPlayer2OnFinishTile() {
		return player2OnFinishTile;
	}

	public void setPlayer2OnFinishTile(boolean player2OnFinishTile) {
		this.player2OnFinishTile = player2OnFinishTile;
	}

	
	 
}
