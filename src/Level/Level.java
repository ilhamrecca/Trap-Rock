package Level;

import java.io.IOException;

//import java.awt.Graphics2D;

import Entities.EntityManager;
import Handlers.Handlers;
import TileMap.LongVerticalWhiteBlock;
import TileMap.LongVerticalWoodBlock;
import TileMap.LongWhiteBlock;
import TileMap.LongWoodBlock;
import TileMap.Tile;
import TileMap.TimeBlock;
import TileMap.VerticalWhiteBlock;
import TileMap.VerticalWoodBlock;
import TileMap.WhiteBlock;
import TileMap.WoodBlock;
import Utils.Parser;

public class Level {
	private int spawnXP1, spawnYP1;
	private int spawnXP2, spawnYP2;
	private int finishTileX, finishTileY, finishTileType;
	
	/*
	 * private int width = GamePanel.WIDTH/10; private int height =
	 * GamePanel.HEIGHT/10;
	 */
	
	private int[] map;
	private int size;
	EntityManager entityManager;
	Handlers handler;
	public Level(Handlers handler, String path) throws IOException {
		this.handler = handler;
		loadWorld(path);
		
	}
	
//	public void update() {
//		
//	}
//	
//	public void draw(Graphics2D g) {
//		
//	}
	
	public void populateWorld() {
		int xpos = 0;
		int ypos = 0;
		for(int x = 0; x<map.length; x++) {
			if(x % 3==0) {
				xpos = map[x];
			}
			else if(x % 3 ==1) {
				ypos = map[x];
			}
			else {
				handler.getEntityManager().add(CreateTiles(map[x], xpos, ypos));
			}
		}
	}
	
	public Tile CreateTiles(int id, int x, int y) {
		if(id == Tile.WOODBLOCK) {
			return new WoodBlock(handler,id, x, y);
		}
		else if(id == Tile.WHITEBLOCK) {
			return new WhiteBlock(handler,id, x, y);
		}
		else if(id == Tile.TIMEBLOCK) {
			return new TimeBlock(handler,id, x, y);
		}
		else if(id == Tile.LONGWHITEBLOCK) {
			return new LongWhiteBlock(handler,id, x, y);
		}
		else if(id == Tile.LONGWOODBLOCK) {
			return new LongWoodBlock(handler,id, x, y);
		}
		else if(id == Tile.VERTICALWOODBLOCK) {
			return new VerticalWoodBlock(handler,id, x, y);
		}
		else if(id == Tile.LONGVERTICALWOODBLOCK) {
			return new LongVerticalWoodBlock(handler,id, x, y);
		}
		else if(id == Tile.VERTICALWHITEBLOCK) {
			return new VerticalWhiteBlock(handler,id, x, y);
		}
		else if(id == Tile.LONGVERTICALWHITEBLOCK) {
			return new LongVerticalWhiteBlock(handler,id, x, y);
		}
		
		return new WoodBlock(handler,id, x, y);
	}
	
	/*
	 * public Tile getTileType(int x) { Tile t = Tile.tiles[x];
	 * 
	 * return t; }
	 */
	
	
	private void loadWorld (String path) throws IOException {
		String file = Parser.loadFileAsString(path);
		
		String[] grid = file.split("\\s+");
		spawnXP1 = Parser.parseInt(grid[0]);
		spawnYP1 = Parser.parseInt(grid[1]);
		spawnXP2 = Parser.parseInt(grid[2]);
		spawnYP2 = Parser.parseInt(grid[3]);
		
		finishTileX = Parser.parseInt(grid[4]);
		finishTileY = Parser.parseInt(grid[5]);
		finishTileType = Parser.parseInt(grid[6]);
		
		size = Parser.parseInt(grid[7]);
		
		map = new int[size*3];
		for(int x = 0; x < map.length; x++) {
			map[x] = Parser.parseInt(grid[x+8]);
		}
		
	}
	
	public void setEntityManager(EntityManager e) {
		this.entityManager = e;
	}

	public int getSpawnXP1() {
		return spawnXP1;
	}

	public void setSpawnXP1(int spawnXP1) {
		this.spawnXP1 = spawnXP1;
	}

	public int getSpawnYP1() {
		return spawnYP1;
	}

	public void setSpawnYP1(int spawnYP1) {
		this.spawnYP1 = spawnYP1;
	}

	public int getSpawnXP2() {
		return spawnXP2;
	}

	public void setSpawnXP2(int spawnXP2) {
		this.spawnXP2 = spawnXP2;
	}

	public int getSpawnYP2() {
		return spawnYP2;
	}

	public void setSpawnYP2(int spawnYP2) {
		this.spawnYP2 = spawnYP2;
	}

	public int getFinishTileX() {
		return finishTileX;
	}

	public void setFinishTileX(int finishTileX) {
		this.finishTileX = finishTileX;
	}

	public int getFinishTileY() {
		return finishTileY;
	}

	public void setFinishTileY(int finishTileY) {
		this.finishTileY = finishTileY;
	}

	public int getFinishTileType() {
		return finishTileType;
	}

	public void setFinishTileType(int finishTileType) {
		this.finishTileType = finishTileType;
	}

}	
