
public class Character {

    String name;
    float health;
    boolean ai;
    float x, y;
    Sprite sprite;
    int size = 200;
    boolean idle = true;
    boolean dead = false;
    float animCount = 0;
    int anim = 0;

    public Character(String name, float x, float y, boolean ai) {
        health = 1;
        this.name = name;
        this.ai = ai;
        this.x = x;
        this.y = y;
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
        drawHealthBar();

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
    }
    
    private void drawHealthBar(){
        float w = size*1.3f;
        float h = size*.15f;
        float x = this.x;
        float y = this.y + size*1.2f;
        
        BirdBrains.GAME.fill(0);
        BirdBrains.GAME.rect(x, y, w, h);
        BirdBrains.GAME.fill(BirdBrains.GAME.lerpColor(0xffFF0000, 0xff00FF00, health));
        if (health >= 0) {
            BirdBrains.GAME.rect(x + 4, y + 4, (w - 8) * health, h - 8);
        }
    }

    void dealDmg(int dmg, Character enemy) {
        enemy.takeDmg(dmg);
        
    }
}
