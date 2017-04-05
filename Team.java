import java.util.*;

public class Team{

	private String name;
	private double avgPPG;
	private double avgPAPG;
	private Game[] gameList;
	private char[] data;
	private int gameIndex;

	public Team(String n, char[] d){
		name = n;
		avgPPG = 0;
		avgPAPG = 0;
		gameList = new Game[82];
		data = d;
		gameIndex = 0;

		readData();

		System.out.println(name);
		System.out.println("----------------------------");
		
		calcAvgPPG();
		calcAvgPAPG();
		System.out.println("---------------------------------");
	}


	public void printData(){
		System.out.println("Name: " + name + "   Average PPG: " + avgPPG + "   Average PAPG: " + avgPAPG);
	}


	public void readData(){

		boolean doneDate = false;
		boolean donePoints = false;
		boolean doneOTeam = false;
		boolean doneOPoints = false;

		int index = 0;

		while(index < data.length){
			//System.out.print(data[index]);
			if(doneDate == false){
				if(data[index] == '[' && data[index+1] == '2'){
					index = readInDate(index+1);
					doneDate = true;
				}
			}
			else if(donePoints == false){
				if(data[index] == '['){
					index = readInPoints(index+1);
					donePoints = true;
				}
			}
			else if(doneOTeam == false){
				if(data[index] == '['){
					index = readInOTeams(index+1);
					doneOTeam = true;
				}
			}
			else if(doneOPoints == false){
				if(data[index] == '['){
					index = readInOPoints(index+1);
					doneOPoints = true;
				}
			}

			index++;
		}

		//System.out.println("--------------------");
	}

	public int readInDate(int index){
		long gameDate;
		char[] tempDate = new char[8];
		int i;
		int tempIndex = 0;
		for(i = index; i < data.length; i++){
			if(data[i] == ','){
				Game curGame = new Game();
				gameDate = Long.parseLong(new String(tempDate));
				curGame.setDate(gameDate);
				gameList[gameIndex] = curGame;
				tempDate = new char[8];
				gameIndex++;
				tempIndex = 0;
			}
			else if(data[i] == ']'){
				Game curGame = new Game();
				gameDate = Long.parseLong(new String(tempDate));
				curGame.setDate(gameDate);
				gameList[gameIndex] = curGame;
				tempDate = new char[8];
				gameIndex++;
				tempIndex = 0;
				break;
			}
			else{
				tempDate[tempIndex++] = data[i];
			}
		}
		gameIndex = 0;
		return i;
	}

	public int readInPoints(int index){
		int gamePoints;
		char[] tempPoints = new char[3];
		int i;
		int tempIndex = 0;
		for(i = index; i < data.length; i++){
			if(data[i] == ','){
				Game curGame = gameList[gameIndex++];
				if(tempIndex == 2){
					char[] xx = new char[2];
					xx[0] = tempPoints[0];
					xx[1] = tempPoints[1];
					gamePoints = Integer.parseInt(new String(xx));
				}
				else{
					gamePoints = Integer.parseInt(new String(tempPoints));
				}
				
				curGame.setPoints(gamePoints);
				tempPoints = new char[3];
				//System.out.println(gamePoints);
				tempIndex = 0;
			}
			else if(data[i] == 'n'){
				break;
			}
			else if(data[i] == ']'){
				Game curGame = gameList[gameIndex++];
				if(tempIndex == 2){
					char[] xx = new char[2];
					xx[0] = tempPoints[0];
					xx[1] = tempPoints[1];
					gamePoints = Integer.parseInt(new String(xx));
				}
				else{
					gamePoints = Integer.parseInt(new String(tempPoints));
				}
				
				curGame.setPoints(gamePoints);
				tempPoints = new char[3];
				//System.out.println(gamePoints);
				tempIndex = 0;
				break;
			}
			else{
				tempPoints[tempIndex++] = data[i];
			}
		}
		gameIndex = 0;
		return i;
	}


