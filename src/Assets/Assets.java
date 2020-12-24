package Assets;

import java.awt.image.BufferedImage;

import Utils.ImageLoader;

public class Assets {
	
	public static final int HEIGHT = 16, WIDTH = 159;
	
	public static BufferedImage player, player2, woodBlock, blueBlock, pinkBlock , whiteBlock, longWhiteBlock, longBlueBlock, longPinkBlock, longWoodBlock;
	public static BufferedImage heart;
	public static BufferedImage start, startC, startH, quit, quitC, quitH, highscore, highscoreC, highscoreH;
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("Object/Platform.png"));
		SpriteSheet button = new SpriteSheet(ImageLoader.loadImage("Button/Picture2.png"));
		heart = ImageLoader.loadImage("HUD/heartsmall.png");
		woodBlock = sheet.crop(0, 0, WIDTH, HEIGHT);
		pinkBlock = sheet.crop(WIDTH+1, 0, WIDTH, HEIGHT);
		blueBlock = sheet.crop(WIDTH*3 + 2, 0, WIDTH, HEIGHT);
		whiteBlock = sheet.crop(WIDTH*2 + 1, 0, WIDTH, HEIGHT);
		longWhiteBlock = sheet.crop(242, HEIGHT*2, 241, HEIGHT);
		longPinkBlock = sheet.crop(242, HEIGHT, 241, HEIGHT);
		longBlueBlock = sheet.crop(0, HEIGHT*2, 241, HEIGHT);
		longWoodBlock = sheet.crop(0, HEIGHT, 241, HEIGHT);
		player = ImageLoader.loadImage("Object/blue.png");
		player2 = ImageLoader.loadImage("Object/pink.png");
		
		start   = button.crop(0, 0, 818, 166);
		startH  = button.crop(0, 165, 818, 166);
		startC  = button.crop(0, 165*2, 818, 166);
		
		quit  = button.crop(818, 0, 818, 166);
		quitH = button.crop(818, 165, 818, 166);
		quitC = button.crop(818, 165*2, 818, 166);
		
		highscore  = button.crop(818*2, 0, 818, 166);
		highscoreH = button.crop(818*2, 165, 818, 166);
		highscoreC = button.crop(818*2, 165*2, 818, 166);
		
	}
}
