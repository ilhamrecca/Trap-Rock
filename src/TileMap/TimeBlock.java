package TileMap;

import java.awt.image.BufferedImage;

import Assets.Assets;
import Entities.Entity;
import Handlers.Handlers;

public class TimeBlock extends Tile {
	private static final int WIDTH = 159;
	private static final int HEIGHT = 16;
	private long lastTime;
	private long timer;
	
	private final int time = 2000;
	
	public TimeBlock(Handlers handler, int type, int x, int y) {
		super(handler, type, Assets.blueBlock, x, y, WIDTH, HEIGHT);
		timer = 0;
		this.bounds.x = 15;
		this.bounds.width -= this.bounds.x*2;
		lastTime = System.currentTimeMillis();
	}
	
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	@Override
	public void update() {
		
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		//System.out.println(timer);
		if(timer >time) {
			if( !( this.getCollisionBound(0, 0).intersects(handler.getEntityManager().getPlayer().getCollisionBound(0, 0) ) || this.getCollisionBound(0, 0).intersects(handler.getEntityManager().getPlayer2().getCollisionBound(0, 0)) ) ) {
				if(texture.equals(Assets.blueBlock)) {
					setTexture(Assets.pinkBlock);
					solid = false;
				}
				else {
					setTexture(Assets.blueBlock);
					solid = true;
				}
				timer = 0;
			}
		}
	}
	@Override
	public boolean isSolid(Entity player) {
		if(player.equals(handler.getEntityManager().getPlayer()))
			return solid;
		else {
			return !solid;
		}
	}
	@Override
	public int getType() {
		return Tile.TIMEBLOCK;
	}
	
}
