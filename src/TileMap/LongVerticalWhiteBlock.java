package TileMap;

import java.awt.image.BufferedImage;

import Assets.Assets;
import Entities.Entity;
import Handlers.Handlers;
import Utils.ImageLoader;

public class LongVerticalWhiteBlock extends Tile {
	private static final int WIDTH = 241;
	private static final int HEIGHT = 16;
	private BufferedImage rotateWhite;
	private BufferedImage rotateBlue;
	private BufferedImage rotatePink;
	private boolean touched;
	public LongVerticalWhiteBlock(Handlers handler, int type, int x, int y) {
		super(handler, type, Assets.woodBlock, x, y, HEIGHT, WIDTH);
		
		rotateWhite = ImageLoader.rotateCw(Assets.longWhiteBlock);
		
		this.texture = rotateWhite;
		
		rotatePink = ImageLoader.rotateCw(Assets.longPinkBlock);
		rotateBlue = ImageLoader.rotateCw(Assets.longBlueBlock);
		
		touched = false;
//		this.bounds.x = 15;
//		this.bounds.width -= this.bounds.x * 2;
		// this.height -= this.bound.y;
		// solid = false;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	@Override
	public boolean isSolid(Entity player) {
		if(!touched) {
			touched = true;
			if(player.equals(handler.getEntityManager().getPlayer())) {
				setTexture(rotateBlue);
				solid = true;
			}
			else {
				setTexture(rotatePink);
				solid = false;
			}
		}
		if(player.equals(handler.getEntityManager().getPlayer()))
			return solid;
		else {
			return !solid;
		}
		
		
	}
	
	@Override
	public void reset() {
		touched = false;
		solid = true;
		setTexture(rotateWhite);
	}
	
	@Override
	public int getType() {
		return Tile.LONGVERTICALWHITEBLOCK;
	}
}
