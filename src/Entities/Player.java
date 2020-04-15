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
	private final int[] numFrames = { 4, 6, 3, 2, 3, 7};
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
								((j * width) + 15, i* height, width, height);
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
		currAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(200);
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
		if((currAction == SWORD || currAction == BOW) &&
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
			if(dy < 0 && !jump) dy += stopjumpstart;
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
			if(currAction != SWORD)
			{
				currAction = SWORD;
				animation.setFrames(sprites.get(SWORD));
				animation.setDelay(50);
				width = 40;
			}
		}
		else if(firing)
		{
			if(currAction != BOW)
			{
				currAction = BOW;
				animation.setFrames(sprites.get(BOW));
				animation.setDelay(100);
				width = 30;
			}
		}
		else if(dy > 0)
		{
			if(currAction != FALIING)
			{
				currAction = FALIING;
				animation.setFrames(sprites.get(FALIING));
				animation.setDelay(100);
				width = 30;
			}
		}
		else if(dy < 0)
		{
			if(currAction != JUMPING)
			{
				currAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(100);
				width = 30;
			}
		}
		else if(left || right)
		{
			if(currAction != WALKING)
			{
				currAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 30;
			}
		}
		else
		{
			if(currAction != IDLE)
			{
				currAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(200);
				width = 30;
			}
		}
		
		animation.update();
		//Set direction of player standing
		if(currAction != SWORD && currAction != BOW)
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
