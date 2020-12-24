package TileMap;


import Assets.Assets;
import Handlers.Handlers;


public class LongWoodBlock extends Tile{

	private static final int WIDTH = 241;
	private static final int HEIGHT = 16;
	public LongWoodBlock(Handlers handler,int type, int x, int y) {
		super(handler, type, Assets.longWoodBlock, x, y, WIDTH, HEIGHT);
		
		this.bounds.x = 15;
		this.bounds.width -= this.bounds.x*2;
		//this.height -= this.bound.y;
		//solid = false;
	}
	
	@Override
	public int getType() {
		return Tile.LONGWOODBLOCK;
	}

}
