//Tile class for the game
package TileMap;

import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage image;
	private int type;
	
	//Tile Types
	public static final int NORMAL = 0;
	public static final int BLOCK = 1;
	
	//Constructor of tile map
	public Tile(BufferedImage image, int type)
	{
		this.image = image;
		this.type = type;
	}
	
	//Return image and type of block
	public BufferedImage getImage() 
	{
		return image;
	}
	public int getType() 
	{
		return type;
	}
}
