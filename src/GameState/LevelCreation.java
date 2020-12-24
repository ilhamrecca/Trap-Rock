package GameState;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Assets.SpriteSheet;
import Entities.Entity;
import Entities.Player;
import Handlers.Handlers;
import Handlers.Keys;
import Handlers.Mouse;
import Main.GamePanel;
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
import Utils.ImageLoader;

public class LevelCreation extends GameState {

	private SpriteSheet sheet;
	private BufferedImage test;
	private BufferedImage[] tile;
	private ArrayList<Entity> temp;
	
	private int currentIndex;
	private Entity e;
	private int tempX, tempY;
	private int grid;
	private boolean draggingItem;
	
	public LevelCreation(Handlers handler) {
		super(handler);
		tile = new BufferedImage[11];
		temp = new ArrayList<Entity>();
		
		draggingItem = false;
		currentIndex = -1;
		init();
	}
	@Override
	public void init() {
		
		test = ImageLoader.loadImage("Background/entah1.jpg");
		sheet = new SpriteSheet(ImageLoader.loadImage("Object/picture4.png"));
		
		for(int i = 0; i<3; i++) {
			temp.add(null);
		}
		
		for(int i = 0; i<tile.length; i++) {
			tile[i] = sheet.crop(i*100, 21, 100, 100);
		}
		grid = 1;
	}

