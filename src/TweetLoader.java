
public class TweetLoader implements Runnable{
    public boolean isComplete = false;
    public boolean isError = false;
    
    @Override
    public void run() {
        try{
            BirdBrains.TWITS.loadTweets();
        }
        catch(Exception e){
            isError = true;
        }
        isComplete = true;
    }
    
    
}
