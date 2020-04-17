//Level 1 class last changed in 18/04/2020 00:33
package GameState;

import TileMap.*;
import Entities.*;
import Entity.Enemies.Mushroom;
import Main.GamePanel;
import Entity.Enemies.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;



public class LEVEL1 extends GameState 
{
	private TileMap tileMap;
	private Background bg; 
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	
	private HUD hud;
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
		tileMap.loadTiles("/Tilesets/grasstileset.png");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setMapCam(1);
		
		bg = new Background("/Backgrounds/forrest1.gif", 0.1);
		player = new Player(tileMap);
		//Sets position of player in level 1
		player.setposition(60 ,195);
		
		enemies = new ArrayList<Enemy>();
		Mushroom m;
		m = new Mushroom(tileMap);
		m.setposition(60, 100);
		enemies.add(m);
		
		hud = new HUD(player);
	}
	
	public void update() 
	{
		//update player
		player.update();
		//Sets camera around player
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), 
				GamePanel.HEIGHT / 2 - player.gety()
				);
		
		//Need to size up the map
		//Moving background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		//Update enemy 
		for(int i = 0; i < enemies.size(); i++)
		{
			enemies.get(i).update();
		}
		
	}
	
	//Draw stage to game panel
	public void draw(Graphics2D g) 
	{
		//Draw background of map 1
		bg.draw(g);
		
		//Draw tile map of map 1
		tileMap.draw(g);
		
		//Draw player
		player.draw(g);
		
		//Draw enemy's
		for(int i = 0; i < enemies.size(); i++)
		{
			enemies.get(i).draw(g);
		}
		
		//Draw player HUD
		hud.draw(g);
		
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
