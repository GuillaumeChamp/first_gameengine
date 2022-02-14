package Universal;
import Fight.Hero;
import OutDoor.Level;
import OutDoor.PlayerMovement;
import javafx.scene.image.Image;

public class Player {
    public PlayerMovement skin;
    public Level location;
    private Hero[] heroes;
    private TriggerList progression;

    public Player(){
        location = new Level(false);
        skin = new PlayerMovement(".//Resources//skin//player.png", 1,location);
    }
}
