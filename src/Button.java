
import java.util.LinkedList;

public class Button {

    int id;
    boolean active;
    float x, y;
    float w, h;
    String text;
    float textSize, paddingX, paddingY;
    LinkedList<ButtonAction> actions = new LinkedList<ButtonAction>();

    Button(float x, float y, float w, float h, String text, int id) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.text = text;
        this.id = id;
        textSize = h * .5f;
        setPadding(w * 0.10f, h * 0.10f);
        active = true;
    }

    Button(float x, float y, float w, float h, String text, int id, boolean active) {
        this(x, y, w, h, text, id);
        this.active = active;
    }

    public void setPadding(float x, float y) {
        paddingX = x;
        paddingY = y;
    }

    public void draw() {
        if (active) {
            BirdBrains.GAME.fill(over() ? 0 : 255);
            BirdBrains.GAME.rect(x, y, w, h);
            BirdBrains.GAME.textSize(textSize);
            BirdBrains.GAME.fill(over() ? 255 : 0);
            BirdBrains.GAME.text(text, x, y, w, h);
        }
    }

    public void addAction(ButtonAction a) {
        actions.add(a);
    }

    public boolean over() {
        if (active) {
            if (BirdBrains.GAME.mouseX >= x && BirdBrains.GAME.mouseX <= (x + w) && BirdBrains.GAME.mouseY >= y && BirdBrains.GAME.mouseY <= (y + h)) {
                return true;
            }
        }
        return false;
    }
}
