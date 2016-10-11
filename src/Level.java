
import java.util.LinkedList;
import processing.core.PImage;

public class Level {

    int id;
    String name;
    private PImage background;
    private LinkedList<TextElement> textElements = new LinkedList<TextElement>();
    private LinkedList<Sprite> sprites = new LinkedList<Sprite>();
    public LinkedList<Button> buttons = new LinkedList<Button>();

    public Level(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Level() {
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
            BirdBrains.GAME.image(background, 0, 0, BirdBrains.GAME.width, BirdBrains.GAME.height);
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
            if (b.over()) {
                for (ButtonAction a : b.actions) {
                    a.action();
                }
                //println("over");
            }
        }
    }
}
