import java.util.ArrayList;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap; 

public class Twitter {
	private ArrayList<Tweet> tweets;
	
	public Twitter() {
		this.tweets = new ArrayList<Tweet> (); 
		
	}
	
	public static void main (String []args) {
	
		 Twitter example = new Twitter();
		 Tweet.loadStopWords("stopWords.txt");
         example.loadDB("tweets.txt");
		}
	
	
	
	public void loadDB (String fileName) {
		try {
			//loading the file reader
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			//reading the tweets 
			String currentLine = br.readLine();
			while (currentLine != null) {
				//creating an array with the four separate attributes as elements  
				String[] item = currentLine.split("\t");
				Tweet currentTweet = new Tweet (item[0], item[1], item[2], item[3]);
				//adding the tweets 
				if(currentTweet.checkMessage()) {
					this.tweets.add(currentTweet);
				}
				currentLine = br.readLine();
			}
			br.close();
			fr.close();
		}catch (IOException e) {
			System.out.println("No thank you");
		}catch(NullPointerException e) {
			System.out.println("Oh no");
		}
		//sorting the tweets 
		sortTwitter(); 
	}
	
	public void sortTwitter(){
		try{
			//
			int del = 0;
			while (del<this.tweets.size()-1) {
				int minElement= del;
				for(int i =del; i<this.tweets.size(); i++) {
					
					//separating the date and time from the Tweet and then concatenating them 
					String date = this.tweets.get(i).getDate();
					String time = this.tweets.get(i).getTime();
					String dateTime = ""+date+" "+time; 
					
					//using the Date class to get the specific date and time in the correct format
					Date previousDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
					
					//separating the date and time from the Tweet and then concatenating them 
					String nextDate = this.tweets.get(minElement).getDate();
					String nextTime = this.tweets.get(minElement).getTime();
					String nextDateTime = ""+nextDate+" "+nextTime; 
					
					//using the Date class to get the specific date and time in the correct format
					Date nextDates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(nextDateTime);
					
					//checking to see which date comes first
					if (previousDate.before(nextDates)) {
						minElement = i;
					}
				}
				//switching the placement of the tweets if necessary 
				Tweet temp= this.tweets.get(del);
				this.tweets.set(del, tweets.get(minElement));
				this.tweets.set(minElement, temp);
				del++;
			}
		}catch (Exception e) {
			System.out.print("Something went wrong");
		}	
	}
	
	//get methods
	public int getSizeTwitter() {
		return this.tweets.size();
	}
	
	public Tweet getTweet(Integer index) {
		return this.tweets.get(index);  
	}
	
	
	public String printDB () {
		//returning nothing if tweets doesn't exist
		if (this.tweets.isEmpty()) {
			return "";
	}else {
		// returning the tweets into a string 
		String s = ""; 
		for (int i = 0; i <this.tweets.size(); i++) {
			Tweet theTweet = this.tweets.get(i);
			String temp = theTweet.toString();
			s = s + temp + "\n";
			}
			return s; 
				
			}
		 
		
		
	
	}
	
	public ArrayList<Tweet> rangeTweets(Tweet tweet1, Tweet tweet2)  {
		//creating a new array list 
		ArrayList<Tweet> range = new ArrayList<Tweet>(); 
		//sorting the tweets
		sortTwitter();
		if (tweet1.isBefore(tweet2)) {
			//returning the tweets between the indices of of the two inputed tweets 
			int indexOfTweet1 = this.tweets.indexOf(tweet1);
			for (int i = indexOfTweet1; i<this.tweets.size(); i++) {
				Tweet newTweet = this.tweets.get(i);
				if(newTweet == tweet2) {
					range.add(newTweet); 
					break; 
				}
				range.add(newTweet);
			} 
			return range; 
		}else if(tweet2.isBefore(tweet1)){
			int indexTweet2 = this.tweets.indexOf(tweet2);
			for (int i = indexTweet2; i<this.tweets.size(); i++) {
				Tweet newTweet = this.tweets.get(i);
				if(newTweet == tweet1) {
					range.add(newTweet); 
					break; 
				}
				range.add(newTweet);
				
		}
			return range;
		 
		}
		return range;
	
	}
	public void saveDB(String fileName)  {
		try {
			//loading the file writer 
			FileWriter fw = new FileWriter (fileName);
			BufferedWriter bw = new BufferedWriter (fw);
			//using printDB to print on the new file
			bw.write(printDB());
			bw.close();
			fw.close(); 		
		}catch (IOException e) {
			System.out.println("This should not print SOS" );
		}
		
	}
	
	
	public String trendingTopic () {
		//creating a hash map
		HashMap<String,Integer> trendingTopic =  new HashMap <String,Integer> (); 
		
		for (int i = 0; i<this.tweets.size(); i++) {
			String message = this.tweets.get(i).getMessage();
			
			//making sure words are still considered if they have punctuation attached 
			message = message.replace(".","");
			message = message.replace(",","");
			message = message.replace(";","");
			message = message.replace(":","");
			
			//using a for each loop to check words 
			for (String word: message.split(" ")) {
				int numberOfWords; 
				
				//ignoring stop words
				if(!Tweet.isStopWord(word)) {
					//counting the number of time a specific word appears
					if(trendingTopic.containsKey(word)) {
						numberOfWords = trendingTopic.get(word) +1;
						trendingTopic.put(word, numberOfWords); 
					}else {
						numberOfWords = 1;
						trendingTopic.put(word, numberOfWords);
					}
				}
			}
			
		}
		
		//checking and returning the most common word
		int mostCommon = 0;
		String mostTrendingWord = "";
		for(String key:trendingTopic.keySet()) {
			if(trendingTopic.get(key) > mostCommon) {
				mostCommon = trendingTopic.get(key);
				mostTrendingWord= key;
			}
		}
		
		return mostTrendingWord;
	}
	
	
	
	
}
