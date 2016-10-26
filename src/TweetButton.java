
import static processing.core.PConstants.*;
import processing.core.PFont;
import processing.core.PImage;

public class TweetButton extends Button{
    
    private Tweet tweet;
    private int candidate;
    private boolean needsRefresh = false;
    private boolean isMoving;
    private boolean isSpawning = true;
    float defaultX, defaultY;
    PImage egg = BirdBrains.GAME.loadImage("EggNude.png");
    PFont font = font = BirdBrains.GAME.createFont("Gotham Narrow Book.otf", 32);
    
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
            dmg = (int)(dmg + 20);
        return dmg;
    }
    
    public void setIsMoving() {
        isMoving = true;
    }
    
    public boolean getIsMoving(){return isMoving;}
    
    public void selectTweet() {
        float xSpeed;
        float ySpeed = (BirdBrains.GAME.height/3 - y) / 10;
        
        if(candidate == BirdBrains.GAME.TRUMP) {
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
        } else {
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
        if(candidate == BirdBrains.GAME.TRUMP && x <= defaultX) {
            x += 20;
        } else if (candidate == BirdBrains.GAME.HILLARY && x >= defaultX) {
            x -= 20;
        } else {
            isSpawning = false;
            
        }
    }
    
    @Override
    public void draw() {
        if(active) {
            BirdBrains.GAME.fill(over() ? BirdBrains.GAME.color(220) : 255);
            BirdBrains.GAME.noStroke();
            
            if(candidate == BirdBrains.GAME.TRUMP) {
                BirdBrains.GAME.rect(x, y, w, h, 5);
                BirdBrains.GAME.triangle(x + 40, y + h, x + 70, y + h, x + 55, y + h + 15);
            } else {
                BirdBrains.GAME.rect(x, y, w, h, 5);
                BirdBrains.GAME.triangle(x + w - 40, y + h, x + w - 70, y + h, x + w - 55, y + h + 15);
            }
            // flashing eggs BEWARE!
            BirdBrains.GAME.fill(BirdBrains.GAME.random(255), BirdBrains.GAME.random(255), BirdBrains.GAME.random(255));
            BirdBrains.GAME.rect(x + 10, y + 10, 50, 50, 10);
            BirdBrains.GAME.image(egg, x + 10 , y + 10, 50, 50);
            
            BirdBrains.GAME.textFont(font);
            BirdBrains.GAME.textAlign(LEFT);
            BirdBrains.GAME.textSize(30);
            BirdBrains.GAME.fill(41,47,51);
            BirdBrains.GAME.text(tweet.getName(), x + 65, y + 15, w - 50, h - 50);
            BirdBrains.GAME.textSize(18);
            BirdBrains.GAME.fill(0,102,153);
            BirdBrains.GAME.text(tweet.getUserName(), x + 65, y + 40, w - 50, h - 50);
            BirdBrains.GAME.fill(0);
            BirdBrains.GAME.textSize(21);
            BirdBrains.GAME.text(tweet.getMessage(), x + 5, y + 65, w - 10, h - 60);
        }
        if(isMoving) {
            selectTweet();
        }
        
//        if(isSpawning) {
//            spawn();
//        }
    }
}