	public int readInOTeams(int index){
		String teamName;
		char[] tempOTeam = new char[20];
		int i;
		int tempIndex = 0;
		for(i = index; i < data.length; i++){
			if(data[i] == ','){
				Game curGame = gameList[gameIndex++];
				char[] xx = new char[tempIndex];
				for(int j = 0; j < xx.length; j++)
					xx[j] = tempOTeam[j];
				teamName = new String(xx);
				curGame.setOTeam(teamName);
				//System.out.println(teamName + " size " + teamName.length());
				tempOTeam = new char[20];
				tempIndex = 0; 
			}
			else if(data[i] == ']'){
				Game curGame = gameList[gameIndex++];
				char[] xx = new char[tempIndex];
				for(int j = 0; j < xx.length; j++)
					xx[j] = tempOTeam[j];
				teamName = new String(xx);
				curGame.setOTeam(teamName);
				//System.out.println(teamName + " size " + teamName.length());
				tempOTeam = new char[20];
				tempIndex = 0;
				break;
			}
			else if(data[i] != '\'')
				tempOTeam[tempIndex++] = data[i];
		}
		gameIndex = 0;
		return i;
	}


	public int readInOPoints(int index){
		int gamePoints;
		char[] tempPoints = new char[3];
		int i;
		int tempIndex = 0;
		for(i = index; i < data.length; i++){
			if(data[i] == ','){
				Game curGame = gameList[gameIndex++];
				if(tempIndex == 2){
					char[] xx = new char[2];
					xx[0] = tempPoints[0];
					xx[1] = tempPoints[1];
					gamePoints = Integer.parseInt(new String(xx));
				}
				else{
					gamePoints = Integer.parseInt(new String(tempPoints));
				}
				
				curGame.setOPoints(gamePoints);
				tempPoints = new char[3];
				//System.out.println(gamePoints);
				tempIndex = 0;
			}
			else if(data[i] == 'n'){
				break;
			}
			else if(data[i] == ']'){
				Game curGame = gameList[gameIndex++];
				if(tempIndex == 2){
					char[] xx = new char[2];
					xx[0] = tempPoints[0];
					xx[1] = tempPoints[1];
					gamePoints = Integer.parseInt(new String(xx));
				}
				else{
					gamePoints = Integer.parseInt(new String(tempPoints));
				}
				
				curGame.setOPoints(gamePoints);
				tempPoints = new char[3];
				//System.out.println(gamePoints);
				tempIndex = 0;
				break;
			}
			else{
				tempPoints[tempIndex++] = data[i];
			}
		}
		gameIndex = 0;
		return i;
	}


	public void calcAvgPPG(){
		int result = 0;
		int count = 0;
		int scale = 0;
		for(int i = 0; i < gameList.length; i++){
			if(!name.equals("Timberwolves") && !name.equals("Trailblazers")){
				if(gameList[i].getPoints() != 0){
					result = result + gameList[i].getPoints();
					count++;
				}
			}
			else{
				if(i < gameList.length-1){
					if(gameList[i].getPoints() != 0){
						result = result + gameList[i].getPoints();
						count++;
					}
				}
				else{
					i++;
				}
			}
			
		}
		avgPPG = (double)(result)/(double)(count);
		System.out.println(avgPPG);
		//System.out.println("----------------------------");
	}

	public void calcAvgPAPG(){
		int result = 0;
		int count = 0;
		for(int i = 0; i < gameList.length; i++){
			if(!name.equals("Timberwolves") && !name.equals("Trailblazers")){
				if(gameList[i].getOPoints() != 0){
					result = result + gameList[i].getOPoints();
					count++;
				}
			}
			else{
				if(i < gameList.length-1){
					if(gameList[i].getOPoints() != 0){
						result = result + gameList[i].getOPoints();
						count++;
					}
				}
				else{
					i++;
				}
			}
		}
		avgPAPG = (double)(result)/(double)(count);
		System.out.println(avgPAPG);
		//System.out.println("Average PAPG: " + avgPAPG);
	}

	public String getName(){
		return name;
	}

	public void setName(String n){
		name = n;
	}

	public double getAvgPPG(){
		return avgPPG;
	}

	public void setAvgPPG(double xx){
		avgPPG = xx;
	}

	public double getAvgPAPG(){
		return avgPAPG;
	}

	public void setAvgPAPG(double yy){
		avgPAPG = yy;
	}

	public Game[] getGameList(){
		return gameList;
	}

	public void setGameList(Game[] game){
		gameList = game;
	}

}
