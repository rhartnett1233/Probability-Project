import java.util.*;

public class Game{
	
	private long date;
	private int points;
	private String oTeam;
	private int oPoints;

	public Game(long day, int point, String team, int op){
		date = day;
		points = point;
		oTeam = team;
		oPoints = op;
	}

	public Game(){
		date = 0;
		points = 0;
		oTeam = null;
		oPoints = 0;
	}


	public long getDate(){
		return date;
	}

	public void setDate(long day){
		date = day;
	}

	public int getPoints(){
		return points;
	}

	public void setPoints(int point){
		points = point;
	}

	public String getOTeam(){
		return oTeam;
	}

	public void setOTeam(String team){
		oTeam = team;
	}

	public int getOPoints(){
		return oPoints;
	}

	public void setOPoints(int op){
		oPoints = op;
	}
}