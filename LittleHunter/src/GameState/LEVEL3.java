
package GameState;

import TileMap.*;
import Entities.*;
import Entity.Enemies.Goblin;
import Entity.Enemies.Skeleton;
import Handlers.KeyHandler;
import Main.GamePanel;
import java.awt.*;
import java.util.ArrayList;
import Entities.ScoreManager;

public class LEVEL3 extends GameState
{
	private TileMap tileMap;
	private Background bg; 
	private Player player;
	private ArrayList<Enemy> enemies;
	private HUD hud;
	private ScoreManager SM;
	
	//Constructor of level 3
	public LEVEL3(GameStateManager gsm)
	{
		this.gsm = gsm;
		init();
	}
	
	//initializing tile map and level 3 stuff
	public void init() 
	{
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/DaungenTileset.png");
		tileMap.loadMap("/Maps/level3.map");
		tileMap.setPosition(0, 0);
		tileMap.setMapCam(1);
		bg = new Background("/Backgrounds/Daungen.png", 0.1);
		player = new Player(tileMap);
		player.setposition(70 ,85);
		player.SetHP(5);
		player.SetAmmo(10);
		PopulateEnemies();
		hud = new HUD(player);
		this.SM = hud.SM;
		String name = SM.GetPlayerName();
		hud.SM.SetPlayerName(name);
	}
	
	
	private void PopulateEnemies()
	{
		enemies = new ArrayList<Enemy>();
		Skeleton s;
		Goblin g;
		
		Point[] points = new Point[]
				{
					new Point(1750, 160),
					new Point(1680, 160),
					new Point(1600, 160),
					new Point(1550, 160),
				};
		//For to go through points array and add enemies on map
		for(int i = 0; i < points.length; i++)
			{
				s = new Skeleton(tileMap);
				s.setposition(points[i].x, points[i].y);
				enemies.add(s);
			}
		g = new Goblin(tileMap);
		g.setposition(1650, 160);
		enemies.add(g);
		
	}
	
	
	//Checks player position if he falls to a pit he goes back to last jump of map
	public void CheckFallPos()
	{
		if(player.gety() > 210 )
		{
			if(player.getx() > 800 )
			{
				player.setposition(800 ,160);
				player.hit(1);
			}
			else if(player.getx() > 90 )
			{
				player.setposition(70 ,85);
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
		if(enemies.size() == 0)
		{
			SM.increaseScore(5000);
			hud.SM.GameScoreEvent();
			hud.SM.ResetScore();
			gsm.setstate(GameStateManager.WINEND);
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
				SM.increaseScore(250);
				i--;
				player.SetHP(1);
				player.SetAmmo(3);
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
