//Level 1 class last changed in 13/04/2020 20:32
package GameState;

import TileMap.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import Entities.*;
import Main.GamePanel;


public class LEVEL1 extends GameState 
{
	private TileMap tileMap;
	private Background bg; 
	
	private Player player;
	//Constructor of level 1
	public LEVEL1(GameStateManager gsm)
	{
		this.gsm = gsm;
		init();
	}
	
	//initializing tile map and level 1 stuff
	public void init() 
	{
		//Constructor
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		
		bg = new Background("/Backgrounds/forrest1.gif", 0.5);
		player = new Player(tileMap);
		//Sets position of player in level 1
		player.setposition(60 ,200);
	}
	
	public void update() 
	{
		//update player
		player.update();
		//Sets camera around player
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), 
				GamePanel.HEIGHT / 2 - player.gety()
				);
	}
	
	//Draw stage to game panel
	public void draw(Graphics2D g) 
	{
		//draw background of map 1
		bg.draw(g);
		
		//draw tilemap of map 1
		tileMap.draw(g);
		
		//draw player
		player.draw(g);
		
	}
	public void keyPressed(int k) 
	{
		if(k == KeyEvent.VK_LEFT) player.SetLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.SetRight(true);
		if(k == KeyEvent.VK_SPACE) player.SetJump(true);
		if(k == KeyEvent.VK_S) player.SetAttacking();
		if(k == KeyEvent.VK_D) player.SetFiring();
	}
	public void keyRelesed(int k) 
	{
		if(k == KeyEvent.VK_LEFT) player.SetLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.SetRight(false);
		if(k == KeyEvent.VK_SPACE) player.SetJump(false);
	}
}
