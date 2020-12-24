package TileMap;

import java.awt.image.BufferedImage;

import Assets.Assets;
import Entities.Entity;
import Handlers.Handlers;


public class WhiteBlock extends Tile {
	
	private static final int WIDTH = 159;
	private static final int HEIGHT = 16;
	
	private boolean touched;
	
	public WhiteBlock(Handlers handler,int type, int x, int y) {
		super(handler, type, Assets.whiteBlock, x, y, WIDTH, HEIGHT); 
		touched = false;
		this.bounds.x = 15;
		this.bounds.width -= this.bounds.x*2;
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
				setTexture(Assets.blueBlock);
				solid = true;
			}
			else {
				setTexture(Assets.pinkBlock);
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
		setTexture(Assets.whiteBlock);
	}
	
	@Override
	public int getType() {
		return Tile.WHITEBLOCK;
	}
	
	
}
