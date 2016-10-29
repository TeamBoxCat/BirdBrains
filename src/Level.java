
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

    LinkedList<String> soundEffect = new LinkedList<String>();
    SoundController light;
    SoundController clap;

    private boolean played1 = false;
    private boolean played2 = false;
    private boolean played3 = false;

    private float counter = 0;
    private TextElement quote;
    private int quoteColour;
    private int lastCandi = -1;
    
    private PImage spotlight = BirdBrains.GAME.loadImage("stagelights_spotlights_flashlights_allofthelights.png");
    private boolean isIntro;
    private float timer = 0;
    
    public Level(int id, String name) {
        this();
        this.id = id;
        this.name = name;
        soundEffect.add("tweet.mp3");
        soundEffect.add("lighting.mp3");
        soundEffect.add("applause.mp3");
        BirdBrains.SEC = new SoundController(soundEffect.get(0));
        light = new SoundController(soundEffect.get(1));
        clap = new SoundController(soundEffect.get(2));
    }

    public Level() {
        quote = new TextElement(BirdBrains.GAME.width*.5f, BirdBrains.GAME.height*.51f, BirdBrains.GAME.width*.01f, "");
        title = BirdBrains.GAME.loadImage("BirdBrainsTitle.png");
        subTitle = BirdBrains.GAME.loadImage("BirdBrainsSubtitle.png");
    }
    
    private String newQuote(){
        return BirdBrains.FLAVOUR.getQuote(BirdBrains.GAME.currentTurn);
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
            background.resize(BirdBrains.GAME.width, BirdBrains.GAME.height);
            BirdBrains.GAME.image(background, 0, 0, BirdBrains.GAME.width,BirdBrains.GAME.height);
            //BirdBrains.GAME.background(background);
        }
        if(BirdBrains.GAME.currentLevel == BirdBrains.MENU)
        {
            final float scale = 0.3f;
            BirdBrains.GAME.image(title, BirdBrains.GAME.width * .5f - (title.width*scale)/2, BirdBrains.GAME.height * 0.0f, (int)(title.width*scale),(int)(title.height*scale));
            BirdBrains.GAME.image(subTitle, BirdBrains.GAME.width * .5f - (subTitle.width*scale)/2, BirdBrains.GAME.height * .25f, (int)(subTitle.width*scale),(int)(subTitle.height*scale));
            
        }
        else if(BirdBrains.GAME.currentLevel == BirdBrains.GAMESCREEN && !isIntro){
            if(counter >=10){
                quote.text = "";
                counter = 0;
            }
            else if(!quote.text.equals(""))
                counter += BirdBrains.DELTA_TIME;
            if(Math.random() >= 0.9 && quote.text.equals("") && BirdBrains.GAME.currentTurn != lastCandi) {
                quote.text = newQuote();
                quoteColour = BirdBrains.GAME.currentTurn;
                lastCandi = BirdBrains.GAME.currentTurn;
            }
            
            quote.drawQuote(quoteColour);
        }
        
        for (TextElement te : textElements) {
            te.draw();
        }
        for (Button b : buttons) {
            b.draw();
        }
        
        if(isIntro) {
            BirdBrains.GAME.tint(120);
            timer += BirdBrains.DELTA_TIME;
            for(int i = 1; i <= 6; i++)
                setBtnActive(i, false);
            if(timer >= 3) {
                if (played1 == false) {
                    light.playSoundEffect();
                    played1 = true;
                }
                BirdBrains.GAME.noTint();
                BirdBrains.GAME.image(spotlight, BirdBrains.GAME.width * 0.26f + 12, BirdBrains.GAME.height * 0.22f, 200, BirdBrains.GAME.height * 0.59f);
                BirdBrains.GAME.tint(120);
            }
            if(timer >= 5) {
                if (played2 == false) {
                    light.playSoundEffect();
                    played2 = true;
                }
                BirdBrains.GAME.noTint();
                BirdBrains.GAME.image(spotlight, BirdBrains.GAME.width * 0.74f - 224, BirdBrains.GAME.height * 0.22f, 200, BirdBrains.GAME.height * 0.59f);
                BirdBrains.GAME.tint(120);
            }
            if(timer >= 7) {
                if (played3 == false) {
                    clap.playSoundEffect();
                    played3 = true;
                }
                BirdBrains.GAME.noTint();
                for(int i = 1; i <= 6; i++) {
                    setBtnActive(i, true);
                }
                timer = 0;
                setIsIntro(false);       
            }
        }
    }

    public void setBtnActive(int id, boolean bool) {
        getButton(id).active = bool;
    }
    
    public void setBtnDisabled(int id, boolean bool) {
        getButton(id).disabled = bool;
    }
    
    public void setIsIntro(boolean bool) {
        isIntro = bool;
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
            if (!anyAnimating() || b.id == 0) {
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
                if(((TweetButton)b).getIsMoving() || ((TweetButton)b).getIsSpawning())
                    return true;
        }
        return false;
    }
}
