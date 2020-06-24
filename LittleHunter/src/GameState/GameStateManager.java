package GameState;

/* Array is to hold all of our game states.
It is much more convenient because you don't have pop all the way to some state
you just can jump to it
*/
import Main.GamePanel;

public class GameStateManager 
{
		private GameState[] gameStates;
		//index of the game state in list
		private int currentState;
		//Game states Enums
		public static final int NUMGAMESTATES = 8;
		public static final int MENUSTATE =  0;
		public static final int LEVEL1 =  1;
		public static final int LEVEL2 =  2;
		public static final int LEVEL3 =  3;
		public static final int WINEND = 4;
		public static final int DEADEND = 5;
		public static final int SCOREBOARD = 6;
		public static final int INFO = 7;
		
		//Constructor
		public GameStateManager()
		{
			gameStates = new GameState[NUMGAMESTATES];
			currentState = MENUSTATE;
			loadState(currentState);
		}
		
		private void loadState(int state) {
			if(state == MENUSTATE)
				gameStates[state] = new MenuState(this);
			else if(state == LEVEL1)
				gameStates[state] = new LEVEL1(this);
			else if(state == LEVEL2)
				gameStates[state] = new LEVEL2(this);
			else if(state == LEVEL3)
				gameStates[state] = new LEVEL3(this);
			else if(state == WINEND)
				gameStates[state] = new WinEnd(this);
			else if(state == DEADEND)
				gameStates[state] = new DeadEnd(this);
			else if(state == SCOREBOARD)
				gameStates[state] = new ScoreBoard(this);
			else if(state == INFO)
				gameStates[state] = new Info(this);	
		}
		
		private void unloadState(int state) {
			gameStates[state] = null;
		}
		
		public void setstate(int state)
		{
			//initialize state as current
			unloadState(currentState);
			currentState = state;
			loadState(currentState);
		}
		
		public void update()
		{
			gameStates[currentState].update();
		}
		
		public void draw(java.awt.Graphics2D g)
		{
			if(gameStates[currentState] != null) gameStates[currentState].draw(g);
			else {
				g.setColor(java.awt.Color.BLACK);
				g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			}
		}
		
} 
 