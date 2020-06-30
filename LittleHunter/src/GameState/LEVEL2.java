
package GameState;

import TileMap.*;
import Entities.*;
import Entity.Enemies.Ghost;
import Handlers.KeyHandler;
import Main.GamePanel;
import java.awt.*;
import java.util.ArrayList;
import Entities.ScoreManager;

public class LEVEL2 extends GameState
{
	private TileMap tileMap;
	private Background bg; 
	private Player player;
	private ArrayList<Enemy> enemies;
	private HUD hud;
	private ScoreManager SM;
	
	//Constructor of level 2
	public LEVEL2(GameStateManager gsm)
	{
		this.gsm = gsm;
		init();
	}
	
	//initializing tile map and level 2 stuff
	public void init() 
	{
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/SnowTileset.png");
		tileMap.loadMap("/Maps/level2.map");
		tileMap.setPosition(0, 0);
		tileMap.setMapCam(1);
		bg = new Background("/Backgrounds/Snowy Mountains.png", 0.1);
		player = new Player(tileMap);
		player.setposition(70 ,180);
		player.SetHP(4);
		player.SetAmmo(12);
		PopulateEnemies();
		hud = new HUD(player);
		this.SM = hud.SM;
		String name = SM.GetPlayerName();
		hud.SM.SetPlayerName(name);
	}
	
	
	private void PopulateEnemies()
	{
		enemies = new ArrayList<Enemy>();
		Ghost g;
		//Java array of points with coordination to spawn enemies on map
		Point[] points = new Point[]
		{
			new Point(550, 80),
			new Point(500, 220),
			new Point(1020, 190),
			new Point(1250, 70),
			new Point(1520, 70),
			new Point(1600, 70),
			new Point(2150, 180),
			new Point(2250, 180),
			new Point(2600, 80),
			new Point(2700, 80),
			new Point(2850, 80),
			new Point(2950, 80),
		};
		
		//For to go through points array and add enemies on map
		for(int i = 0; i < points.length; i++)
		{
			g = new Ghost(tileMap);
			g.setposition(points[i].x, points[i].y);
			enemies.add(g);
		}
	}
	
	//Checks player position if he falls to a pit he goes back to last jump of map
	public void CheckFallPos()
	{
		if(player.gety() > 250 )
		{
			if(player.getx() > 1950 )
			{
				player.setposition(1950 ,90);
				player.hit(1);
			}
			else if(player.getx() > 1700 )
			{
				player.setposition(1700 ,110);
				player.hit(1);
			}
			else if(player.getx() > 1100 )
			{
				player.setposition(1100 ,80);
				player.hit(1);
			}
			else if(player.getx() > 700 )
			{
				player.setposition(700 ,80);
				player.hit(1);
			}
			else if(player.getx() > 180 )
			{
				player.setposition(70 ,180);
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
		if(player.getx() > 2950 )
		{
			gsm.setstate(GameStateManager.LEVEL3);
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
				SM.increaseScore(100);
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
