package Universal;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class BackgroundMusic implements Runnable{
    static MediaPlayer music;

    public BackgroundMusic(String name){
        File file;
        try{
            file = new File("src/Resources/Audio/Music/" + name + ".mp3");
        }
        catch (Exception e){
            file=new File("src/Resources/Audio/Music/default.mp3");
            System.out.println(name);
            e.printStackTrace();
        }
        Media media = new Media(file.toURI().toString());
        music = new MediaPlayer(media);
        this.run();
    }

    /**
     * Can be called everywhere. Used to change the music
     * @param name new song name
     */
    public static void change(String name){
        try{
            music.stop();
            File file = new File("src/Resources/Audio/Music/" + name + ".mp3");
            Media media = new Media(file.toURI().toString());
            music = new MediaPlayer(media);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("unable to change music : " + name);
        }
    }
    @Override
    public void run() {
        music.setCycleCount(Integer.MAX_VALUE);
        music.setVolume(music.getVolume()/6);
        music.setOnReady(()-> {
            music.play();
            music.setOnEndOfMedia(music::play);
        });
    }
}
