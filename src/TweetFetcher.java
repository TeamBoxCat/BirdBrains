import java.util.ArrayList;
import java.util.Collections;
import processing.core.PApplet;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetFetcher{
	private ArrayList<Query> queryList = new ArrayList<Query>();
	private Twitter twitter;
	private ConfigurationBuilder configBuilder;
	private ArrayList<ArrayList<Tweet>> tweetSuperList = new ArrayList<ArrayList<Tweet>>();
	
	public TweetFetcher(){
		//Make Twitter great again (initialises Twitter object using authKeys)
		configBuilder = new ConfigurationBuilder(); 
		configBuilder.setOAuthConsumerKey("SERAfLQVyIQ9qBX5rUj0scSWS"); 
		configBuilder.setOAuthConsumerSecret("ZO11i1HD73djcklwkXT3IKCrQd0HvUJiDvdV1IUjZN4O3rhYZg"); 
		configBuilder.setOAuthAccessToken("4342428493-asxXAFk0hW020ooYVLSShnR9K5iRE9lDxYtTWD8"); 
		configBuilder.setOAuthAccessTokenSecret("VB6QJzCA8zteayYyb4L8BnIr9gpUeP6pFapcrdu3jNH4l");
		twitter = new TwitterFactory(configBuilder.build()).getInstance();
	}
	
	public void loadTweets() throws Exception{
		ArrayList<QueryResult> queryResultList = new ArrayList<QueryResult>();
		for (Query query : queryList) {
			queryResultList.add(twitter.search(query));
		}
		
		for (QueryResult queryResult : queryResultList) {
			ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
	  		ArrayList<Status> statusList = (ArrayList<Status>) queryResult.getTweets();
	  		System.out.println(statusList);
	  			
	  		for (Status status : statusList) {  
 				Tweet newTweet = new Tweet(status.getText(), status.getRetweetCount(), status.getUser().getName());
 				System.out.print(newTweet);
 				tweetList.add(newTweet);
  	  		}
	  		tweetSuperList.add(tweetList);
	  	}
	}
	
	public Tweet getTweet(int queryNumber){
		ArrayList<Tweet> selectedQuery = tweetSuperList.get(queryNumber);
		Collections.shuffle(selectedQuery);
		Tweet selectedTweet = selectedQuery.get(0);
		selectedQuery.remove(0);
		return selectedTweet;
	}
	
	public void addQuery(String searchTerm, int resultAmount){
		queryList.add(new Query(searchTerm));
		queryList.get(queryList.size() - 1).setCount(resultAmount);
	}
}