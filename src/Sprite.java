
import java.util.LinkedList;
import processing.core.PImage;
import processing.core.PVector;

public class Sprite {

    PImage spriteSheet;
    float spriteWidth;
    float spriteHeight;
    float numSpritesX;
    LinkedList<PVector[]> animations = new LinkedList<PVector[]>();

    public Sprite(String imagePath, float spriteWidth, float spriteHeight) {
        this.spriteHeight = spriteHeight;
        this.spriteWidth = spriteWidth;
        spriteSheet = BirdBrains.GAME.loadImage(imagePath);
        numSpritesX = spriteSheet.width / spriteWidth;
    }

    public PImage getSprite(PVector pos) {
        int x = (int) pos.x * (int) spriteWidth;
        int y = (int) pos.y * (int) spriteHeight;
        return spriteSheet.get(x, y, (int) spriteWidth, (int) spriteHeight);
    }

    public PImage getAnim(int anim, int frame) {
        //println((int)animations.get(anim)[frame].x);
        //println((int)animations.get(anim)[frame].y);
        return getSprite(animations.get(anim)[frame]);
    }
}
