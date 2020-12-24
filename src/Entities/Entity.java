package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Handlers.Handlers;



public abstract class Entity {
	
	protected double x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected Handlers handler;
	protected boolean solid;
	public Entity(Handlers handler, double x, double y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle(0, 0, width, height);
		
	}
	
	
	public boolean CheckCollision(double xOff, double yOff) { 
		
		for(Entity e: handler.getPlayState().getEntityManager().getEntities()) { 
			if((e.equals(this))) { 
				continue;
			}
			if(e.getCollisionBound(0, 0).intersects(getCollisionBound(xOff, yOff))) { 
				if(! e.isSolid(this)) {
					continue;
				}
				else if( (e.equals(handler.getEntityManager().getFinishTile())) && yOff>0 ) {
					if( this.equals(handler.getEntityManager().getPlayer() ) ) {
						handler.getEntityManager().setPlayer1OnFinishTile(true);
					}
					else {
						handler.getEntityManager().setPlayer2OnFinishTile(true);
					}
				}
					
				return true; 
			}
			
		} 
		
		if( this.equals(handler.getEntityManager().getPlayer() ) ) {
			handler.getEntityManager().setPlayer1OnFinishTile(false);
		}
		else {
			handler.getEntityManager().setPlayer2OnFinishTile(false);
		}
		
		
		return false;
	}
	
	public boolean isSolid(Entity entity) {
		return solid;
	}
	
	public abstract void reset(); 
	
	public Rectangle getCollisionBound(double xOff, double yOff) {
		
		return new Rectangle( (int) (this.x + bounds.x + xOff), (int) (this.y + bounds.y + yOff), bounds.width, bounds.height);
	}
	
	
	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}
	
	

	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public abstract int getType();
	public abstract void update();
	public abstract void draw(Graphics2D g);
}
