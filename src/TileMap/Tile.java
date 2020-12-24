package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Entities.Entity;
import Handlers.Handlers;

public class Tile extends Entity{
	
	public static final int WOODBLOCK = 0;
	public static final int WHITEBLOCK = 1;
	public static final int TIMEBLOCK = 2;
	public static final int LONGWHITEBLOCK = 3;
	public static final int LONGWOODBLOCK = 4;
	public static final int LONGBLUEBLOCK = 5;
	public static final int LONGPINKBLOCK = 6;
	public static final int VERTICALWOODBLOCK = 7;
	public static final int LONGVERTICALWOODBLOCK = 8;
	public static final int VERTICALWHITEBLOCK = 9;
	public static final int LONGVERTICALWHITEBLOCK = 10;
	
	
	//public int width, height;
	protected BufferedImage texture;
	protected final int TYPE;

	
	public Tile(Handlers handler, int type, BufferedImage texture, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
		this.texture = texture;
		this.TYPE = type;
//		this.width = width;
//		this.height = height;
		
		//bound = new Rectangle(0, 0, width, height);
		// no parameter stuff
		solid = true;
		//tiles[id] = this;
		
	}
	
	
	public void update() {
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(texture, (int) x, (int) y, width, height, null);
		
		//g.fillRect( (int) (x + bounds.x),(int) (y + bounds.y), this.bounds.width,this.bounds.height);
	}


	@Override
	public int getType() {
		return 0;
	}


	@Override
	public void reset() {
		
		
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
	public int getWidth() {
		return this.width;
	
	}
	
	public int getHeight() {
		return this.height;
	}
	
	
	
}
