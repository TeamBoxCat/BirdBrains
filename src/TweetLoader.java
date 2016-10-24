
public class TweetLoader implements Runnable{
    public boolean isComplete = false;
    public boolean isError = false;
    
    @Override
    public void run() {
        try{
            BirdBrains.TWITS = new TweetFetcher();
            BirdBrains.TWITS.addQuery("@realDonaldTrump #dumptrump -http -https -RT", 200);
            BirdBrains.TWITS.addQuery("@HillaryClinton #ImNotWithHer -http -https -RT", 200);
            BirdBrains.TWITS.loadTweets();
        }
        catch(Exception e){
            isError = true;
        }
        isComplete = true;
    }
    
    
}
