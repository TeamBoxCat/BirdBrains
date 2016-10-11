
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
    LinkedList<Level> levels = new LinkedList<Level>();
    int currentLevel = 0;
    Player trump, hillary, p1, p2;
    PImage tempBack;
    boolean p1first = false;

    Minim minim;
    LinkedList<String> sounds;
    SoundController sc;

    public void setup() {
        GAME = this;
        //try{System.setOut(new PrintStream(new FileOutputStream("./log.txt")));}
        //catch(FileNotFoundException e){}
        background(0);

        minim = new Minim(this);

        sounds = new LinkedList<String>();
        sounds.add("ssb.mp3");
        sounds.add("money.mp3");
        sounds.add("runtheworld.mp3");
        sc = new SoundController(sounds.get(0));

        tempBack = genBack();
        initMainMenu();
    }

    public void draw() {

        textAlign(CENTER, CENTER);
        background(0);
        levels.get(currentLevel).draw();
        if (currentLevel == 1) {
            trump.draw();
            hillary.draw();
        }

        if (!sc.sound.equals(sounds.get(0)) && (currentLevel == 0 || currentLevel == 2)) {
            sc.playNext(sounds.get(0));
        }
        if (currentLevel == 1 && hillary.health > trump.health && !sc.sound.equals(sounds.get(2))) {
            sc.playNext(sounds.get(2));
        } else if (currentLevel == 1 && hillary.health < trump.health && !sc.sound.equals(sounds.get(1))) {
            sc.playNext(sounds.get(1));
        }

        if (currentLevel == 1) {
            if (trump.health <= 0) {
                gameOver("Hillary");
            } else if (hillary.health <= 0) {
                gameOver("Trump");
            }
        }
        sc.update();
    }

    public void gameOver(String winner) {
        for (Button b : levels.get(1).buttons) {
            if (b.id != 0) {
                b.active = false;
            }
        }
        levels.get(1).addText(new TextElement(width / 2, height * .3f, 50, winner + " wins!!!"));
    }

    public void mousePressed() {
        levels.get(currentLevel).mousePressed();
    }

    public void initMainMenu() { //<>//
        if (levels.size() < 1) {
            levels.add(new Level(0, "Main Menu"));
            levels.add(new Level(1, "Game Screen"));
            levels.add(new Level(2, "Credits Screen"));
        } else {
            levels.add(0, new Level(0, "Main Menu"));
        }
        levels.get(0).addText(new TextElement(width * .5f, height * .15f, 50, "Bird Brains?"));
        levels.get(0).addText(new TextElement(width * .5f, height * .25f, 30, "Something Something Politics"));

        initChars();

        Button exitButton = new Button(0, 0, 100, 50, "Exit", 0);
        exitButton.addAction(new ButtonAction() {
            @Override
            public void action() {
                exit();
            }
        }
        );
        levels.get(0).addButton(exitButton);

        ButtonAction trumpActive = new ButtonAction() {
            @Override
            public void action() {
                trump.ai = false;
                p1 = trump;
                setLevel(1);
            }
        };
        ButtonAction hillaryActive = new ButtonAction() {
            @Override
            public void action() {
                hillary.ai = false;
                p1 = hillary;
                setLevel(1);
            }
        };
        Button trumpBtn = new Button(width * .35f - 100, height * .4f, 200, 50, "Trump", 80, false);
        trumpBtn.addAction(trumpActive);
        levels.get(0).addButton(trumpBtn);
        Button hillaryBtn = new Button(width * .65f - 100, height * .4f, 200, 50, "Hillary", 81, false);
        hillaryBtn.addAction(hillaryActive);
        levels.get(0).addButton(hillaryBtn);
        Button trump2Btn = new Button(width * .35f - 100, height * .5f, 200, 50, "Trump", 90, false);
        trump2Btn.addAction(trumpActive);
        levels.get(0).addButton(trump2Btn);
        Button hillary2Btn = new Button(width * .65f - 100, height * .5f, 200, 50, "Hillary", 91, false);
        hillary2Btn.addAction(hillaryActive);
        levels.get(0).addButton(hillary2Btn);

        Button oneP = new Button(width * .5f - 100, height * .4f, 200, 50, "1 Player", 1);
        oneP.addAction(new ButtonAction() {
            @Override
            public void action() {
                playerSelect(1);
            }
        }
        );
        levels.get(0).addButton(oneP);

        Button twoP = new Button(width * .5f - 100, height * .5f, 200, 50, "2 Players", 2);
        twoP.addAction(new ButtonAction() {
            @Override
            public void action() {
                playerSelect(2);
            }
        }
        );
        levels.get(0).addButton(twoP);

        Button credits = new Button(width * .5f - 50, height * .6f, 100, 50, "Credits", 3);
        credits.addAction(new ButtonAction() {
            @Override
            public void action() {
                setLevel(2);
            }
        }
        );
        levels.get(0).addButton(credits);

        levels.get(0).setBackground(tempBack);
    }

    public void initGameScreen() {
        if (levels.size() < 2) {
            levels.add(new Level(1, "Game Screen"));
        } else {
            levels.add(1, new Level(1, "Game Screen"));
        }

        levels.get(1).setBackground(tempBack);

        Button mainButton = new Button(0, 0, 100, 50, "Main", 0);
        mainButton.addAction(new ButtonAction() {
            @Override
            public void action() {
                setLevel(0);
            }
        }
        );
        levels.get(1).addButton(mainButton);
        levels.get(1).addButton(new Button(width * 0.1f, height * 0.1f, 200, 50, "Trump Option 1", 1));
        levels.get(1).addButton(new Button(width * 0.1f, height * 0.2f, 200, 50, "Trump Option 2", 2));
        levels.get(1).addButton(new Button(width * 0.1f, height * 0.3f, 200, 50, "Trump Option 3", 3));
        levels.get(1).addButton(new Button(width * 0.9f - 200, height * 0.1f, 200, 50, "Hillary Option 1", 4));
        levels.get(1).addButton(new Button(width * 0.9f - 200, height * 0.2f, 200, 50, "Hillary Option 2", 5));
        levels.get(1).addButton(new Button(width * 0.9f - 200, height * 0.3f, 200, 50, "Hillary Option 3", 6));

        levels.get(1).getButton(1).addAction(new ButtonAction() {
            @Override
            public void action() {
                hillary.takeDmg(10);
            }
        }
        );
        levels.get(1).getButton(2).addAction(new ButtonAction() {
            @Override
            public void action() {
                hillary.takeDmg(5);
            }
        }
        );
        levels.get(1).getButton(3).addAction(new ButtonAction() {
            @Override
            public void action() {
                hillary.takeDmg(1);
            }
        }
        );
        levels.get(1).getButton(4).addAction(new ButtonAction() {
            @Override
            public void action() {
                trump.takeDmg(5);
            }
        }
        );
        levels.get(1).getButton(5).addAction(new ButtonAction() {
            @Override
            public void action() {
                trump.takeDmg(1);
            }
        }
        );
        levels.get(1).getButton(6).addAction(new ButtonAction() {
            @Override
            public void action() {
                trump.takeDmg(10);
            }
        }
        );
    }

    public void initCredits() {
        if (levels.size() < 3) {
            levels.add(new Level(2, "Credits Screen"));
        } else {
            levels.add(2, new Level(2, "Credits Screen"));
        }

        Button mainButton = new Button(0, 0, 100, 50, "Main", 0);
        mainButton.addAction(new ButtonAction() {
            @Override
            public void action() {
                setLevel(0);
            }
        }
        );

        levels.get(2).setBackground(tempBack);
        levels.get(2).addText(new TextElement(width * .5f, height * .15f, 50, "Credits"));
        levels.get(2).addText(new TextElement(width * .2f, height * .25f, 30, "Almost Everything - Mohamad Kalache", LEFT));
        levels.get(2).addText(new TextElement(width * .2f, height * .30f, 30, "Sprites - Universal LPC Sprite Sheet Character Generator", LEFT));
        levels.get(2).addText(new TextElement(width * .2f, height * .35f, 20, "-https://goo.gl/qTVZYn", LEFT));
        levels.get(2).addText(new TextElement(width * .2f, height * .40f, 30, "Sound Library - Minim", LEFT));
        levels.get(2).addText(new TextElement(width * .2f, height * .45f, 20, "-http://code.compartmental.net/tools/minim", LEFT));
        levels.get(2).addText(new TextElement(width * .2f, height * .50f, 30, "Theme - Star Spangled Banner", LEFT));
        levels.get(2).addText(new TextElement(width * .2f, height * .55f, 20, "-US Army Band", LEFT));
        levels.get(2).addText(new TextElement(width * .2f, height * .60f, 30, "Trump Theme - The O'Jays, For the Love of Money", LEFT));
        levels.get(2).addText(new TextElement(width * .2f, height * .65f, 20, "-Sony Music", LEFT));
        levels.get(2).addText(new TextElement(width * .2f, height * .70f, 30, "Hillary Theme - Beyonc\u00e9, Run the World(Girls)", LEFT));
        levels.get(2).addText(new TextElement(width * .2f, height * .75f, 20, "-Sony Music", LEFT));
        levels.get(2).addButton(mainButton);
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
            levels.get(currentLevel).setBtnActive(90, false);
            levels.get(currentLevel).setBtnActive(91, false);
            levels.get(currentLevel).setBtnActive(2, true);
            levels.get(currentLevel).setBtnActive(80, true);
            levels.get(currentLevel).setBtnActive(81, true);
            levels.get(currentLevel).setBtnActive(1, false);
        }
        if (id == 2) {
            levels.get(currentLevel).setBtnActive(80, false);
            levels.get(currentLevel).setBtnActive(81, false);
            levels.get(currentLevel).setBtnActive(1, true);
            levels.get(currentLevel).setBtnActive(90, true);
            levels.get(currentLevel).setBtnActive(91, true);
            levels.get(currentLevel).setBtnActive(2, false);
        }
    }

    public void initChars() {
        trump = new Player("Trump", width * 0.1f, height * 0.4f, true);
        hillary = new Player("Hillary", width * 0.9f - 200, height * 0.4f, true);
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
        if (l == 0) {
            initMainMenu();
        } else if (l == 1) {
            initGameScreen();
        } else {
            initCredits();
        }
    }

    public void settings() {
        size(1280, 720);
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"BirdBrains"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
