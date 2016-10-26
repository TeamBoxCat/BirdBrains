
import java.util.LinkedList;
import processing.core.PImage;

public class Level {

    int id;
    String name;
    private PImage background;
    private PImage title;
    private PImage subTitle;
    private LinkedList<TextElement> textElements = new LinkedList<TextElement>();
    private LinkedList<Sprite> sprites = new LinkedList<Sprite>();
    public LinkedList<Button> buttons = new LinkedList<Button>();

    public Level(int id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    public Level() {
        title = BirdBrains.GAME.loadImage("BirdBrainsTitle.png");
        subTitle = BirdBrains.GAME.loadImage("BirdBrainsSubtitle.png");
    }

    public void addButton(Button b) {
        buttons.add(b);
    }

    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    public void setBackground(PImage img) {
        background = img;
    }

    public void addText(TextElement t) {
        textElements.add(t);
    }

    public void draw() {
        
        if (background != null) {
            //BirdBrains.GAME.image(background, 0, 0, BirdBrains.GAME.width, BirdBrains.GAME.height);
            background.resize(BirdBrains.GAME.width, BirdBrains.GAME.height);
            BirdBrains.GAME.background(background);
        }
        if(BirdBrains.GAME.currentLevel == BirdBrains.MENU)
        {
            final float scale = 0.3f;
            BirdBrains.GAME.image(title, BirdBrains.GAME.width * .5f - (title.width*scale)/2, BirdBrains.GAME.height * 0.0f, (int)(title.width*scale),(int)(title.height*scale));
            BirdBrains.GAME.image(subTitle, BirdBrains.GAME.width * .5f - (subTitle.width*scale)/2, BirdBrains.GAME.height * .25f, (int)(subTitle.width*scale),(int)(subTitle.height*scale));
            
        }
        for (TextElement te : textElements) {
            te.draw();
        }
        for (Button b : buttons) {
            b.draw();
        }
    }

    public void setBtnActive(int id, boolean bool) {
        getButton(id).active = bool;
    }
    
    public void setBtnDisabled(int id, boolean bool) {
        getButton(id).disabled = bool;
    }

    public Button getButton(int id) {
        for (Button b : buttons) {
            if (b.id == id) {
                return b;
            }
        }
        return null;
    }

    public void setPlayer(String name, boolean ai) {
    }

    public void mousePressed() {
        for (Button b : buttons) {
            if (!anyAnimating()) {
                if (b.over()) {
                    for (ButtonAction a : b.actions) {
                        a.action();
                    }
                }
            }
        }
    }

    public boolean anyAnimating() {
        for(Button b : buttons){
            if(b instanceof TweetButton)
                if(((TweetButton)b).getIsMoving())
                    return true;
        }
        return false;
    }
}
