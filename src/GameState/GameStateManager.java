package GameState;

/* Array list is to hold all of our game states.
It is much more convenient because you don't have pop all the way to some state
you just can jump to it
*/
import java.util.ArrayList;

public class GameStateManager 
{
		private ArrayList<GameState> gameStates;
		//index of the game state in list
		private int currentState;
		//Game states Enums
		public static final int MENUSTATE =  0;
		public static final int LEVEL1 =  1;
		public static final int LEVEL2 =  2;
		//Constructor
		public GameStateManager()
		{
			gameStates = new ArrayList<GameState>();
			currentState = MENUSTATE;
			gameStates.add(new MenuState(this));
			gameStates.add(new LEVEL1(this));
		}
		
		public void setstate(int i)
		{
			currentState = i;
			//initialize state as current
			gameStates.get(currentState).init();
		}
		
		public void update()
		{
			gameStates.get(currentState).update();
		}
		
		public void draw(java.awt.Graphics2D g)
		{
			gameStates.get(currentState).draw(g);
		}
		
		public void keyPressed(int k)
		{
			gameStates.get(currentState).keyPressed(k);
		}
		
		public void keyRelesed(int k)
		{
			gameStates.get(currentState).keyRelesed(k);
		}
		
} 
 