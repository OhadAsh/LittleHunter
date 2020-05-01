package GameState;


import TileMap.Background;
import java.awt.*;
import Handlers.KeyHandler;

public class DeadEnd extends GameState 
{

	private Background bg;
	

	public DeadEnd(GameStateManager gsm)
	{
	this.gsm = gsm;
	init();
	}
	
	//abstract methods
	public void init()
	{
		try {
			//Resource folder
			bg = new Background("/Backgrounds/DeadEnd.gif", 1);
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
	}

	//Key functions at menu window
	public void handleInput() {
		if(KeyHandler.isPressed(KeyHandler.ESCAPE)) 
		{
			System.exit(0);
		}
		if(KeyHandler.isPressed(KeyHandler.ENTER)) {
			gsm.setstate(GameStateManager.MENUSTATE);
			}
		}
	}