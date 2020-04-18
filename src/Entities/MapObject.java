//Superclass map object, base class to all objects on game last changed in 13/04/2020 21:38
package Entities;

import java.awt.Rectangle;

import Main.GamePanel;
import TileMap.Tile;
import TileMap.TileMap;

public abstract class MapObject {

	//Tile map variables
	protected TileMap tilemap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	//Position and vector of object variables
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	//Dimensions variables for reading sprite sheet 
	protected int width;
	protected int height;
	
	//Collision box variables
	protected int cwidth;
	protected int cheight;
	
	//Collision variables
	protected int currRow;
	protected int currCol;
	protected double Xdestination;
	protected double Ydestination;
	protected double Xtemp;
	protected double Ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	//Animation variables 
	protected Animation animation;
	protected int currAction;
	protected int preAction;
	protected boolean faceright;
	
	//Movement variables
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jump;
	protected boolean fall;
	
	//Movement attributes of an object variables
	protected double movespeed;
	protected double maxspeed;
	protected double stopspeed;
	protected double fallspeed;
	protected double maxfallspeed;
	protected double jumpstart;
	protected double stopjumpstart;
	
	
	//Constructor to set tile map and get tile size
	public MapObject(TileMap tm)
	{
		tilemap = tm;
		tileSize = tm.getTileSize();
		animation = new Animation();
		faceright = true;
	}
	
	//Function of intersection between map object "collisions"
	public boolean intersects(MapObject obj)
	{
		Rectangle R1 = getRectangle();
		Rectangle R2 = obj.getRectangle();
		return R1.intersects(R2);
	}
	
	//Function that creates object rectangle 
	public Rectangle getRectangle()
	{
		return new Rectangle((int)x - cwidth,(int)y - cheight, cwidth, cheight);
	}
	
	//Function that calculates corners of object 
	protected void CalCorners(double x, double y)
	{
		int leftTile = (int)(x - cwidth / 2) / tileSize;
		// -1 in order not to move to next column 
		int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
		int topTile = (int)(y - cheight / 2) / tileSize;
		int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
			
		//Get types of tiles
		int TL = tilemap.getType(topTile, leftTile);
		int TR = tilemap.getType(topTile, rightTile);
		int BL = tilemap.getType(bottomTile, leftTile);
		int BR = tilemap.getType(bottomTile, rightTile);
		
		//Setting booleans for blocking map object (player)
		topLeft = TL == Tile.BLOCK;
		topRight = TR == Tile.BLOCK;
		bottomLeft = BL == Tile.BLOCK;
		bottomRight = BR == Tile.BLOCK;
	}
	
	//Function that checks collision between objects
	public void CheckTileMapColl()
	{
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		//Destination positions
		Xdestination = x + dx;
		Ydestination = y + dy;
		
		//Tracking x,y positions 
		Xtemp = x;
		Ytemp = y;
		
		CalCorners(x, Ydestination);
		//Going up 
		if(dy < 0)
		{
			if(topLeft || topRight)
			{
				dy = 0;
				//Setting the player below object 
				Ytemp = currRow * tileSize + cheight / 2;
			}
			else 
			{
				Ytemp += dy;
			}
		}
		//Falling player
		if(dy > 0)
		{
			if(bottomLeft || bottomRight)
			{
				dy = 0;
				fall = false;
				//Setting the player in lower object 
				Ytemp = (currRow + 1) * tileSize - cheight / 2;
			}
			else 
			{
				Ytemp += dy;
			}
		}
		CalCorners(Xdestination, y);
		//Going left
		if(dx < 0)
		{
			if(topLeft || bottomLeft)
			{
				dx = 0;
				//Setting the player right to the object we hit in left
				Xtemp = currCol * tileSize + cwidth / 2;
			}
			else 
			{
				Xtemp += dx;
			}
		}
		//Going right
		if(dx > 0)
		{
			if(topRight || bottomRight)
			{
				dx = 0;
				//Setting the player left to the object we hit in right
				Xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else 
			{
				Xtemp += dx;
			}
		}
		//Check if player is falling
		if(!fall) 
		{
			CalCorners(x, Ydestination+1);
			if(!bottomLeft && !bottomRight)
			{
				fall = true ;
			}
		}	
	}
	
	//Getters
	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public int getWidth() { return (int)width; }
	public int getHeight() { return (int)height; }
	public int getCWidth() { return (int)cwidth; }
	public int getCHeight() { return (int)cheight; }
	
	//Setter for positions
	public void setposition(double x,double y)
	{
		//Setters
		this.x = x;
		this.y = y;
	}
	
	//Setter for vector
	public void setVector(double dx, double dy)
	{
		//Setters
		this.dx = dx;
		this.dy = dy;
	}
	
	//Set map position in order to know where to draw the character
	public void setMapPosition() 
	{
		xmap = tilemap.getx();
		ymap = tilemap.gety();
	}
	
	//Set action of the object
	public void SetLeft(boolean b) { left = b; }
	public void SetRight(boolean b) { right = b; }
	public void SetJump(boolean b) { jump = b; }
	//No need for now
	//public void SetUp(boolean b) { up = b; }
	//public void SetDown(boolean b) { down = b; }
	
	//Function to know if object is needed to be drawn on map or not
	public boolean NotOnScreenObj()
	{
		return x + xmap + width < 0 ||
				x + xmap - width > GamePanel.WIDTH ||
				y + ymap + height < 0 ||
				y + ymap - height > GamePanel.HEIGHT; 
	}
	
	public void draw(java.awt.Graphics2D g)
	{
		//Drawing sprite right or left depends on object action
		if(faceright)
		{
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
		}
		else
		{
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2 + width), (int)(y + ymap - height / 2), -width, height, null);
		}
	}
}