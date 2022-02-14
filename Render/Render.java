import Universal.BackgroundMusic;
import Universal.Player;
import OutDoor.Scene_outside;
import Universal.Game_Scene;
import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.animation.AnimationTimer;

public class Render extends Application {
    static Canvas canvas = new Canvas(Game_Scene.width,Game_Scene.height);
    public void start(Stage theStage) {
        theStage.setTitle("Render");
        Player player = new Player();
        Group root = new Group();
        int borderXSize = 0;
        int borderYSize = 70;

        final double defaultWidth = Screen.getPrimary().getBounds().getWidth()-borderXSize;
        final double defaultHeight = Screen.getPrimary().getBounds().getHeight()-borderYSize;

        final long startNanoTime = System.nanoTime();

        root.getChildren().add(canvas);
        //BackgroundMusic sound = new BackgroundMusic("Mystic_Forest");

        Game_Scene ActiveScene = new Scene_outside(root,player,canvas,defaultWidth,defaultHeight);
        theStage.setScene(ActiveScene);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                ActiveScene.Tick(t);
            }
        }.start();
        theStage.show();
    }
}