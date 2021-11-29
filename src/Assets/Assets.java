package Assets;

import java.awt.image.BufferedImage;

import Utils.ImageLoader;

public class Assets {
	
	public static final int HEIGHT = 16, WIDTH = 159;
	public static BufferedImage[] frames = new BufferedImage[196];

	
	public static void init() {
	
		for (int i = 0; i < 196; i++) {
			if (i < 10) {
				frames[i] = ImageLoader.loadImage("Loading/00" + i + ".gif");
			} else if (i >= 10 && i < 100) {
				frames[i] = ImageLoader.loadImage("Loading/0" + i + ".gif");
			} else {
				frames[i] = ImageLoader.loadImage("Loading/" + i + ".gif");
			}
//			System.out.println("loading frames : " + i);
			
		}
	}
}
