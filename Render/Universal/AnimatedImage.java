package Universal;

import javafx.scene.image.*;

public class AnimatedImage
{
    protected Image[] frames;
    protected double duration;

    public AnimatedImage(String path){
        frames = ImageCropper.crop(path,0,40,40);
        duration = 1;
    }

    public Image getFrame(double time)
    {
        int index = (int)((time % (frames.length * duration)) / duration);
        return frames[index];
    }

    /**
     * Useful for animation and cinematic
     * @param duration frame duration on second (I guess)
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     * Useful to change the texture might be use on trigger or cinematic
     * @param frames new texture of the character
     */
    public void setFrames(Image[] frames) {
        this.frames = frames;
    }
}
