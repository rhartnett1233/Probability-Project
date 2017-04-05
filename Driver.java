import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.net.*;

public class Driver{

	public static void main(String[] args) throws IOException{

		LinkedList<Team> teamList = new LinkedList<Team>();
		String[] teamNames = {"Celtics", "Hawks", "Nets", "Hornets", "Bulls", "Cavaliers", "Mavericks", "Nuggets", 
								"Pistons", "Warriors", "Rockets", "Pacers", "Clippers", "Lakers", "Grizzlies", 
								"Heat", "Bucks", "Timberwolves", "Pelicans", "Knicks", "Thunder", "Magic", 
								"Seventysixers", "Suns", "Trailblazers", "Kings", "Spurs", "Raptors", "Jazz",
								"Wizards"};


		FileWriter file1 = null;
		BufferedWriter buf = null;
		String resLine = null;

		Scanner scan = new Scanner(System.in);
		System.out.println("Enter 1 to refresh information (takes a few minutes) or 0 to begin program:");
		int choice = scan.nextInt();


		//read in information from the database
		if(choice == 1){

			try{
			

				for(int i = 0; i < teamNames.length; i++){
					//date,points,o:team,o:points@team=Celtics and season=2016
					URL url = new URL("http://api.sportsdatabase.com/nba/query.json?sdql=date%2Cpoints%2Co%3Ateam%2Co%3Apoints%40team%3D"+teamNames[i]+"%20and%20season%3D2016&output=json&api_key=guest");
					URLConnection con = url.openConnection();
					InputStream is = con.getInputStream();

					BufferedReader br = new BufferedReader(new InputStreamReader(is));

					String line = null;
				

					while ((line = br.readLine()) != null) {
    					resLine  = resLine + line;
        			}
        			resLine = resLine + "\n";

        			TimeUnit.SECONDS.sleep(2);
        		
        		}

        		System.out.println("----------------------------------------------------------------------------");
        		System.out.println("Loaded in successfull");
        		TimeUnit.SECONDS.sleep(5);

    			System.out.println(resLine);

        	} catch (Exception e){
        		System.out.println("Error Refreshing Data, Restart Program and Try Again!");
        	}


        	try{
        		file1 = new FileWriter("storage.txt");
				buf = new BufferedWriter(file1);

				buf.write(resLine);
        	} catch (Exception e){
        		System.out.println("writing error");
        	}

        	buf.close();
    		file1.close();

		}

		//store information to storage.txt
		FileReader inputStream = null;

        try {
            inputStream = new FileReader("storage.txt");

            int c;
            char curChar;
            char[] data = new char[2500];
            int index = 0;
            int count = 0;
            Team curTeam;

            while ((c = inputStream.read()) != -1) {

            	curChar = (char)c;
            	if(curChar == '\n'){
            		curTeam = new Team(teamNames[count], data);
            		data = new char[2500];
            		count++;
            		index = 0;
            		teamList.add(curTeam);
            		//System.out.println(count);
            	}
            	else
            		data[index++] = curChar;            	
                
            }

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        /*for(int i = 0; i < teamList.size(); i++){
        	if(teamList.get(i) != null){
        		System.out.print("index: " + i + "\t");
        		teamList.get(i).printData();
        	}
        }*/
	}
	
}