//Level 1 class last changed in 18/04/2020 00:33
package GameState;

import TileMap.*;
import Entities.*;
import Entity.Enemies.Mushroom;
import Handlers.KeyHandler;
import Main.GamePanel;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class LEVEL1 extends GameState 
{
	private TileMap tileMap;
	private Background bg; 
	private Player player;
	private ArrayList<Enemy> enemies;
	private HUD hud;
	private ScoreManager SM;
	
	//Constructor of level 1
	public LEVEL1(GameStateManager gsm)
	{
		this.gsm = gsm;
		init();
	}
	
	//initializing tile map and level 1 stuff
	public void init() 
	{
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.png");
		tileMap.loadMap("/Maps/level1.map");
		tileMap.setPosition(0, 0);
		tileMap.setMapCam(1);
		bg = new Background("/Backgrounds/forrest1.png", 0.1);
		player = new Player(tileMap);
		//Sets position of player in level 1
		player.setposition(80 ,195);
		player.SetHP(2);
		player.SetAmmo(5);
		PopulateEnemies();
		hud = new HUD(player);
		this.SM = hud.SM;
		String name = JOptionPane.showInputDialog("Please insert your name :)");
		hud.SM.SetPlayerName(name);
	}
	
	
	private void PopulateEnemies()
	{
		enemies = new ArrayList<Enemy>();
		Mushroom m;
		//Java array of points with coordination to spawn enemies on map
		Point[] points = new Point[]
		{
			new Point(860, 190),
			new Point(1525, 190),
			new Point(1680, 190),
			new Point(1800, 190),
			new Point(2200, 190),
		};
		
		//For to go through points array and add enemies on map
		for(int i = 0; i < points.length; i++)
		{
			m = new Mushroom(tileMap);
			m.setposition(points[i].x, points[i].y);
			enemies.add(m);
		}
	}
	
	//Checks player position if he falls to a pit he goes back to last jump of map
	public void CheckFallPos()
	{
		if(player.gety() > 210 )
		{
			if(player.getx() > 1900 )
			{
				player.setposition(1950 ,60);
				player.hit(1);
			}
			else if(player.getx() > 950 )
			{
				player.setposition(1150 ,60);
				player.hit(1);
			}
			else if(player.getx() > 400 )
			{
				player.setposition(450 ,60);
				player.hit(1);
			}	
		}
	}
	
	//Checks if player died in level
	public void CheckIFDied()
	{
		if(player.getHP() == 0)
		{
			hud.SM.GameScoreEvent();
			hud.SM.ResetScore();
			gsm.setstate(GameStateManager.DEADEND);
		}
	}
	
	public void CheckIFWon()
	{
		if(player.getx() > 2400 )
		{
			gsm.setstate(GameStateManager.LEVEL2);
		}
	}
	
	public void update()
	{
		//Key handle update
		handleInput();
		//update player
		player.update();
		CheckFallPos();
		//Sets camera around player
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), 
				GamePanel.HEIGHT / 2 - player.gety()
				);
		
		//Need to size up the map
		//Moving background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		//Check if Player attacks enemy
		player.CheckAttack(enemies);
		//Update enemies on map
		for(int i = 0; i < enemies.size(); i++)
		{
			enemies.get(i).update();
			if(enemies.get(i).isDead())
			{
				enemies.remove(i);
				SM.increaseScore(50);
				i--;
			}
		}
		CheckIFWon();
		CheckIFDied();
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
	
	public void handleInput() {
		player.SetLeft(KeyHandler.keyState[KeyHandler.LEFT]);
		player.SetRight(KeyHandler.keyState[KeyHandler.RIGHT]);
		player.SetJump(KeyHandler.keyState[KeyHandler.SPACE]);
		if(KeyHandler.isPressed(KeyHandler.BUTTON1)) player.SetAttacking();
		if(KeyHandler.isPressed(KeyHandler.BUTTON2)) player.SetFiring();
	}
}
