
import ddf.minim.AudioPlayer;

class SoundController {

    String sound;
    boolean isPlaying = false;
    int gain = -15;
    AudioPlayer audioPlayer;

    public SoundController(String sound) {
        this.sound = sound;
    }

    public void play() {
        audioPlayer = BirdBrains.GAME.minim.loadFile(sound, 4096);
        audioPlayer.setGain(gain);
        audioPlayer.loop();
        isPlaying = true;
    }
    
    public void playSoundEffect() {
        audioPlayer = BirdBrains.GAME.minim.loadFile(sound, 4096);
        audioPlayer.setGain(gain);
        audioPlayer.play();
        isPlaying = true;
    }

    public void update() {
        if (!isPlaying) {
            play();
        }
        if(!audioPlayer.isPlaying()){
            audioPlayer.close();
        }
    }

    public void playNext(String next) {
        audioPlayer.pause();
        audioPlayer.close();
        sound = next;
        play();
    }
}
