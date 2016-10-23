public class TweetButton extends Button{
    
    private Tweet tweet;
    private int candidate;
    private boolean needsRefresh = false;
    
    public TweetButton(float x, float y, float w, float h, int id, int candidate) {
        super(x, y, w, h, id);
        this.candidate = candidate;
        textSize = 10;
        tweet = BirdBrains.TWITS.getTweet(candidate);
        text = tweet.getMessage();
    }
    
    public void refreshTweet(){
        tweet = BirdBrains.TWITS.getTweet(candidate);
        text = tweet.getMessage();
    }
    
    public int getDamage(){
        int dmg = tweet.getRetweets();
        if(dmg>100)
            dmg/=10;
        else
            dmg = (int)((dmg + 20) * Math.max(0.5, Math.random()));
        return dmg;
    }

}
