package Main;

import javax.swing.JFrame;

public class HunterGame 
{
	public static void main (String[] args)
	{
		JFrame window = new JFrame ("Little Hunter");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
}
