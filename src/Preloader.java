
import processing.core.PImage;

public class Preloader {

    private PImage background;
    private TextElement errorMsg = new TextElement(BirdBrains.GAME.width * 0.5f, BirdBrains.GAME.height * 0.5f, 20, "Error: Please check your internet connection and retry");
    private TextElement loading = new TextElement(BirdBrains.GAME.width * 0.5f, BirdBrains.GAME.height * 0.5f, 40, "Loading");
    private Button retry = new Button(BirdBrains.GAME.width * 0.6f, BirdBrains.GAME.height * 0.6f, BirdBrains.GAME.width * 0.05f, BirdBrains.GAME.height * 0.08f, "Retry", 1);
    private TweetLoader loader;
    private int count = 0;
    private boolean isLoading = false;
    private boolean needLoad = true;

    public Preloader() {
        loader = new TweetLoader();
    }

    public void draw() {
        if (!isLoading && needLoad) {
            new Thread(loader).start();
            isLoading = true;
            needLoad = false;
        }
        if (isLoading) {
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

            loading.draw();
            
            if(loader.isComplete){
                isLoading = false;
                if(!loader.isError)
                    BirdBrains.GAME.currentLevel = BirdBrains.GAME.MENU;
            }
                
        }

    }
}
