//Game panel class last changed in 13/04/2020 20:31
package Main;

import java.awt.*; // needed for window dimensions 
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import GameState.GameStateManager;
import Handlers.KeyHandler;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener
{
	// dimensions of game window 
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;	
	
	// game thread 
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;

	// image
	private BufferedImage image;
	private Graphics2D g;
	
	//game state manager
	private GameStateManager gsm;
	
	// GamePanel constructor 
	public GamePanel()
	{
		super();
		setPreferredSize(
			new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	public void addNotify() 
	{
		super.addNotify();
		if(thread ==null) 
		{
			thread = new Thread (this);
			addKeyListener(this);
			thread.start();
		}
	}
	private void init () 
	{
	image = new BufferedImage(
			WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	g = (Graphics2D) image.getGraphics();
	running = true;
	
	// new in order to be defined 
	gsm = new GameStateManager();
	}
	
	
	public void run() 
	{
		init ();
		long start;
		long elapsed;
		//Must update before render
		long MUBU;
		//Game running loop on thread
		while(running)
			{
				start = System.nanoTime();
				update();
				draw();
				drawtoscreen();
				
				elapsed = System.nanoTime() - start;
				//Total time before render
				MUBU = targetTime - elapsed / 1000000;
				try {
					Thread.sleep(MUBU);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
		
			}
	}
	
	// updates the game
	public void update() 
	{
		gsm.update();
		KeyHandler.update();
	}
	// draws the game onto an off-screen buffered image
	public void draw() 
	{
		gsm.draw(g);
	}
	// draws the off-screen  buffered image to the screen
	public void drawtoscreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) 
	{
		KeyHandler.keySet(key.getKeyCode(), true);
	}
	public void keyReleased(KeyEvent key) 
	{
		KeyHandler.keySet(key.getKeyCode(), false);
	}
}
