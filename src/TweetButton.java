public class TweetButton extends Button{
    
    private Tweet tweet;
    private int candidate;
    private boolean needsRefresh = false;
    private boolean isMoving;
    private boolean isSpawning = true;
    float defaultX, defaultY;
    
    public TweetButton(float x, float y, float w, float h, int id, int candidate) {
        super(x, y, w, h, id);
        defaultX = x;
        defaultY = y;
        this.candidate = candidate;
        textSize = 10;
        tweet = BirdBrains.TWITS.getTweet(candidate);
        text = tweet.getMessage();
    }
    
    public TweetButton(){super();}
    
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
    
    public void setIsMoving() {
        isMoving = true;
    }
    
    public boolean getIsMoving(){return isMoving;}
    
    public void moveTo() {
        float xSpeed;
        float ySpeed = (BirdBrains.GAME.height/3 - y) / 10;
        
        if(id <= 3) {
            xSpeed = (BirdBrains.GAME.width/3 - x) / 10;
            if(x <= BirdBrains.GAME.width/3 - 1) {
                x += xSpeed;
                y += ySpeed;
            } else {
                isMoving = false;
                x = defaultX;
                y = defaultY;
                refreshTweet();
            }
        } else if(id >= 4) {
            xSpeed = (BirdBrains.GAME.width/2 - x) / 10;
            if(x >= BirdBrains.GAME.width/2 + 1) {
                x += xSpeed;
                y += ySpeed;
            } else {
                isMoving = false;
                x = defaultX;
                y = defaultY;
                refreshTweet();
            }
        }
    }
    
    public void spawn() {
        if(id <= 3 && x <= BirdBrains.GAME.width * 0.1f) {
            x += 20;
        } else if (id >= 4 && x >= BirdBrains.GAME.width * 0.9f - 200) {
            x -= 20;
        }
    }
    
    @Override
    public void draw() {
        if(isMoving) {
            moveTo();
        }
        //spawn();
        super.draw();
    }
}
