package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Object {
	protected int width, height;
	protected Rectangle bounds;
	
	public Object(int width, int height) {
		this.width = width;
		this.height = height;
		bounds = new Rectangle(0, 0, width, height);
	}
	
	public abstract void update();
	public abstract void draw(Graphics2D g);
}
