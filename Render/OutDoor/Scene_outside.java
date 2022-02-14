package OutDoor;
import Universal.Game_Scene;
import Universal.Player;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;

public class Scene_outside extends Game_Scene {
    private final Player player;
    private final ArrayList<String> input = new ArrayList<>(); //store the keyboard input

    public Scene_outside(Group root, Player player, Canvas canvas, double width, double height){
        super(root, canvas);
        this.player=player;
        Game_Scene.height = height;
        Game_Scene.width = width;
    }
    private void walk(){
        player.skin.setVelocity(0,0);
        boolean moved = false;
        if (input.contains("LEFT")) {
            player.skin.addVelocity(-10, 0);
            moved = true;
        }
        if (input.contains("RIGHT")) {
            player.skin.addVelocity(10, 0);
            moved = true;
        }
        if (input.contains("UP")) {
            player.skin.addVelocity(0, -10);
            moved = true;
        }
        if (input.contains("DOWN")) {
            player.skin.addVelocity(0, 10);
            moved = true;
        }
        player.skin.update();
        if (moved) triggerCombat();
    }

    private void triggerCombat(){
        if (!player.location.isPeaceful()){
            double rng = Math.random();
            if (rng>0.95) Combat();
        }
    }

    private void Combat(){
        //TODO : Switch to Fight
        System.out.println("new Fight");
    }

    @Override
    public void Tick(double t){
        walk();
        double printLimitX = 800;
        double printLimitY = 450;
        double offSetLandX = player.skin.getPositionX() - (printLimitX/2);
        double offSetLandY = player.skin.getPositionY() - (printLimitY/2);

        double xRatio = width/ printLimitX;

        double yRatio = height/ printLimitY;
        if (offSetLandX < 0) offSetLandX = 0;
        if (offSetLandX >= player.location.getSizeX()-printLimitX) offSetLandX = player.location.getSizeX()-printLimitX;
        if (offSetLandY < 0) offSetLandY = 0;
        if (offSetLandY >= player.location.getSizeY()-printLimitY) offSetLandY = player.location.getSizeY()-printLimitY;
        //Fixme : define a Level print size
        gc.drawImage(player.location.getBackground(),offSetLandX,offSetLandY, printLimitX, printLimitY,0,0, printLimitX *xRatio,yRatio* printLimitY);
        gc.drawImage(player.skin.getFrame(t), (player.skin.getPositionX() - offSetLandX)*xRatio, (player.skin.getPositionY()-offSetLandY)*yRatio,40*xRatio,40*yRatio);
    }

    @Override
    public void addController() {
        this.setOnKeyPressed(e -> {
                    String code = e.getCode().toString();
                    if (!input.contains(code))
                        input.add(code);
                    if (code.equals("R"))
                        System.out.println(player.skin.getPositionX() +"  "+ player.skin.getPositionY());
                }
        );

        this.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });
    }
}
