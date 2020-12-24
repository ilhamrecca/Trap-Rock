package GameState;


import java.awt.Graphics2D;

import Handlers.Handlers;




public abstract class GameState {
	
protected Handlers handler;
	
	public GameState(Handlers handler) {
		this.handler = handler;
		
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void handleInput();
	
}
