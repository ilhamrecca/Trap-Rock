package Entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import Assets.Assets;
import GameState.GameStateManager;
import Handlers.AudioPlayer;
import Handlers.Handlers;
import Handlers.Keys;
import Main.GamePanel;


public class Player extends Entity{
	protected static int lives;
	public static final int size = 32;
	//speed x and speed y
	private double dx, dy;
	private double xtemp, ytemp;
	private BufferedImage texture;
	
	// movement
		protected boolean left;
		protected boolean right;
		protected boolean up;
		protected boolean down;
		protected boolean jumping;
		protected boolean falling;
	
	//speed caps and idk
		protected double moveSpeed;
		protected double maxSpeed;
		protected double stopSpeed;
		protected double fallSpeed;
		protected double maxFallSpeed;
		protected double jumpStart;
		protected double stopJumpSpeed;
		
	public Player(Handlers handler, double x, double y, int width, int height) {
		super(handler, x,y, width, height);
		lives = 3;
		
		solid = false;
		
		moveSpeed = 3;
		maxSpeed = 5.5;
		stopSpeed = 0.5;
		fallSpeed = 1.0f;
		maxFallSpeed =15.0;
		jumpStart = -20;
		stopJumpSpeed = 0.3;
		dx = 0;
		dy = 0;
		falling = true;
		this.texture = Assets.player;
		this.left = false;
		this.right = false;
		this.up = false;
		this.down = false;
		
	}
	@Override
	public void reset() {
		if(this.y > GamePanel.HEIGHT + 20) {
			loseLives();
			handler.getEntityManager().reset();
			resetPosition();
		}
	}
	
	@Override
	public int getType() {
		return -1;
	}
	
	@Override
	public void update() {
		getNextPosition();
		checkCollisionWithTile();
		//System.out.println("x : "+ xtemp + " y : "+ytemp);
		
		setPosition();
		reset();
		resetLevel();
		time();
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(texture,(int) x,(int) y, width, height, null);
		
	}
	
	private void resetPosition() {
		//reset position player 1
		handler.getEntityManager().getPlayer().setX(handler.getLevel().getSpawnXP1());
		handler.getEntityManager().getPlayer().setY(handler.getLevel().getSpawnYP1());
		
		
		//reset position player 2
		handler.getEntityManager().getPlayer2().setX(handler.getLevel().getSpawnXP2());
		handler.getEntityManager().getPlayer2().setY(handler.getLevel().getSpawnYP2());
	}
	
	private void resetLevel() {
		if(Player.lives <0) {
			int second = handler.getDisplay().getSecond();
			int minutes = handler.getDisplay().getMinute();
			String hasil = second < 10 ? minutes + ":0" + second : minutes + ":"+ second;
			hasil = minutes < 10? "0" + hasil : hasil;
			handler.getDisplay().getTimer().cancel(true);
			handler.getDisplay().setSecond(0);
			handler.getDisplay().setMinute(0);
			
			
			Keys.reset();
			handler.getEntityManager().getEntities().clear();
			handler.getGsm().setState(GameStateManager.MENUSTATE);
		}
	}
	
	
	private void loseLives() {
		Player.lives --;
	}
	
	private void getNextPosition() {
		double maxSpeed = this.maxSpeed;

		// movement
		if(left) {
			if(x >= 0) {
				dx -= moveSpeed;
				if(dx < -maxSpeed) {
					dx = -maxSpeed;
				}
			}
			else {
				dx = 0;
			}
		}
		else if(right) {
			if(x <= GamePanel.WIDTH - size) {
				dx += moveSpeed;
				if(dx > maxSpeed) {
					dx = maxSpeed;
				}
			}
			else {
				dx = 0;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		
		// jumping
		if(jumping && !falling) {
			AudioPlayer.play("jump");
			dy = jumpStart;
			falling = true;
		}
		
		// falling
		if(falling) {
			//AudioPlayer.play("fall");
			dy += fallSpeed;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		
	}
	
	private void setPosition() {

			this.x = xtemp;
			this.y = ytemp;
	}
	
	private void time() {
		
//		System.out.print("menit : " + handler.getDisplay().getMinute());
//		System.out.println("detik" + handler.getDisplay().getSecond());
		
		
	}

	private void checkCollisionWithTile() {
		xtemp = x;
		ytemp = y;
		
		xtemp += dx;
		ytemp +=dy;
		if(CheckCollision(dx, 0)) {
			xtemp -= dx;
			dx = 0.5;
		}
		
		if(CheckCollision(0, dy)) {
			ytemp -= dy;
			
//			}else {
//				dy = dy/2;
//			}
			//dy = dy/2;
			if(dy<0) {
				falling = true;
				dy = 0;
			}
			else {
				//AudioPlayer.play("fall", 0);
				falling = false;
				dy = 0.6;
			}
		}
		else if (CheckCollision(dx, dy)) {
			xtemp -= dx;
			ytemp -= dy;
			dy = 0.5;
			dx = 0.5;
		}
		else {
			if(dy>0) {
				falling = true;
			}
		}
		 
	}

	//setter getter
	
	public boolean isLeft() {
		return left;
	}
	
	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public boolean isRight() {
		return right;
	}
	
	public void setRight(boolean right) {
		this.right = right;
	}
	
	public boolean isUp() {
		return up;
	}
	
	public void setUp(boolean up) {
		this.up = up;
	}
	
	public boolean isDown() {
		return down;
	}
	
	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	
	public void setFalling(boolean falling) {
		this.falling = falling;
	}
	public BufferedImage getTexture() {
		return texture;
	}
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public static int getLives() {
		return lives;
	}

	public static void setLives(int lives) {
		Player.lives = lives;
	}
	

	public double getXtemp() {
		return xtemp;
	}
	public void setXtemp(double xtemp) {
		this.xtemp = xtemp;
	}
	public double getYtemp() {
		return ytemp;
	}
	public void setYtemp(double ytemp) {
		this.ytemp = ytemp;
	}
}
