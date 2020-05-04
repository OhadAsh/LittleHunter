//Handles sprite animation of characters 
package Entities;

import java.awt.image.BufferedImage;

public class Animation {
	//Array for all animations
	private BufferedImage[] frames;
	
	private int currentFrame;
	private long startTime;
	private long delay;
	private boolean playedOnce;
	
	//Constructor
	public Animation()
	{
		playedOnce = false;
	}
	
	//Set frames
	public void setFrames(BufferedImage[] frames)
			{
				this.frames = frames;
				currentFrame = 0;
				startTime = System.nanoTime();
				playedOnce = false;
			}
	//Delay
	public void setDelay(long d) { delay = d; }
	//In order to set frames manually
	public void setFrame(int i) { currentFrame = i; }
	
	//To check if frame needs update
	public void update()
	{
		if(delay == -1) 
		{
			return;
		}
		//How long since last frame came up
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay)
		{
			//move to next frame and reset start time
			currentFrame++;
			startTime = System.nanoTime();
		}
		//Check not to go out of bounds 
		if(currentFrame == frames.length)
		{
			currentFrame = 0;
			playedOnce = true;
		}
	}
	
	
	//Getters
	public int getFrame() { return currentFrame; }
	public BufferedImage getImage() { return frames[currentFrame]; }
	public boolean HasPlayedOnce() { return playedOnce; }
}
