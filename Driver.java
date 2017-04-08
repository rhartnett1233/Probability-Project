import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.net.*;

public class Driver{

    public static final double INCREMENT = 1E-4;
    public static final double EXP = 2.718281828;
    public static final double SQRT2PI = 2.506628275;

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

        ListIterator<Team> iter = teamList.listIterator();
        while(iter.hasNext()){
            Team curTeam = iter.next();
            Team nextTeam = null;
            ListIterator<Team> tempIter = teamList.listIterator();
            while(tempIter.hasNext()){
                Team temp = tempIter.next();
                if(temp.getName().equals(curTeam.getNextGame())){
                    nextTeam = temp;
                    break;
                }
            }

            System.out.println(curTeam.getName() + " vs " + nextTeam.getName());
            System.out.println("CurTeam PPG and std = " + curTeam.getAvgPPG() + " " + curTeam.getPPGStdDev());
            System.out.println("NextTeam PPG and std = " + nextTeam.getAvgPPG() + " " + nextTeam.getPPGStdDev());
            double xx = findIntersect(curTeam.getAvgPPG(), curTeam.getPPGStdDev(), nextTeam.getAvgPPG(), nextTeam.getPPGStdDev());
            double area = overlapArea(xx, curTeam.getAvgPPG(), curTeam.getPPGStdDev(), nextTeam.getAvgPPG(), nextTeam.getPPGStdDev());
            System.out.println("intersection = " + xx + "\t area = " + area);
            System.out.println("----------------------------");
        }

	}

    /*************************************************************************
    *This function finds the intersect of the two gaussian distributions     *
    *************************************************************************/
    public static double findIntersect(double u1, double o1, double u2, double o2){
        double a = 1/(2*Math.pow(o1,2)) - 1/(2*Math.pow(o2,2));
        double b = u2/(Math.pow(o2,2)) - u1/(Math.pow(o1,2));
        double c = Math.pow(u1,2)/(2*Math.pow(o1,2)) - Math.pow(u2,2)/(2*Math.pow(o2,2)) - Math.log(11.14/9.975);
        double result = (b*b - 4*a*c);
        if(result > 0){
            double result1 = (-1*b+Math.sqrt(result))/(2*a);
            double result2 = (-1*b-Math.sqrt(result))/(2*a);
            System.out.println("intersection info = " + result1 + "\t " + result2);
            if(result1 < result2){
                return result1;
            }
            else{
                return result2;
            }
        } 
        return 0.0;
    }


    /*************************************************************************
    *This function is how we are going to solve the integral we just not how *
    *to find out what the bounds of the integral are                         *
    *************************************************************************/
    public static double integral(double a, double b, Function function){
        double area = 0;
        double modifier = 1;
        if(a>b){
            double temp = a;
            a = b;
            b = temp;
        }
        for(double i = a+INCREMENT; i<b; i+=INCREMENT){
            double dFromA = i-a;
            area += (INCREMENT/2)*(function.f(a+dFromA) + function.f(a+dFromA-INCREMENT));
        }
        return area;
    }


    /*************************************************************************
    *This function finds the area of the overlapping section, this will tell *
    *us the probability of the team with less average points winning         *
    *************************************************************************/
    public static double overlapArea(double intersect, double u1, double o1, double u2, double o2){
        double xx = findIntersect(u1, o1, u2, o2);
        double area;
        if(u1 < u2){
            /*double int1 = integral(intersect, 300, x -> {
                return 1/(o1*SQRT2PI)*Math.pow(EXP, -Math.pow((x-u1), 2)/(2*o1*o1));
            });*/
            double int2 = integral(-100, intersect, x -> {
                return 1/(o2*SQRT2PI)*Math.pow(EXP, -Math.pow((x-u2), 2)/(2*o2*o2));
            });

            area = int2;
        }
        else{
            double int1 = integral(-100, intersect, x -> {
                return 1/(o1*SQRT2PI)*Math.pow(EXP, -Math.pow((x-u1), 2)/(2*o1*o1));
            });
            /*double int2 = integral(intersect, 300, x -> {
                return 1/(o2*SQRT2PI)*Math.pow(EXP, -Math.pow((x-u2), 2)/(2*o2*o2));
            });*/

            area = int1;
        }
        return area;
    }
	
}