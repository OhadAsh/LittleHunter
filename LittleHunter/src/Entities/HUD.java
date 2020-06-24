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
	private Font Sfont;
	public ScoreManager SM;
	//Constructor
	public HUD(Player p)
	{
		player = p;
		SM = new ScoreManager();
		try {
			image = ImageIO.read
					(getClass().getResourceAsStream("/HUD/HUD.png"));
			font = new Font("Ariel", Font.PLAIN, 14);
			Sfont = new Font("Ariel", Font.CENTER_BASELINE, 9);
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
		g.drawString(player.getHP() + "HP" ,45 , 35);
		g.drawString(player.getAmmo() + "/" + player.getMaxAmmo(), 40, 55);
		
		g.setFont(Sfont);
		g.setColor(Color.YELLOW);
		g.drawString(SM.getScore() + "Score" ,275 , 10);
	}
	
}
