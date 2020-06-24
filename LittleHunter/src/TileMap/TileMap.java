//TileMap engine for game last changed in 11/04/2020 
package TileMap;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import Main.GamePanel;


public class TileMap 
{

	//Positions on map
	private double x;
	private double y;
	
	//Boundaries of the map
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	//Camera of map
	private double MapCam;
	
	//Map 
	private int [][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	//Tileset of Map
	private BufferedImage tileset;
	private int numTilesAcross;
	//Array of tiles that represents the tileset
	private Tile[][] tiles;
	
	
	//Drawing of the map in order not to draw all the map at once
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	//Constructor of tilemap
	public TileMap(int tileSize)
	{
		this.tileSize = tileSize;
		//+2 for padding buffered map two blocks ahead
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		MapCam = 0;
	}
	
	//Function that loads tile set of map to memory 
	public void loadTiles(String s)
	{
		try {
			tileset = ImageIO.read(getClass().getResource(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			//Import tile set
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++)
			{
				//Tiles in the 0 row is object normal tile
				subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				//Tiles in the 1 row is block tile
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage, Tile.BLOCK);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Function that loads the map tiles from memory
	@SuppressWarnings("resource")
	public void loadMap(String s)
	{
		try {
			
			//Map file loader
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader (new InputStreamReader(in));
			
			//Read map  
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			
			//Map array
			map = new int [numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			//default values for variables 
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			
			//Read map file array
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++)
			{
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++)
				{
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
		
	//getters 
	public int getTileSize() 
	{
		return tileSize;
	}
	public double getx() 
	{
		return x;
	}
	public double gety() 
	{
		return y;
	}
	public int GetWidth() 
	{
		return width;
	}
	public int GetHeight() 
	{
		return height;
	}
	public int GetNumRows() 
	{
		return numRows;
	}
	public int GetNumCols() 
	{
		return numCols;
	}
	//Get tile type
	public int getType(int row,int col)
	{
		int RC  = map[row][col];
		int r = RC / numTilesAcross;
		int c = RC % numTilesAcross;
		return tiles[r][c].getType();
	}
	
	//Set position on player entity
	public void setPosition(double x, double y)
	{
		this.x += (x - this.x) * MapCam;
		this.y += (y - this.y) * MapCam;
		FixBounds();
		colOffset = (int)-this.x / tileSize;
		rowOffset  = (int)-this.y / tileSize;
	}
	
	//Setter for MapCam
	public void setMapCam(double d) { MapCam = d; }
	
	//Function that fixes the boundaries of visual map
	private void FixBounds()
	{
		if(x < xmin) x = xmin;
		if(x > xmax) x = xmax;
		if(y < ymin) y = ymin;
		if(y > ymax) y = ymax;
	}
	
	//Function that draws the map
	public void draw(Graphics2D g)
	{
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++)
		{
			if(row >= numRows)
			{
				break;
			}
			for(int col = colOffset; col < colOffset + numColsToDraw; col++)
			{
				if(col >= numCols)
				{
					break;
				}
				if(map[row][col] == 0)
				{
					continue;
				}
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				g.drawImage(tiles[r][c].getImage(), (int)x + col * tileSize,(int)y + row * tileSize, null);
			}
		}
	}

}
