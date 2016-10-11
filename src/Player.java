
public class Player {

    String name;
    float health;
    boolean ai;
    float x, y;
    HealthBar healthBar;
    Sprite sprite;
    int size = 200;
    boolean idle = true;
    boolean dead = false;
    float animCount = 0;
    int anim = 0;

    public Player(String name, float x, float y, boolean ai) {
        health = 1;
        this.name = name;
        this.ai = ai;
        this.x = x;
        this.y = y;
        healthBar = new HealthBar(x, y, 200, 30);
    }

    public void takeDmg(int dmg) {
        health -= (float) dmg / 100;
    }

    public void draw() {
        if (health <= 0) {
            idle = false;
            dead = true;
        }

        if (idle) {
            anim = 0;
        } else if (dead) {
            anim = 1;
        }

        BirdBrains.GAME.image(sprite.getAnim(anim, (int) animCount), x, y, size, size);
        healthBar.draw(health);

        if (!(anim == 1 && (int) animCount == sprite.animations.get(1).length - 1)) {
            if ((int) animCount < sprite.animations.get(anim).length - 1) {
                animCount += 0.1f;
            } else {
                animCount = 0;
            }
        }
    }

    public void addSprite(Sprite s) {
        sprite = s;
        healthBar.y += size + 10;
    }
}
