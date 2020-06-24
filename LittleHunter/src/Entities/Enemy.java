//Class for enemies in game, Last changed 17/04/2020 21:30
package Entities;

import TileMap.TileMap;


public class Enemy extends MapObject {

	protected int HP;
	protected int MaxHP;
	protected boolean dead;
	protected int DMG;
	
	protected boolean flinching;
	protected long flinchTimer;
	
	
	public Enemy(TileMap tm)
	{
		super(tm);
	}
	
	public boolean isDead()
	{
		return dead;
	}
	
	public int getDMG()
	{
		return DMG;
	}
	
	public void hit(int DMG)
	{
		if(dead || flinching) return;
		HP -= DMG;
		if(HP < 0 ) 
		{
			HP = 0;
		}
		if(HP == 0) 
		{
			dead = true;
		}
		flinching = true;
		flinchTimer = System.nanoTime();	
	}
	
	public void update() {}
	
}
