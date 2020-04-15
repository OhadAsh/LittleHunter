package GameState;

// we don't want anyone to use this class just to extend out of it
public abstract class GameState 
{
	protected GameStateManager gsm;
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyRelesed(int k);
}
