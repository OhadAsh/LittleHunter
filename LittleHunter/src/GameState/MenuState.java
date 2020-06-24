//last changed in 12/04/2020 22:45
package GameState;

import TileMap.Background;
import java.awt.*;
import Handlers.KeyHandler;

public class MenuState extends GameState
{
	private Background bg;
	
	private int currentChoise = 0;
	private String[] options = 
		{
		"Play",
		"Scoreboard",
		"Info",
		"Exit"
		};
	
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	//Constructor
	public MenuState(GameStateManager gsm)
	{
	this.gsm = gsm;
	try {
		//Resource folder
		bg = new Background("/Backgrounds/menubg.png", 1);
		titleColor = new Color(250, 145, 25);
		titleFont = new Font("Caliberi", Font.BOLD, 26);
		font = new Font("Arial", Font.ROMAN_BASELINE, 14);
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	
	}
	
	//Abstract methods
	public void init(){}
	
	//Update screen
	public void update()
	{
		bg.update();
		//Key handle update
		handleInput();
	}
	
	//Draw to screen 
	public void draw(Graphics2D g)
	{
		//draw background
		bg.draw(g);
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Little Hunter", 80, 100);
		
		//menu options
		g.setFont(font);
		for(int i=0; i<options.length; i++)
		{
			if(i == currentChoise) {
				g.setColor(Color.ORANGE);
			}
			else {
				g.setColor(Color.CYAN);	
			}
			g.drawString(options[i], 145, 140+i * 15);
		}
	}
	
	private void select () {
		if (currentChoise == 0)
		{
			gsm.setstate(GameStateManager.LEVEL1);
		}
		else if(currentChoise == 1) {
			gsm.setstate(GameStateManager.SCOREBOARD);
		}
		else if(currentChoise == 2) {
			gsm.setstate(GameStateManager.INFO);
		}
		else if(currentChoise == 3) {
			System.exit(0);
		}
	}
	
	//Key functions at menu window
	public void handleInput() {
		if(KeyHandler.isPressed(KeyHandler.ENTER)) select();
		if(KeyHandler.isPressed(KeyHandler.UP)) 
		{
			if(currentChoise > 0) 
			{
				currentChoise--;
			}
		}
		if(KeyHandler.isPressed(KeyHandler.DOWN)) 
		{
			if(currentChoise < options.length - 1) 
			{
				currentChoise++;
			}
		}
	}
	
}
