
import java.util.LinkedList;
import java.util.Random;
import processing.core.PImage;

public class Preloader {

    private PImage background;
    private TextElement errorMsg = new TextElement(BirdBrains.GAME.width * 0.5f, BirdBrains.GAME.height * 0.4f, 30, "Error: Please check your internet connection and retry");
    private TextElement loading = new TextElement(BirdBrains.GAME.width * 0.5f, BirdBrains.GAME.height * 0.5f, 40, "Loading");
    private Button retry = new Button(BirdBrains.GAME.width * 0.5f - (BirdBrains.GAME.width * 0.07f)/2, BirdBrains.GAME.height * 0.5f, BirdBrains.GAME.width * 0.07f, BirdBrains.GAME.height * 0.08f, "Retry", 1);
    private TweetLoader loader;
    private int count = 0;
    private boolean isLoading = false;
    private boolean needLoad = true;

    public Preloader() {
        Random rand = new Random();
        
        loader = new TweetLoader();
        background = BirdBrains.GAME.loadImage("load" + (rand.nextInt(6)+1) + ".jpg");
        background.resize(BirdBrains.GAME.width, BirdBrains.GAME.height);
    }

    public void draw() {
        BirdBrains.GAME.background(background);
        if (!isLoading && needLoad) {
            new Thread(loader).start();
            isLoading = true;
            needLoad = false;
        }
        else if (isLoading) {
            if (loader.isComplete) {
                isLoading = false;
                if (!loader.isError) {
                    BirdBrains.GAME.currentLevel = BirdBrains.GAME.MENU;
                }
            }
            else{
                updateLoadTxt();
                loading.draw();
            }
        }
        else if(loader.isError){
            errorMsg.draw();
            retry.draw();
        }

    }

    private void updateLoadTxt() {
        if (count > 10) {
            if (!loading.text.equals("Loading...")) {
                loading.text += ".";
            } else {
                loading.text = "Loading";
            }
            count = 0;
        } else {
            count++;
        }
    }

    void mousePressed() {
        if(retry.over()){
            needLoad = true;
            loader = new TweetLoader();
        }
    }

}
