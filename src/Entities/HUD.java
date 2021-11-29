package Entities;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Assets.Assets;
import Handlers.Handlers;


public class HUD {	
	private Handlers handler;
	private Font font;
	public HUD(Handlers handler) {
		this.handler =handler;
		font = new Font("Arial", Font.PLAIN, 40);
	}
	
	public void draw(Graphics2D g) {

		g.setColor(Color.GREEN);
		g.setFont(font);
		int second = handler.getDisplay().getSecond();
		int minutes = handler.getDisplay().getMinute();
		String hasil = second < 10 ? minutes + ":0" + second : minutes + ":"+ second;
		hasil = minutes < 10? "0" + hasil : hasil;
		g.drawString(hasil, 800, 50);
		
	}
	
}













