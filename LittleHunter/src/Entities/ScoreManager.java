package Entities;

import java.lang.StringBuffer;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ScoreManager
{
  private Path scoreFile = null;
  private Comparator<PlayerScore> scoreSorter = (score1,score2) -> score2.score - score1.score;
  private static String playerName = "Empty";
  private static int score = 0;
  
  
  public ScoreManager()
	{
	  try
	  {
	  scoreFile = Paths.get("Resources/File/SaveScore/Score.dat");
	  }
	  catch(Exception e)
		{
			e.printStackTrace();
		}
	}
  
  //GetScore reads score list from file 
  public List<PlayerScore> getScores(){
    List<PlayerScore> scores = new ArrayList<PlayerScore>(10);
    try(BufferedReader fileReader = Files.newBufferedReader(scoreFile);)
    {
      String line;
      while ( (line=fileReader.readLine())!=null) 
      {
        String[] data = line.split("=");
        if (data.length < 2) break;
        String name = data[0];
        int score = Integer.parseInt(data[1]);
        PlayerScore playerScore = new PlayerScore(name,score);
        scores.add(playerScore);
      }
    }
    catch(Exception e)
		{
			e.printStackTrace();
		}	
   return scores;	
  }
  
  //Setters
  public void SetPlayerName(String Name) 
  {
	 ScoreManager.playerName = Name;
  }
  public String GetPlayerName()
  {
	  return playerName;
  }
  public int getScore() { return score; }
  public void GameScoreEvent()
  {
		  writeScore();
  }

  //Setters
  public void increaseScore(int Snum) 
  	{
		score += Snum; 
	}
  public void ResetScore()
  {
	  score = 0;  
  }

  public void writeScore() {
    List<PlayerScore> scores = getScores();
    PlayerScore PS = new PlayerScore(playerName, score);
    scores.add(PS);
    scores = scores
             .stream()
             .sorted(scoreSorter)
             .collect(Collectors.toList());
		writeScores(scores);
            
  }

  private void writeScores(final List<PlayerScore> playerScores){
    StringBuffer buffer = new StringBuffer();
    List<PlayerScore> top10 = playerScores
            .stream()
           .limit(10)
           .collect(Collectors.toList());
    boolean first = true;
    for (PlayerScore playerScore: top10) {
     if (first) {
        first = false;
      } else {
        buffer.append("\n");
      }
      buffer.append(playerScore.name+"="+playerScore.score);
    }
    try(OutputStream os = Files.newOutputStream(scoreFile)) {
      os.write(buffer.toString().getBytes());
    }
    catch(Exception e)
	{
		e.printStackTrace();
	}	
  }
  
  public static class PlayerScore {
    PlayerScore (String name, int score) {
      this.name = name;
      this.score = score;
    }
    private String name= "";
    private Integer score = 0;
    
    public String GetName()
    {
    	return name;
    }
   public Integer GetScore()
    {
        return score;
    }
  } 
}