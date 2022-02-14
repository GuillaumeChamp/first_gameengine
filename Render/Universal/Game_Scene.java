package Universal;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Screen;

public abstract class Game_Scene extends Scene {
    static public double width =  Screen.getPrimary().getBounds().getWidth();
    static public double height = Screen.getPrimary().getBounds().getHeight()-70;
    static protected GraphicsContext gc;
    static protected Canvas canvas;

    public Game_Scene(Parent parent, Canvas canvas) {
        super(parent);
        if (canvas != null){
            Game_Scene.canvas=canvas;
            gc = canvas.getGraphicsContext2D();
        }
        this.addResizeable();
        this.addController();
    }

    abstract public void Tick(double t);
    abstract public void addController();

    /**
     * Add the resizeable behaviour to the GameScene
     */
    private void addResizeable(){
        this.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            width = (double) newSceneWidth;
            clear();
        });
        this.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            height = (double) newSceneHeight;
            clear();
        });
    }
    private void clear(){
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

}
