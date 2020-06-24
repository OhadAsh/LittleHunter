package GameState;

import TileMap.Background;
import java.awt.*;
import Entities.ScoreManager;
import Entities.ScoreManager.PlayerScore;
import Handlers.KeyHandler;

public class ScoreBoard extends GameState 
{

	private Background bg;
	private Font Hfont;
	private Font font;
	private ScoreManager SM;
	@SuppressWarnings("unused")
	private String s;
	
	public ScoreBoard(GameStateManager gsm)
	{
	this.gsm = gsm;
	init();
	}
	
	//abstract methods
	public void init()
	{
		try 
		{
			SM = new ScoreManager();
			bg = new Background("/Backgrounds/ScoreBoard.png", 1);
			Hfont = new Font("Ariel", Font.BOLD, 15);
			font = new Font("Ariel", Font.ROMAN_BASELINE, 10);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
    } 

	
	public void DrawScore(Graphics2D g)
	{
		int i = 0;
		for(PlayerScore s: SM.getScores())
		{
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString((i/10+1)+"." +s.GetName()+":"+s.GetScore().toString(),120, 55 + i);
			i += 10;
		}
	}
	
	public void update()
	{
		//Key handle update
		handleInput();
	}
	public void draw(Graphics2D g)
	{
		//draw background
		bg.draw(g);
		g.setFont(Hfont);
		g.setColor(Color.ORANGE);
		g.drawString("ScoreBoard" ,120 , 35);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString("Press Enter for Menu", 110, 210);
		DrawScore(g);
	}
	//Key functions at ScoreBoard window
	public void handleInput() 
	{
		if(KeyHandler.isPressed(KeyHandler.ENTER)) {
			gsm.setstate(GameStateManager.MENUSTATE);
			}
		}
	}


