package GameState;

import Main.GamePanel;
import TileMap.Background;
import java.awt.*;
import java.awt.event.KeyEvent;

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
	}
	public void draw(Graphics2D g)
	{
		//draw background
		bg.draw(g);
	}

	//Key functions at menu window
	public void keyPressed(int k)
	{
		if (k== KeyEvent.VK_ESCAPE) {
			Runtime.getRuntime().exit(0);
		}
		if (k== KeyEvent.VK_ENTER) {
			gsm.setstate(GameStateManager.MENUSTATE);
		}
	}	
	public void keyRelesed(int k){}
}