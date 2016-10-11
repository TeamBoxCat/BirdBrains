
public class HealthBar {

    float x, y;
    int w, h;

    HealthBar(float x, float y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(float health) {
        BirdBrains.GAME.fill(0);
        BirdBrains.GAME.rect(x, y, w, h);
        BirdBrains.GAME.fill(BirdBrains.GAME.lerpColor(0xffFF0000, 0xff00FF00, health));
        if (health >= 0) {
            BirdBrains.GAME.rect(x + 4, y + 4, (w - 8) * health, h - 8);
        }
    }
}
