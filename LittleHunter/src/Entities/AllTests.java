package Entities;

import org.junit.Test;
import TileMap.TileMap;
import org.junit.Assert;

public class AllTests 
{
	private ScoreManager SM;
	private TileMap tileMap;
	private Player player;
	
	public AllTests()
	{
		//Need to add mock map and tileset for testing
		tileMap = new TileMap(1);
		player = new Player(tileMap);
		SM = new ScoreManager();
	}
	
@Test
public void TestPlayerHP()
{
	//Tests player HP Inc/Dec
	Assert.assertEquals(1, player.getHP());
	player.SetHP(2);
	Assert.assertEquals(3, player.getHP());
	player.hit(1);
	Assert.assertEquals(2, player.getHP());
}

@Test
public void TestArrowAmount()
{
	//Tests Player ammo function
	player.SetAmmo(5);
	Assert.assertEquals(5, player.getAmmo());
	player.AmmoCost();
	Assert.assertEquals(4, player.getAmmo());
	
}

@Test
public void TestPlayerScore()
{

	//Test to Check player score
	SM.increaseScore(50);
	Assert.assertEquals(50, SM.getScore());
	SM.ResetScore();
	Assert.assertEquals(0, SM.getScore());

}

@Test
public void TestPlayerName()
{

	//Test to Check player Name
	//None name was given
	Assert.assertEquals("Empty", SM.GetPlayerName());
	
	//Check if player name is inserted accordingly
	String Name = "Student";
	SM.SetPlayerName(Name);
	Assert.assertEquals(Name, SM.GetPlayerName());

}

}