	@Override
	public void update() {
		
		handleInput();
		if(! temp.isEmpty() && draggingItem) {
			e = temp.get(currentIndex);
			e.setX( ( Mouse.getMouseX() / grid ) * grid - ( e.getWidth()/2 / grid ) * grid );
			e.setY( ( Mouse.getMouseY() / grid ) * grid - ( e.getHeight()/2 / grid ) * grid );
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		//g.drawRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		

		
		g.drawImage(test, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT,null);
		
		g.setStroke(new BasicStroke(2));
		if(grid != 1) {
			for(int i = 0; i< GamePanel.WIDTH/grid; i++) {
				g.draw(new Line2D.Float(i * grid, 85, i * grid, GamePanel.HEIGHT));
			}
			for(int i = 0 ; i<GamePanel.HEIGHT/grid; i ++) {
				g.draw(new Line2D.Float(0, i * grid + 85 , GamePanel.WIDTH, i * grid + 85));
			}
		}
		
		
		for(int i = 0; i<tile.length; i++) {
			g.drawImage(tile[i], GamePanel.WIDTH/2 - (tile.length-1)*25 + i*50, 25, 50, 50, null);
		}
		
		for(Entity e: temp) {
			if(e!=null)
				e.draw(g);
		}
	}

	@Override
	public void handleInput() {
		//System.out.println(Mouse.getMouseX());
		if( Mouse.isPressed(Mouse.LEFT) ) {
			//System.out.println("pressed");
			
			tempX = Mouse.getMouseX();
			tempY = Mouse.getMouseY();
			int hover = checkHoverMouse (tempX, tempY);
			if( hover >= 0 && !draggingItem) {
				if(Keys.isHold(Keys.CTRL)) {
					System.out.println(hover);
					draggingItem = true;
					if(hover == Tile.WOODBLOCK) {
						temp.set(2,  new WoodBlock(handler,hover, tempX, tempY) );
						currentIndex = 2;
					}
							
					else if(hover == Tile.LONGWOODBLOCK) {
						temp.set(2,  new LongWoodBlock(handler,hover, tempX, tempY) );
						currentIndex = 2;
					}
				}
				else {
					System.out.println(hover);
					draggingItem = true;
					if(hover == Tile.WOODBLOCK) {
						temp.add( new WoodBlock(handler,hover, tempX, tempY) );
						currentIndex = temp.size()-1;
						System.out.println(currentIndex);
					}
					else if(hover == Tile.WHITEBLOCK) {
						temp.add( new WhiteBlock(handler,hover, tempX, tempY) );
						currentIndex = temp.size()-1;
					}
					else if(hover == Tile.TIMEBLOCK) {
						temp.add( new TimeBlock(handler,hover, tempX, tempY) );
						currentIndex = temp.size()-1;
					}
					else if(hover == Tile.LONGWHITEBLOCK) {
						temp.add( new LongWhiteBlock(handler,hover, tempX, tempY) );
						currentIndex = temp.size()-1;
					}				
					else if(hover == Tile.LONGWOODBLOCK) {
						temp.add( new LongWoodBlock(handler,hover, tempX, tempY) );
						currentIndex = temp.size()-1;
					}
					else if(hover+2 == Tile.VERTICALWOODBLOCK) {
						temp.add( new VerticalWoodBlock(handler,hover, tempX, tempY) );
						currentIndex = temp.size()-1;
					}
					else if(hover+2 == Tile.LONGVERTICALWOODBLOCK) {
						temp.add( new LongVerticalWoodBlock(handler,hover, tempX, tempY) );
						currentIndex = temp.size()-1;
					}
					else if(hover+2 == Tile.VERTICALWHITEBLOCK) {
						temp.add( new VerticalWhiteBlock(handler,hover, tempX, tempY) );
						currentIndex = temp.size()-1;
					}
					else if(hover+2 == Tile.LONGVERTICALWHITEBLOCK) {
						temp.add( new LongVerticalWhiteBlock(handler,hover, tempX, tempY) );
						currentIndex = temp.size()-1;
					}
					else if(hover == 9) {
						temp.set(0, new Player(handler, tempX, tempY, Player.size, Player.size));
						currentIndex = 0;
					}
					else if(hover == 10) {
						temp.set(1, new Player(handler, tempX, tempY, Player.size, Player.size));
						currentIndex = 1;
					}
				}
			}
			else {
				//tempX = Mouse.getMouseX();
				//tempY = Mouse.getMouseY();
				
				//add to arraylist
//				objectPos.add(tempX);
//				objectPos.add(tempY);
				
				
				//currentIndex +=2;
				
				
				draggingItem = false;
			}
		}
		else if(Mouse.isPressed(Mouse.RIGHT) && !draggingItem) {
			if(!temp.isEmpty() && temp.size()>3)
				temp.remove(temp.size()-1);
			else if(temp.size() <=3){
				temp.set(temp.size()-1, null);
			}
		}
		
		if(Keys.isHold(Keys.CTRL)) {
			if(Keys.isPressed(Keys.DOWN_PLAYER2)) {
				if(temp.get(0) !=null && temp.get(1) != null && temp.get(2) != null ) {
					System.out.println("Saved");
					saveToFile();
				}
			}
		}
		
		if(Keys.isHold(Keys.CTRL)) {
			if(Keys.isPressed(Keys.UP_PLAYER2)) {
				handler.getGsm().setState(GameStateManager.SPLASHSCREEN);
			}
		}
		
		if(Keys.isHold(Keys.SHIFT)) {
			grid = 20;
			
		}
		else {
			grid = 1;
		}
		
		
	}
	
	
	private void saveToFile(){
		try {
			PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
			int i =0;
			for(Entity e: temp) {
				if(i < 2) {
					writer.println((int)e.getX() + " " +(int) e.getY());
				}
				else if(i==3){
					writer.println(temp.size()-3);
				}
				if(i>1) {
					writer.println((int)e.getX() + " " +(int) e.getY() + " " + e.getType());
				}
				
				i++;
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private Rectangle getCollisionBound(int x, int y, int width, int height ) {
		return new Rectangle(x, y, width, height);
	}
	
	private int checkHoverMouse(int x, int y) {
		for(int i = 0; i<tile.length; i++) {
			if(getCollisionBound( (GamePanel.WIDTH/2 - (tile.length-1)*25 + i * 50), (25), 50, 50 ).contains(getCollisionBound( x, y, 1, 1 )))
				return i;
		}
		return -1;
		
	}
	
}
