
public class TextElement {

    float size;
    String text;
    float x, y;
    int align;

    public TextElement(float x, float y, float size, String text) {
        this.size = size;
        this.x = x;
        this.y = y;
        this.text = text;
        align = BirdBrains.GAME.CENTER;
    }

    public TextElement(float x, float y, float size, String text, int align) {
        this(x, y, size, text);
        this.align = align;
    }

    public void draw() {
        BirdBrains.GAME.textFont(BirdBrains.GAME.FONT);
        BirdBrains.GAME.fill(255);
        BirdBrains.GAME.textAlign(align);
        BirdBrains.GAME.textSize(size);
        BirdBrains.GAME.text(text, x, y);
        BirdBrains.GAME.textAlign(BirdBrains.GAME.CENTER, BirdBrains.GAME.CENTER);
    }
    
    public void drawQuote(int turn) {
        BirdBrains.GAME.textFont(BirdBrains.GAME.FONT);
        if (turn == 0) {
            BirdBrains.GAME.fill(3, 83, 164);
        }
        else {
            BirdBrains.GAME.fill(237, 66, 47);
        }
        BirdBrains.GAME.textAlign(align);
        BirdBrains.GAME.textSize(size);
        float w = BirdBrains.GAME.width*.18f;
        float h = BirdBrains.GAME.height*.1f;
        BirdBrains.GAME.text(text, x-w/2, y, w, h);
        BirdBrains.GAME.textAlign(BirdBrains.GAME.CENTER, BirdBrains.GAME.CENTER);
    }
}
