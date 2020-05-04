//Background class for the game
package TileMap;

import Main.GamePanel;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

//Last change 10/04/2020 23:40
//Menu background image
public class Background 
{
	private BufferedImage image;
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double moveSacle;
	public Background(String s, double ms)
	{
		try {
			//Get resource image
			image = ImageIO.read(getClass().getResourceAsStream(s));
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPosition(double x, double y) 
	{
		this.x = (x * moveSacle) % GamePanel.WIDTH;
		this.y = (y * moveSacle) % GamePanel.HEIGHT;
	}
	
	public void setVector(double dx, double dy) 
	{
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update()
	{
		x += dx;
		y += dy;
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(image, (int)x, (int)y, null);
		if (x < 0)
		{
			g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
		}
		if (x > 0)
		{
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
		}
	}	
}
