package GameState;


import TileMap.Background;
import java.awt.*;
import Handlers.KeyHandler;

public class DeadEnd extends GameState 
{

	private Background bg;
	private Font font;

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
			bg = new Background("/Backgrounds/DeadEnd.png", 1);
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
		g.setColor(Color.WHITE);
		g.drawString("Press ESC to Exit" ,95 , 175);
		g.drawString("Press Enter for Menu", 90, 195);
	}

	//Key functions at window
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