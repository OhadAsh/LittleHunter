//Player class last changed in 15/04/2020 20:10
package Entities;

import TileMap.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject {

	//Player Inventory
	private int HP;
	private int MaxHP;
	private int Ammo;
	private int MaxAmmo;
	private boolean Dead;
	private boolean flinch;
	private long flinchTimer;
	
	//Arrows
	private boolean firing;
	private int ArrowCost;
	private int ArrowDMG;
	
	//private arraylist<arrows> arrows
	
	//Sword
	private boolean Attack;
	private int AttackDMG;
	private int AttackRNG;
	
	//Animation
	private ArrayList<BufferedImage[]> sprites;
	//Array of sprite littleHunter
	private final int[] numFrames = { 4, 6, 3, 2, 8, 7};
	private int curretAction;
	//Animation actions, Enums for knowing which index of Animation were on
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALIING = 3;
	private static final int BOW = 4;
	private static final int SWORD = 5;
	
	//Constructor, setting tile map and tile size
	public Player(TileMap tm)
	{
		super(tm);
		
		//For reading sprite sheet
		width = 30;
		height = 30;
		cwidth =  20;
		cheight = 20;
		
		//Physic variables 
		movespeed = 0.3;
		maxspeed = 1.6;
		stopspeed = 0.4;
		fallspeed = 0.15;
		maxfallspeed = 4.0;
		jumpstart = -4.8;
		stopjumpstart = 0.3;
		
		faceright = true;
		
		HP = MaxHP = 3;
		Ammo = MaxAmmo = 30;
		ArrowCost = 1;
		ArrowDMG = 5;
		//Arrow = new Array<Arrows>();
	
		AttackDMG = 9;
		AttackRNG = 40;
		
		//load sprite onto game 
		try 
		{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream
					("/Sprites/Player/LittleHunter.gif"));
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 6; i++)
			{
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				//Read individual sprites from sheet
				for(int j = 0; j < numFrames[i]; j++)
				{
					if(i != 4) {
					bi[j] = spritesheet.getSubimage
							(j * width, i* height, width, height);
					}
					else
					{
						bi[j] = spritesheet.getSubimage
								((j * width) + 12, i* height, width, height);
					}
				}
				sprites.add(bi);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		
		animation = new Animation();
		//Sets player default animation IDLE
		curretAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(250);
	}
	
	//Getters for player HUD 
	public int getHP() { return HP; }
	public int getMaxHP() { return MaxHP; }
	public int getAmmo() { return Ammo; }
	public int MaxAmmo() { return MaxAmmo; }
	
	//Keyboard Input
	public void SetFiring()
	{
		firing = true;
	}
	
	public void SetAttacking()
	{
		Attack = true;
	}
	
	//Function that determines where the next position of the player is by reading keyboard input
	private void GetNextPosition()
	{
		//Movement
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
		else {
			if(dx > 0)
			{
				dx -= stopspeed;
				if(dx < 0 )
				{
					dx = 0;
				}
			}
			else if(dx < 0)
			{
				dx += stopspeed;
				if(dx > 0)
				{
					dx = 0;
				}
			}
		}
		//cannot move while attacking
		if((curretAction == SWORD || curretAction == BOW) &&
		!(jump || fall))
		{
			dx = 0;
		}
		//Jump
		if(jump && !fall)
		{
			dy = jumpstart;
			fall = true;
		}
		//Fall
		if(fall)
		{
			dy += fallspeed;
			if(dy > 0 ) jump = false;
			//The longer you hold the jump button the higher you jump
			//if(dy < 0 && !jump) dy += stopjumpstart;
			if(dy > maxfallspeed) dy = maxfallspeed;
		}
	}
	
	//Updates drawing of player on screen
	public void update()
	{
		//Update position of player
		GetNextPosition();
		CheckTileMapColl();
		setposition(Xtemp, Ytemp);
		
		//SetAnimation
		if(Attack)
		{
			if(curretAction != SWORD)
			{
				curretAction = SWORD;
				animation.setFrames(sprites.get(SWORD));
				animation.setDelay(35);
				width = 42;
			}
		}
		else if(firing)
		{
			if(curretAction != BOW)
			{
				curretAction = BOW;
				animation.setFrames(sprites.get(BOW));
				animation.setDelay(35);
				width = 42;
			}
		}
		else if(dy > 0)
		{
			if(curretAction != FALIING)
			{
				curretAction = FALIING;
				animation.setFrames(sprites.get(FALIING));
				animation.setDelay(70);
				width = 30;
			}
		}
		else if(dy < 0)
		{
			if(curretAction != JUMPING)
			{
				curretAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(200);
				width = 30;
			}
		}
		else if(left || right)
		{
			if(curretAction != WALKING)
			{
				curretAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(100);
				width = 30;
			}
		}
		else
		{
			if(curretAction != IDLE)
			{
				curretAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(200);
				width = 30;
			}
		}
		
		animation.update();
		//Set direction of player standing
		if(curretAction != SWORD && curretAction != BOW)
			if(right) faceright = true;
			if(left) faceright = false;
	}
	
	//Draws object of 2D to screen
	public void draw(Graphics2D g)
	{
		setMapPosition();
		
		//Draw player
		if(flinch)
		{
			long elapsed = (System.nanoTime() - flinchTimer / 1000000);
			if(elapsed / 100 % 2 == 0)
			{
				return;
			}
		}
		//Drawing sprite right or left depends on player action
		if(faceright)
		{
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
		}
		else
		{
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2 + width), (int)(y + ymap - height / 2), -width, height, null);
		}
	}

}
