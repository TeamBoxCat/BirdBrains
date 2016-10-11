
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
        BirdBrains.GAME.fill(255);
        BirdBrains.GAME.textAlign(align);
        BirdBrains.GAME.textSize(size);
        BirdBrains.GAME.text(text, x, y);
        BirdBrains.GAME.textAlign(BirdBrains.GAME.CENTER, BirdBrains.GAME.CENTER);
    }
}
