
public class Tweet {
	private String message;
	private int retweets;
	private String userName;
	private String name;
	
	public Tweet(String message, int retweets, String userName, String name) {
		this.message = message;
		this.retweets = retweets;
		this.userName = userName;
		this.name = name;
	}
	
	public String getMessage(){
		return message;
	}
	public int getRetweets(){
		return retweets;
	}
	public String getUserName(){
		return userName;
	}
	public String getName(){
		return name;
	}
	
	@Override 
	public String toString() {
		return "[" + userName + "/" + name + "] " + message + "(got " + retweets + " retweets)";
	}
}
