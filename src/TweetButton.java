
import java.awt.Color;
import static processing.core.PConstants.*;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;

public class TweetButton extends Button{
    
    private Tweet tweet;
    private int candidate;
    private boolean needsRefresh = false;
    private boolean isMoving;
    private boolean isSpawning = true;
    private float originalX, originalY;
    private PImage egg = BirdBrains.GAME.loadImage("EggNude.png");
    private PFont font = font = BirdBrains.GAME.createFont("GothamNarrow-Book+SegoeUIEmoji.ttf", 32);
    private PVector color;
    
    public TweetButton(float x, float y, float w, float h, int id, int candidate) {
        super(x, y, w, h, id);
        originalX = x;
        originalY = y;
        if(candidate == BirdBrains.GAME.HILLARY) 
            super.x = -(x+w)-100;
        else 
            super.x = BirdBrains.GAME.width+100;
        this.candidate = candidate;
        textSize = 10;
        tweet = BirdBrains.TWITS.getTweet(candidate);
        text = tweet.getMessage();
        color = new PVector();
        color.x = BirdBrains.GAME.random(255);
        color.y = BirdBrains.GAME.random(255);
        color.z = BirdBrains.GAME.random(255);
    }
    
    public TweetButton(){super();}
    
    public void refreshTweet(){
        tweet = BirdBrains.TWITS.getTweet(candidate);
        text = tweet.getMessage();
        color.x = BirdBrains.GAME.random(255);
        color.y = BirdBrains.GAME.random(255);
        color.z = BirdBrains.GAME.random(255);
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
    public boolean getIsSpawning(){return isSpawning;}
    
    public void selectTweet() {
        float xSpeed = (BirdBrains.GAME.width/3 - x) / 10;
        float ySpeed = (BirdBrains.GAME.height/5 - y) / 10;
        
        if(candidate == BirdBrains.GAME.HILLARY) {
            if(x <= BirdBrains.GAME.width/3 - 1) {
                x += xSpeed;
                y += ySpeed;
            } else {
                isMoving = false;
                x = -(originalX+w)-100;
                y = originalY;
                refreshTweet();
                isSpawning = true;
            }
        } else {
            if(x >= BirdBrains.GAME.width/3 + 1) {
                x += xSpeed;
                y += ySpeed;
            } else {
                isMoving = false;
                x = BirdBrains.GAME.width+100;
                y = originalY;
                refreshTweet();
                isSpawning = true;
            }
        }
    }
    
    public void spawn() {
        if(candidate == BirdBrains.GAME.HILLARY && x <= originalX) {
            x += 20;
        } else if (candidate == BirdBrains.GAME.TRUMP && x >= originalX) {
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
            
            if(candidate == BirdBrains.GAME.HILLARY) {
                BirdBrains.GAME.rect(x, y, w, h, 5);
                BirdBrains.GAME.triangle(x + w*.08f, y + h, x + w*.14f, y + h, x + w*.11f, y + h + h*.08f);
            } else {
                BirdBrains.GAME.rect(x, y, w, h, 5);
                BirdBrains.GAME.triangle(x + w*.92f, y + h, x + w - w*.14f, y + h, x + w - w*.11f, y + h + h*.08f);
            }
            BirdBrains.GAME.fill(color.x, color.y, color.z);
            BirdBrains.GAME.rect(x + w*.02f, y + h*.05f, w*.1f, h*.27f, 10);
            BirdBrains.GAME.image(egg, x + w*.02f , y + h*.05f, w*.1f, h*.27f);
            
            BirdBrains.GAME.textFont(font);
            BirdBrains.GAME.textAlign(LEFT);
            BirdBrains.GAME.textSize(w*.06f);
            BirdBrains.GAME.fill(41,47,51);
            BirdBrains.GAME.text(tweet.getName(), x + w*.13f, y + h*.08f, w - w*.1f, h - h*.27f);
            BirdBrains.GAME.textSize(w*.036f);
            BirdBrains.GAME.fill(0,102,153);
            BirdBrains.GAME.text(tweet.getUserName(), x + w*.13f, y + h*.25f, w - w*.1f, h - h*.27f);
            BirdBrains.GAME.fill(0);
            BirdBrains.GAME.textSize(w*.042f);
            BirdBrains.GAME.text(tweet.getMessage(), x + w*.01f, y + h*.4f, w - w*.02f, h - h*.324f);
        }
        if(isMoving) {
            selectTweet();
        }
        
        if(isSpawning) {
            spawn();
        }
    }
}