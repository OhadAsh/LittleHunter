package GameState;


import TileMap.Background;
import java.awt.*;
import Handlers.KeyHandler;

public class Info extends GameState 
{

	private Background bg;
	private Font font;

	public Info(GameStateManager gsm)
	{
	this.gsm = gsm;
	init();
	}
	
	//abstract methods
	public void init()
	{
		try {
			//Resource folder
			bg = new Background("/Backgrounds/Info.png", 1);
			font = new Font("Ariel", Font.PLAIN, 14);
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
		g.setColor(Color.black);
		g.drawString("Press Enter for Menu", 100, 210);
	}

	//Key functions at window
	public void handleInput() {
		if(KeyHandler.isPressed(KeyHandler.ENTER)) 
			{
				gsm.setstate(GameStateManager.MENUSTATE);
			}
		}
	}