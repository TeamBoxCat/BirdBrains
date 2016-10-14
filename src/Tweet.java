
public class Tweet {
	private String message;
	private int retweets;
	private String userName;
	
	public Tweet(String message, int retweets, String userName) {
		this.message = message;
		this.retweets = retweets;
		this.userName = userName;
	}
	
	public String getMessage(){
		return message;
	}
	public int getRetweets(){
		return retweets;
	}
	public String getName(){
		return userName;
	}
	
	@Override 
	public String toString() {
		return "[" + userName + "] " + message + "(got " + retweets + " retweets)";
	}
}
