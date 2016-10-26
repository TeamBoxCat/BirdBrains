
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import ddf.minim.*;
import java.util.*;

import javazoom.jl.converter.*;
import javazoom.jl.decoder.*;
import javazoom.jl.player.*;
import javazoom.jl.player.advanced.*;
import ddf.minim.javasound.*;
import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;
import javazoom.spi.*;
import javazoom.spi.mpeg.sampled.convert.*;
import javazoom.spi.mpeg.sampled.file.*;
import javazoom.spi.mpeg.sampled.file.tag.*;
import org.tritonus.sampled.file.*;
import org.tritonus.share.*;
import org.tritonus.share.midi.*;
import org.tritonus.share.sampled.*;
import org.tritonus.share.sampled.convert.*;
import org.tritonus.share.sampled.file.*;
import org.tritonus.share.sampled.mixer.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.*;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class BirdBrains extends PApplet {

    public static BirdBrains GAME;
    public static TweetFetcher TWITS;
    LinkedList<Level> levels = new LinkedList<Level>();
    int currentLevel = 0;
    Character trump, hillary;
    PImage tempBack;
    public int currentTurn = -1;
    
    //Constants
    public static final int HILLARY = 0;
    public static final int TRUMP = 1;
    public static final int MENU = 0;
    public static final int GAMESCREEN = 1;
    public static final int CREDITS = 2;
    public static final int LOADINGSCREEN = 3;
    public static double DELTA_TIME = 0.0;
    
    private long lastTime = System.nanoTime();

    Minim minim;
    LinkedList<String> sounds;
    SoundController sc;
    private Preloader preloader;
    public boolean isGameOver = false;

    public void setup() {
        currentLevel = LOADINGSCREEN;
        GAME = this;
        
        
        background(0);
        
        minim = new Minim(this);

        sounds = new LinkedList<String>();
        sounds.add("ssb.mp3");
        sounds.add("money.mp3");
        sounds.add("runtheworld.mp3");
        sc = new SoundController(sounds.get(0));

        tempBack = genBack();
        initMainMenu();
        preloader = new Preloader();
    }

    public void draw() {
        
        long currentTime = System.nanoTime();
        DELTA_TIME = ((double)currentTime - lastTime) /1000000000;
        lastTime = currentTime;
        
        textAlign(CENTER, CENTER);
        background(125);
        if (currentLevel != LOADINGSCREEN) {
            levels.get(currentLevel).draw();
            if (currentLevel == 1) {
                trump.draw();
                hillary.draw();
                handleTurn();
            }

            if (!sc.sound.equals(sounds.get(0)) && (currentLevel == 0 || currentLevel == 2)) {
                sc.playNext(sounds.get(0));
            }

            if (currentLevel == 1) {
                if (trump.health <= 0) {
                    gameOver("Hillary");
                } else if (hillary.health <= 0) {
                    gameOver("Trump");
                }
            }
        }
        else{
            preloader.draw();
        }
        
        sc.update();
    }

    public void gameOver(String winner) {
        isGameOver = true;
        for (Button b : levels.get(1).buttons) {
            if (b.id != 0) {
                b.active = false;
            }
        }
        levels.get(1).addText(new TextElement(width / 2, height * .3f, 50, winner + " wins!!!"));
    }

    public void mousePressed() {
        if(currentLevel == LOADINGSCREEN)
            preloader.mousePressed();
        else
            levels.get(currentLevel).mousePressed();
    }

    public void initMainMenu() { 
        if (levels.size() < 1) {
            levels.add(new Level(MENU, "Main Menu"));
            levels.add(new Level(GAMESCREEN, "Game Screen"));
            levels.add(new Level(CREDITS, "Credits Screen"));
        } else {
            levels.add(MENU, new Level(MENU, "Main Menu"));
        }
        levels.get(MENU).addText(new TextElement(width * .5f, height * .15f, 50, "Bird Brains?"));
        levels.get(MENU).addText(new TextElement(width * .5f, height * .25f, 30, "Something Something Politics"));

        initChars();

        Button exitButton = new Button(0, 0, 100, 50, "Exit", 0);
        exitButton.addAction(new ButtonAction() {
            @Override
            public void action() {
                exit();
            }
        }
        );
        levels.get(MENU).addButton(exitButton);

        ButtonAction trumpActive = new ButtonAction() {
            @Override
            public void action() {
                BirdBrains.GAME.trump.ai = false;
                setLevel(GAMESCREEN);
                randTurn();
            }

            
        };
        ButtonAction hillaryActive = new ButtonAction() {
            @Override
            public void action() {
                BirdBrains.GAME.hillary.ai = false;
                setLevel(GAMESCREEN);
                randTurn();
            }
        };
        Button trumpBtn = new Button(width * .35f - 100, height * .4f, 200, 50, "Trump", 80, false);
        trumpBtn.addAction(trumpActive);
        levels.get(MENU).addButton(trumpBtn);
        Button hillaryBtn = new Button(width * .65f - 100, height * .4f, 200, 50, "Hillary", 81, false);
        hillaryBtn.addAction(hillaryActive);
        levels.get(MENU).addButton(hillaryBtn);

        Button oneP = new Button(width * .5f - 100, height * .4f, 200, 50, "1 Player", 1);
        oneP.addAction(new ButtonAction() {
            @Override
            public void action() {
                playerSelect(1);
            }
        }
        );
        levels.get(MENU).addButton(oneP);

        Button twoP = new Button(width * .5f - 100, height * .5f, 200, 50, "2 Players", 2);
        twoP.addAction(new ButtonAction() {
            @Override
            public void action() {
                BirdBrains.GAME.trump.ai = false;
                BirdBrains.GAME.hillary.ai = false;
                setLevel(GAMESCREEN);
                randTurn();
            }
        }
        );
        levels.get(MENU).addButton(twoP);

        Button credits = new Button(width * .5f - 50, height * .6f, 100, 50, "Credits", 3);
        credits.addAction(new ButtonAction() {
            @Override
            public void action() {
                setLevel(CREDITS);
            }
        }
        );
        levels.get(MENU).addButton(credits);

        levels.get(MENU).setBackground(tempBack);
    }

    public void initGameScreen() {
        if (levels.size() < 2) {
            levels.add(new Level(GAMESCREEN, "Game Screen"));
        } else {
            levels.remove(GAMESCREEN);
            levels.add(GAMESCREEN, new Level(GAMESCREEN, "Game Screen"));
        }

        levels.get(GAMESCREEN).setBackground(tempBack);

        Button mainButton = new Button(0, 0, 100, 50, "Main", 0);
        mainButton.addAction(new ButtonAction() {
            @Override
            public void action() {
                setLevel(MENU);
            }
        }
        );
        //levels.get(GAMESCREEN).addButton(mainButton);
        levels.get(GAMESCREEN).addButton(new TweetButton(12, 12, 500, 185, 1, TRUMP));
        levels.get(GAMESCREEN).addButton(new TweetButton(12, 258, 500, 185, 2, TRUMP));
        levels.get(GAMESCREEN).addButton(new TweetButton(12, 504, 500, 185, 3, TRUMP));
        levels.get(GAMESCREEN).addButton(new TweetButton(1072, 12, 500, 185, 4, HILLARY));
        levels.get(GAMESCREEN).addButton(new TweetButton(1072, 258, 500, 185, 5, HILLARY));
        levels.get(GAMESCREEN).addButton(new TweetButton(1072, 504, 500, 185, 6, HILLARY));

        levels.get(GAMESCREEN).getButton(1).addAction(new ButtonAction() {
            @Override
            public void action() {
                trump.dealDmg(((TweetButton)(levels.get(GAMESCREEN).getButton(1))).getDamage(), hillary);
                currentTurn = HILLARY;
            }
        }
        );
        levels.get(GAMESCREEN).getButton(2).addAction(new ButtonAction() {
            @Override
            public void action() {
                trump.dealDmg(((TweetButton)(levels.get(GAMESCREEN).getButton(2))).getDamage(), hillary);
                currentTurn = HILLARY;
            }
        }
        );
        levels.get(GAMESCREEN).getButton(3).addAction(new ButtonAction() {
            @Override
            public void action() {
                trump.dealDmg(((TweetButton)(levels.get(GAMESCREEN).getButton(3))).getDamage(), hillary);
                currentTurn = HILLARY;
            }
        }
        );
        levels.get(GAMESCREEN).getButton(4).addAction(new ButtonAction() {
            @Override
            public void action() {
                hillary.dealDmg(((TweetButton)(levels.get(GAMESCREEN).getButton(4))).getDamage(), trump);
                currentTurn = TRUMP;
            }
        }
        );
        levels.get(GAMESCREEN).getButton(5).addAction(new ButtonAction() {
            @Override
            public void action() {
                hillary.dealDmg(((TweetButton)(levels.get(GAMESCREEN).getButton(5))).getDamage(), trump);
                currentTurn = TRUMP;
            }
        }
        );
        levels.get(GAMESCREEN).getButton(6).addAction(new ButtonAction() {
            @Override
            public void action() {
                hillary.dealDmg(((TweetButton)(levels.get(GAMESCREEN).getButton(6))).getDamage(), trump);
                currentTurn = TRUMP;
            }
        }
        );
        
        for(int i = 1;i<= 6; i++){
            TweetButton b = (TweetButton)levels.get(GAMESCREEN).getButton(i);
            b.addAction(new ButtonAction() {
                @Override
                public void action() {
                    b.setIsMoving();
                }
            }
            );
        }
    }

    public void initCredits() {
        if (levels.size() < 3) {
            levels.add(new Level(CREDITS, "Credits Screen"));
        } else {
            levels.remove(CREDITS);
            levels.add(CREDITS, new Level(CREDITS, "Credits Screen"));
        }

        Button mainButton = new Button(0, 0, 100, 50, "Main", 0);
        mainButton.addAction(new ButtonAction() {
            @Override
            public void action() {
                setLevel(MENU);
            }
        }
        );

        levels.get(CREDITS).setBackground(tempBack);
        levels.get(CREDITS).addText(new TextElement(width * .5f, height * .15f, 50, "Credits"));
        levels.get(CREDITS).addText(new TextElement(width * .2f, height * .25f, 30, "Almost Everything - Mohamad Kalache", LEFT));
        levels.get(CREDITS).addText(new TextElement(width * .2f, height * .30f, 30, "Sprites - Universal LPC Sprite Sheet Character Generator", LEFT));
        levels.get(CREDITS).addText(new TextElement(width * .2f, height * .35f, 20, "-https://goo.gl/qTVZYn", LEFT));
        levels.get(CREDITS).addText(new TextElement(width * .2f, height * .40f, 30, "Sound Library - Minim", LEFT));
        levels.get(CREDITS).addText(new TextElement(width * .2f, height * .45f, 20, "-http://code.compartmental.net/tools/minim", LEFT));
        levels.get(CREDITS).addText(new TextElement(width * .2f, height * .50f, 30, "Theme - Star Spangled Banner", LEFT));
        levels.get(CREDITS).addText(new TextElement(width * .2f, height * .55f, 20, "-US Army Band", LEFT));
        levels.get(CREDITS).addText(new TextElement(width * .2f, height * .60f, 30, "Trump Theme - The O'Jays, For the Love of Money", LEFT));
        levels.get(CREDITS).addText(new TextElement(width * .2f, height * .65f, 20, "-Sony Music", LEFT));
        levels.get(CREDITS).addText(new TextElement(width * .2f, height * .70f, 30, "Hillary Theme - Beyonc\u00e9, Run the World(Girls)", LEFT));
        levels.get(CREDITS).addText(new TextElement(width * .2f, height * .75f, 20, "-Sony Music", LEFT));
        levels.get(CREDITS).addButton(mainButton);
    }

    public PImage genBack() {
        int c1 = 0xffed422f;
        int c2 = 0xff0a2463;
        PImage img = createImage(width, height, RGB);
        img.loadPixels();
        for (int i = 0; i < img.height; i++) {
            for (int j = 0; j < img.width; j++) {
                float offset = ((float) j) / width;
                int c = lerpColor(c1, c2, offset);
                img.pixels[j + i * width] = c;
            }
        }

        img.updatePixels();

        return img;
    }

    public void playerSelect(int id) {
        if (id == 1) {
            levels.get(currentLevel).setBtnActive(2, true);
            levels.get(currentLevel).setBtnActive(80, true);
            levels.get(currentLevel).setBtnActive(81, true);
            levels.get(currentLevel).setBtnActive(1, false);
        }
    }

    public void initChars() {
        trump = new Character(TRUMP, width * 0.1f, height * 0.4f, true);
        hillary = new Character(HILLARY, width * 0.9f - 200, height * 0.4f, true);
        trump.addSprite(new Sprite("./data/trump.png", 64, 64));
        hillary.addSprite(new Sprite("./data/hillary.png", 64, 64));

        PVector[] trumpIdle = {new PVector(0, 3), new PVector(1, 3), new PVector(2, 3), new PVector(3, 3), new PVector(4, 3), new PVector(5, 3), new PVector(6, 3)};
        PVector[] trumpLose = {new PVector(0, 20), new PVector(1, 20), new PVector(2, 20), new PVector(3, 20), new PVector(4, 20), new PVector(5, 20)};
        trump.sprite.animations.add(trumpIdle);
        trump.sprite.animations.add(trumpLose);

        PVector[] hillaryIdle = {new PVector(0, 1), new PVector(1, 1), new PVector(2, 1), new PVector(3, 1), new PVector(4, 1), new PVector(5, 1), new PVector(6, 1)};
        PVector[] hillaryLose = {new PVector(0, 20), new PVector(1, 20), new PVector(2, 20), new PVector(3, 20), new PVector(4, 20), new PVector(5, 20)};
        hillary.sprite.animations.add(hillaryIdle);
        hillary.sprite.animations.add(hillaryLose);
    }

    public void setLevel(int l) {
        currentLevel = l;
        if (l == MENU) {
            initMainMenu();
        } else if (l == GAMESCREEN) {
            initGameScreen();
        } else {
            initCredits();
        }
    }

    public void settings() {
        size(1600, 900);
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"BirdBrains"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }

    private void handleTurn() {
        if(currentTurn == HILLARY){
            levels.get(currentLevel).setBtnDisabled(1, true);
            levels.get(currentLevel).setBtnDisabled(2, true);
            levels.get(currentLevel).setBtnDisabled(3, true);
            levels.get(currentLevel).setBtnDisabled(4, false);
            levels.get(currentLevel).setBtnDisabled(5, false);
            levels.get(currentLevel).setBtnDisabled(6, false);
        }
        else if(currentTurn == TRUMP){
            levels.get(currentLevel).setBtnDisabled(1, false);
            levels.get(currentLevel).setBtnDisabled(2, false);
            levels.get(currentLevel).setBtnDisabled(3, false);
            levels.get(currentLevel).setBtnDisabled(4, true);
            levels.get(currentLevel).setBtnDisabled(5, true);
            levels.get(currentLevel).setBtnDisabled(6, true);
        }
        else{
            levels.get(currentLevel).setBtnDisabled(1, true);
            levels.get(currentLevel).setBtnDisabled(2, true);
            levels.get(currentLevel).setBtnDisabled(3, true);
            levels.get(currentLevel).setBtnDisabled(4, true);
            levels.get(currentLevel).setBtnDisabled(5, true);
            levels.get(currentLevel).setBtnDisabled(6, true);
        }
    }
    
    private void randTurn() {
        Random rand = new Random();
        currentTurn = rand.nextInt(2);
    }
}
