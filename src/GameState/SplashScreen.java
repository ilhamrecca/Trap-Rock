package GameState;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import Handlers.AudioPlayer;
import Handlers.Handlers;
import Handlers.Keys;
import Main.GamePanel;
import Utils.ImageLoader;

public class SplashScreen extends GameState{

	
		
		private BufferedImage test;
		private float alpha;
		private float rate;
		private long timer;
		private long halfTime;
		private float initialXSize;
		private float initialYSize;
		private float zoomRate;
		public SplashScreen(Handlers handler) {
			super(handler);
			initialXSize = 365f;
			initialYSize =  282f;
			zoomRate = 1f;
			this.halfTime = 0;
			this.timer = 0;
			
			init();
		} 
		@Override
		public void init() {
			
			AudioPlayer.init();
			this.alpha = 0;
			this.rate = 0.04f;
			test = ImageLoader.loadImage("SplashScreen/Splash.png");
		}

		@Override
		public void update() {
			handleInput();
			isDone();
		}
		
		@Override
		public void draw(Graphics2D g) {
//			g.setComposite(AlphaComposite.getInstance(
//		            AlphaComposite.SRC_OVER, 1.0f));
//		    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//			
			g.setColor(Color.BLACK);
		    g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			
			g.setComposite(AlphaComposite.getInstance(
		            AlphaComposite.SRC_OVER, alpha));
		    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		    //do the drawing here
		    
		    
		    
			g.drawImage(test, (int) (GamePanel.WIDTH/2 - initialXSize /2 ), (int) ( GamePanel.HEIGHT/2 - initialYSize/2 ), (int) (initialXSize + zoomRate ), (int) (initialYSize + zoomRate), null);
			
		    //increase the opacity and repaint
		    alpha += rate;
			if(alpha >=1.0f) {
				alpha = 1.0f;
				if(halfTime !=0) {
					timer += System.currentTimeMillis() - halfTime;
				}
				halfTime = System.currentTimeMillis();
				if(timer>500) {
					rate = -0.015f;
					timer = 0;
					halfTime = System.currentTimeMillis();
				}
			}
			
			if( rate < 0f){
				timer += System.currentTimeMillis() - halfTime;
				halfTime = System.currentTimeMillis();
				if(timer > 1150) {
					alpha = 0;
				}
				else if(alpha <= 0) {
					alpha = 0.00001f;
				}
				
			}
		}

		
		private void isDone() {
			initialXSize += zoomRate;
			initialYSize += zoomRate;
			if(alpha <= 0 && rate <0) {
				System.out.println("SPLASHSCREEN ENDED, LOADING MAIN MENU MUSIC");
				AudioPlayer.load("Music/MainMenu.mp3", "menu");
				handler.getGsm().setState(GameStateManager.MENUSTATE);
			}
		}
		
		@Override
		public void handleInput() {
			if(Keys.isPressed(Keys.ENTER)) {
				if(rate >0) {
					alpha = 1.0f;
				}
			}
			
		}
}
