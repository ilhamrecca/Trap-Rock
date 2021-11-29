package Entities;

public class Piece {
	private int xPos;
	private int yPos;
	private int player;
	
	public Piece(int xPos, int yPos, int player) {
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.player = player;
	}
	public int getxPos() {
		return xPos;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public int getyPos() {
		return yPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	public int getPlayer() {
		return player;
	}
	public void setPlayer(int player) {
		this.player = player;
	}
}
