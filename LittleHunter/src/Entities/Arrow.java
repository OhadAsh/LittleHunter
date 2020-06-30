//Arrow entity
package Entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import TileMap.TileMap;

public class Arrow extends MapObject{
	
	private boolean hit;
	private boolean remove;
	private BufferedImage[] RegSprite;
	private BufferedImage[] HitSprite;

	//Constructor
	public Arrow(TileMap tm, boolean right)
	{
		super(tm);
		
		faceright = right;
		movespeed = 4.5;
		
		//Sets the move direction of arrow
		if(right)
		{
			dx = movespeed;
		}
		else
		{
			dx = -movespeed;
		}
		//For drawing
		width = 30;
		height = 30;
		cwidth = 14;
		cheight = 14;
		
		//Load sprite of arrow
		try 
		{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
					"/Sprites/Player/Arrow.png"));
			RegSprite = new BufferedImage[4];
			for(int i = 0; i < RegSprite.length; i++)
			{
				RegSprite[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
			
			//DEVELOPMENT CURRENTLY 0 THERE IS NO ANIMATION READY FOR THIS
			//Just in order to work for now
			HitSprite = new BufferedImage[0];
			for(int i = 0; i < HitSprite.length; i++)
			{
				HitSprite[i] = spritesheet.getSubimage(i * width, height, width, height);
			}	
			
			//Set animation  
			animation =  new Animation();
			animation.setFrames(RegSprite);
			animation.setDelay(25);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Function to know if arrow hit something
	public void SetHit()
	{
		if(hit)
		{
			return;
		}
		hit = true;
		//No animation yet for Arrow hit
		//animation.setFrames(HitSprite);
		//animation.setDelay(70);
		dx = 0;
	}
	
	//Returns if arrow should be removed from game
	public boolean RemoveArrow() 
	{
		return remove;
	}
	
	public void update()
	{
		CheckTileMapColl();
		setposition(Xtemp, Ytemp);
		
		//If arrow speed is 0 and didn't hit any target then count as hit
		if (dx == 0 && !hit)
		{
			SetHit();
		}
		
		//For animation update
		animation.update();
		//Check if arrow should be removed
		if(hit && animation.HasPlayedOnce())
		{
			remove = true;
		}
	}
	
	public void draw(Graphics2D g)
	{
		setMapPosition();
		super.draw(g);
	}
	
}
