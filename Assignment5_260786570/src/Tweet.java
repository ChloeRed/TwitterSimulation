import java.util.HashSet;
import java.text.SimpleDateFormat;
import java.util.Date; 
import java.io.*;

//Chloé Reddy
//260786570

public class Tweet {
	String userAccount;
	String date; 
	String time; 
	String message;
	private static HashSet<String> stopWords; 
	
	public Tweet (String userAccount, String date, String time, String message) {
		//constructor 
		this.userAccount = userAccount; 
		this.date = date; 
		this.time= time;
		this.message = message; 
	}
	
	public boolean checkMessage () {
		//throwing a nullpointerexception if stop words is not loaded
		
		if (stopWords == null) {
			throw new NullPointerException(); 
		}
		
		//making sure words are still considered if they have punctuation attached 
		message = message.replace(".","");
		message = message.replace(",","");
		message = message.replace(";","");
		message = message.replace(":","");
		
		int wordCount = 0;
		
		//Splitting the message into words
		for (String words: message.split(" ")) {
			//checking for stop words and adding to wordCount if the word is not a stop word
			if (!isStopWord (words)) {
				wordCount++;
			}
		}
		
		if (wordCount <= 0 ) {
			return false; 
		}else if (wordCount > 15){
			return false; 
		}else {
			return true;
		}
		
	}
	
	//get methods
	public String getDate(){
		return this.date;
	}
	
	public String getTime(){
		return this.time;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public String getUserAccount () {
		return this.userAccount;
	}
	
	//concatenating the attributes into a total string 
	public String toString () {
		String total = userAccount + "\t" + date + "\t" + time + "\t" + message;
		return total;
	}
	public boolean isBefore(Tweet tweet) {
		try { 
			//to avoid ParseException
			
			//separating the date and time from the Tweet and then concatenating them 
			String date = tweet.getDate();
			String time = tweet.getTime(); 
			String dateTime = ""  +date + " " + time; 
			
			//using date class to compare the dates 
			
			//using the Date class to get the specific date and time in the correct format
			Date previousDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
			String nextDateTime = ""+this.date+" " +this.time;
			Date nextDate = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss").parse(nextDateTime);
			
			//returning the earlier tweet 
			if(nextDate.before(previousDate)) {
				return true;
			}else {
				return false;
			}
	
		}catch (Exception e) {
			//catching exceptions
			System.out.print("woops");
			return false;
		}
	}
	
	public static void loadStopWords (String fileName) {
		//loading the file reader 
		FileReader fr;
		BufferedReader br; 
		//creating a temporary hash set 
		HashSet<String> temp = new HashSet<String>();
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			
			//reading each line of the file 
			String s = br.readLine();
			while (s != null) {
				temp.add(s);
				s = br.readLine();
			}
			stopWords = temp; 
			br.close();
			fr.close();
		}catch (IOException e) {
			System.out.println("This should not print");
		}catch(NullPointerException e) {
			System.out.println("Please don't print");
		}
	}
	
	//helper method
	public static boolean isStopWord (String s) {
		//Testing to see if a specific word is a stop word
		for (String word: stopWords) {
			if (s.equalsIgnoreCase(word)) {
				return true;
			}
		}
		
		return false; 
	}
		
}

