package GameState;


import TileMap.Background;
import java.awt.*;
import Handlers.KeyHandler;

public class WinEnd extends GameState 
{

	private Background bg;
	private Font font;

	public WinEnd(GameStateManager gsm)
	{
	this.gsm = gsm;
	init();
	}
	
	//abstract methods
	public void init()
	{
		try {
			//Resource folder
			bg = new Background("/Backgrounds/WinEnd.png", 1);
			font = new Font("Ariel", Font.BOLD, 15);
		}
		catch(Exception e) {
			e.printStackTrace();
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
		g.setFont(font);
		g.setColor(Color.blue);
		g.drawString("Press Enter for Scoreboard", 90, 175);
	}

	//Key functions at window
	public void handleInput() {
		if(KeyHandler.isPressed(KeyHandler.ENTER)) 
			{
				gsm.setstate(GameStateManager.SCOREBOARD);
			}
		}
	}