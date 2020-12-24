package Utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read( ImageLoader.class.getClassLoader().getResourceAsStream(path));

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public static BufferedImage rotateCw( BufferedImage img )
	{
	    int         width  = img.getWidth();
	    int         height = img.getHeight();
	    BufferedImage   newImage = new BufferedImage( height, width, img.getType() );
	 
	    for( int i=0 ; i < width ; i++ )
	        for( int j=0 ; j < height ; j++ )
	            newImage.setRGB( height-1-j, i, img.getRGB(i,j) );
	 
	    return newImage;
	}
		
}
