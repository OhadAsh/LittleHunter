//Player class last changed in 15/04/2020 20:10
package Entities;

import TileMap.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import GameState.GameStateManager;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject {

	//Player Inventory variables
	private int HP;
	private int MaxHP;
	private int Ammo;
	private int MaxAmmo;
	private boolean Dead;
	private boolean flinch;
	private long flinchTimer;
	
	//Arrows variables
	private boolean firing;
	private int ArrowCost;
	private int ArrowDMG;
	private ArrayList<Arrow> Arrows;
	
	//Sword variables
	private boolean Attack;
	private int AttackDMG;
	private int AttackRNG;
	
	//Animation
	private ArrayList<BufferedImage[]> sprites;
	//Array of sprite sheet littleHunter
	private final int[] numFrames = { 4, 6, 3, 2, 6, 6};
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
		cheight = 30;
		
		//Physic variables 
		movespeed = 0.3;
		maxspeed = 1.6;
		stopspeed = 0.4;
		fallspeed = 0.15;
		maxfallspeed = 4.0;
		jumpstart = -4.8;
		stopjumpstart = 0.3;
		
		faceright = true;
		
		HP = 1;
		MaxHP = 20;
		Ammo = MaxAmmo = 30;
		ArrowCost = 1;
		ArrowDMG = 5;
		Arrows = new ArrayList<Arrow>();
	
		AttackDMG = 9;
		AttackRNG = 40;
		
		//Load player sprite onto game 
		try 
		{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream
					("/Sprites/Player/LittleHunter.png"));
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 6; i++)
			{
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				//Read individual sprite from sprite sheet
				for(int j = 0; j < numFrames[i]; j++)
				{
					if(i != 4) {
					bi[j] = spritesheet.getSubimage
							(j * width, i* height, width, height);
					}
					else
					{
						bi[j] = spritesheet.getSubimage
								((j * width) + 10, i* height, width + 2, height);
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
	public int getMaxAmmo() { return MaxAmmo; }
	
	//Setter for Keyboard Input
	public void SetFiring()
	{
		firing = true;
	}
	public void SetAttacking()
	{
		Attack = true;
	}
	//Setter for HP
	public void SetHP( int i)
	{
		HP += i;
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
		//Cannot move while attacking except in air which is OK
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
			if(dy > maxfallspeed) dy = maxfallspeed;
		}
	}
	
	//Function that checks if player attacks enemy entity's
	public void CheckAttack(ArrayList<Enemy> enemies)
	{
		//Loop through array of enemies 
		for(int i = 0; i < enemies.size(); i++)
		{
			Enemy e = enemies.get(i);
			//Check if sword attack hits enemy
			if(Attack)
			{
				if(faceright)
				{
					//Checks if enemy that is to the right of us is being hit by sword attack
					if(e.getx() > x &&
							e.getx() < x + AttackRNG &&
							e.gety() > y - height / 2 &&
							e.gety() < y + height / 2)
					{
						e.hit(AttackDMG);
					}
				}
				else
				{
					//Checks if enemy that is to the left of us is being hit by sword attack
					if(e.getx() < x &&
							e.getx() > x - AttackRNG &&
							e.gety() > y - height / 2 &&
							e.gety() < y + height / 2)
					{
						e.hit(AttackDMG);
					}
				}
			}
			//Check if Bow attack hits enemy
			//need for loop in order to check active arrows that are still on map
			for(int j = 0; j < Arrows.size(); j++)
			{
				if(Arrows.get(j).intersects(e))
				{
					e.hit(ArrowDMG);
					//if arrow hit arrow disappear
					Arrows.get(j).SetHit();
					break;
				}
			}	
			
			//Check for enemy collision
			if(intersects(e))
			{
				hit(e.getDMG());
			}
		}		
	}
	
	//Damage to player when hit
	public void hit(int DMG)
	{
		if(flinch) return;
		HP -= DMG;
		if(HP < 0) HP = 0;
		if(HP == 0) 
		{
			Dead = true;
		}
		//jump when hit
		//dy = -3;
		flinch = true;
		flinchTimer = System.nanoTime();
	}
	
	//Updates drawing of player on screen
	public void update()
	{
		//Update position of player
		GetNextPosition();
		CheckTileMapColl();
		setposition(Xtemp, Ytemp);
		
		//Check attack has stopped
		if(curretAction == SWORD)
		{
			if(animation.HasPlayedOnce()) 
			{
				Attack = false;
			}
		}
		if(curretAction == BOW)
		{
			if(animation.HasPlayedOnce()) 
			{
				firing = false;
			}
		}
		
		//Bow attack, Checking if there enough arrows in player inventory.
		if(Ammo > MaxAmmo)
		{
			Ammo = MaxAmmo;
		}
		if(firing && curretAction != BOW)
		{
			//Checks if there is enough ammo to shoot arrow
			if (Ammo > 0)
			{
				Ammo -= ArrowCost;
				Arrow AA = new Arrow(tilemap, faceright);
				//Set in the same player tile
				AA.setposition(x, y);
				Arrows.add(AA);
			}
		}
		
		//Update Arrow movement across map tiles
		for(int i = 0; i < Arrows.size(); i++)
		{
			Arrows.get(i).update();
			if(Arrows.get(i).RemoveArrow())
			{
				Arrows.remove(i);
				i--;
			}
		}
		
		//Check if flinch is done
		if(flinch)
		{
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1000)
			{
				flinch = false;
			}
		}
		
		//TO FIX BUG WITH COMBINATION OF ATTACKS BOW & SWORD MAKE IT A SKILL AND CHANGE TO SWITCH CASE CODE!!!!!
		//SetAnimation for all player positions
		if(Attack)
		{
			if(curretAction != SWORD)
			{
				curretAction = SWORD;
				animation.setFrames(sprites.get(SWORD));
				animation.setDelay(35);
				width = 30;
			}
		}
		else if(firing)
		{
			if(curretAction != BOW)
			{
				curretAction = BOW;
				animation.setFrames(sprites.get(BOW));
				animation.setDelay(35);
				width = 30;
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
				animation.setDelay(100);
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
		{
			if(right) faceright = true;
			if(left) faceright = false;
		}
	}
	
	//Draws object of 2D to screen
	public void draw(Graphics2D g)
	{
		setMapPosition();
		//Draw Arrows
		for(int i = 0; i < Arrows.size(); i++) 
		{
			Arrows.get(i).draw(g);
		}
		
		//Draw player
		if(flinch)
		{
			long elapsed = (System.nanoTime() - flinchTimer / 1000000);
			if(elapsed / 100 % 2 == 0)
			{
				return;
			}
		}
		super.draw(g);
	}


}
