public class TweetButton extends Button{
    
    private Tweet tweet;
    
    public TweetButton(float x, float y, float w, float h, int id, int candidate) {
        super(x, y, w, h, id);
        tweet = BirdBrains.TWITS.getTweet(candidate);
        text = tweet.getMessage();
    }
    
}
