
package Entity.Enemies;

import Entities.Animation;
import Entities.Enemy;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Goblin extends Enemy{
	
	private BufferedImage[] sprites;
	
	public Goblin(TileMap tm)
	{
		super(tm);
		
		movespeed =  1.4;
		maxspeed = 1.4;
		fallspeed = 0.2;
		maxfallspeed = 10.0;
		
		
		width = 30;
		height =  40;
		cwidth = 30;
		cheight = 30;
		
		HP = MaxHP = 40;
		DMG = 4;
		
		//Load enemy sprite sheet
		try
		{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream("/Sprites/Enemies/Goblin.png"));
				sprites = new BufferedImage[4];
				for(int i = 0; i < sprites.length; i++)
				{
					sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);		
				}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(135);
		
		right  = true;
		faceright = true;
	}
	private void getNextPosition() 
	{
		if(left)
		{
			dx -= movespeed;
			if(dx < -maxspeed)
			{
				dx = -maxspeed;
			}
		}
		else if(right)
		{
			dx += movespeed;
			if(dx > maxspeed)
			{
				dx = maxspeed;
			}
		}
		//Falling
		if(fall)
		{
			dy += fallspeed;
		}
	}
	
	public void update()
	{
		//Update position of enemy
		getNextPosition();
		CheckTileMapColl();
		setposition(Xtemp, Ytemp);
		
		//Check flinching
		if(flinching)
		{
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400)
			{
				flinching = false;
			}
		}
		//Go other direction if hits a wall
		if(right && dx == 0)
		{
			right = false;
			left = true;
			faceright = false;
		}
		else if(left && dx == 0)
		{
			right = true;
			left = false;
			faceright = true;
		}
		
		//Update animation of Enemy
		animation.update();
	}
	
	public void draw(Graphics2D g)
	{
		
		setMapPosition();
		
		super.draw(g);
		
	}
	
	
}
