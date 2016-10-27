
import java.util.LinkedList;

public class Button {

    int id;
    boolean active,disabled;
    float x, y;
    float w, h;
    String text;
    float textSize, paddingX, paddingY;
    LinkedList<ButtonAction> actions = new LinkedList<ButtonAction>();

    public Button(float x, float y, float w, float h, String text, int id) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.text = text;
        this.id = id;
        textSize = h * .55f;
        setPadding(w * 0.10f, h * 0.10f);
        active = true;
        disabled = false;
    }
    
    public Button(float x, float y, float w, float h, int id) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.id = id;
        textSize = h * .55f;
        setPadding(w * 0.10f, h * 0.10f);
        active = true;
        text="";
    }

    public Button(float x, float y, float w, float h, String text, int id, boolean active) {
        this(x, y, w, h, text, id);
        this.active = active;
    }
    
    public Button(){}

    public void setPadding(float x, float y) {
        paddingX = x;
        paddingY = y;
    }

    public void draw() {
        if (active) {
            BirdBrains.GAME.stroke(10,36,99);
            BirdBrains.GAME.strokeWeight(2);
            BirdBrains.GAME.textFont(BirdBrains.FONT);
            BirdBrains.GAME.fill(over() ? 180 : 255);
            BirdBrains.GAME.rect(x, y, w, h);
            BirdBrains.GAME.textSize(textSize);
            BirdBrains.GAME.fill(0);
            BirdBrains.GAME.text(text, x, y, w, h);
        }
    }

    public void addAction(ButtonAction a) {
        actions.add(a);
    }

    public boolean over() {
        if (active && !disabled) {
            if (BirdBrains.GAME.mouseX >= x && BirdBrains.GAME.mouseX <= (x + w) && BirdBrains.GAME.mouseY >= y && BirdBrains.GAME.mouseY <= (y + h)) {
                return true;
            }
        }
        return false;
    }
}
