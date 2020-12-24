package TileMap;

import java.awt.image.BufferedImage;

import Assets.Assets;
import Handlers.Handlers;
import Utils.ImageLoader;

public class LongVerticalWoodBlock extends Tile {
	private static final int WIDTH = 241;
	private static final int HEIGHT = 16;
	private BufferedImage rotateWood;
	public LongVerticalWoodBlock(Handlers handler, int type, int x, int y) {
		super(handler, type, Assets.woodBlock, x, y, HEIGHT, WIDTH);
		
		rotateWood = ImageLoader.rotateCw(Assets.longWoodBlock);
		
		this.texture = rotateWood;
		
//		this.bounds.x = 15;
//		this.bounds.width -= this.bounds.x * 2;
		// this.height -= this.bound.y;
		// solid = false;
	}

	@Override
	public int getType() {
		return Tile.LONGVERTICALWOODBLOCK;
	}
}
