package Entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class HUD {

	private Player player;
	private BufferedImage image;
	private Font font;
	
	//Constructor
	public HUD(Player p)
	{
		player = p;
		try {
			image = ImageIO.read
					(getClass().getResourceAsStream("/HUD/hud.gif"));
			font = new Font("Ariel", Font.PLAIN, 14);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(image, 0, 20, null);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(player.getHP() + "/" + player.getMaxHP(),40 , 35);
		g.drawString(player.getAmmo() + "/" + player.getMaxAmmo(), 40, 55);
	}
	
}
