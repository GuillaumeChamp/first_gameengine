package com.application.Graphic.Scene;

import com.application.Game.Level.LevelElements.Layer0.Tile;
import com.application.Game.Universal.Player;
import com.application.Graphic.Game;
import com.application.Graphic.Graphic_Const;
import com.application.Graphic.Interface.Menu;
import com.application.Graphic.Interface.Options;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class Scene_outside extends Game_Scene{
    private final Player player;
    public Menu lastMenu = null;
    private int lastFight;


    public Scene_outside(Group root, Player player, Canvas canvas, double width, double height){
        super(root, canvas);
        this.player=player;
        lastFight=0;
        Game_Scene.height = height;
        Game_Scene.width = width;
        isFocus=true;
        this.addController();
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
        lastFight++;
        int FIGHT_PROTECTION = 50;
        if (lastFight< FIGHT_PROTECTION) return;
        double rng = Math.random();
        if (rng>0.95) Combat();
    }

    private void Combat(){
        Game.changeScene(new Fight_Scene(new Group(),this));
        lastFight=0;
    }

    public void Tick(){
        super.Tick();
        if(lastMenu==null) {
            paintScene();
            return;
        }
        paintMenu();
    }

    @Override
    public void performControl() {
        if (lastMenu==null) {
            walk();
            return;
        }
        lastMenu.changeOption();
    }

    private void paintMenu() {
        double printLimitX = Graphic_Const.H_TILES_PER_SCREEN*Graphic_Const.TILES_SIZE;
        double printLimitY = Graphic_Const.V_TILES_PER_SCREEN*Graphic_Const.TILES_SIZE;
        //Calculate ratio to allow resize
        double xRatio = width/ printLimitX;
        double yRatio = height/ printLimitY;
        paintScene();
        int POLICE_SIZE=30;
        double effectivePolice = POLICE_SIZE*Math.max(xRatio,yRatio);
        Rectangle2D position = lastMenu.getPosition();
        Options options = lastMenu.getOptions();
        gc.setFill(Color.BLUE);
        gc.fillRect(position.getMinX(),position.getMinY(), position.getWidth()*Math.max(xRatio,yRatio),position.getHeight()*Math.max(xRatio,yRatio));
        gc.setFill(Color.BLACK);
        for (int i=0;i<options.options.size();i++){
            if (i==options.getSelected())
                gc.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR,effectivePolice));
            else
                gc.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR,effectivePolice));
            gc.fillText(options.options.get(i), position.getMinX(), position.getMinY()+effectivePolice*(i+1));
        }
    }

    public void paintScene(){
        double t = System.nanoTime() / 1000000000.0;
        int tileSize = Graphic_Const.TILES_SIZE;
        double printLimitX = Graphic_Const.H_TILES_PER_SCREEN*tileSize;
        double printLimitY = Graphic_Const.V_TILES_PER_SCREEN*tileSize;
        //Calculate ratio to allow resize
        double xRatio = width/ printLimitX;
        double yRatio = height/ printLimitY;
        double ratio = Math.max(xRatio,yRatio);

        double offSetLandX = player.skin.getPositionX() - (printLimitX/2)-Graphic_Const.H_PLAYER_TILE_SIZE*xRatio/2;
        double offSetLandY = player.skin.getPositionY() - (printLimitY/2)-Graphic_Const.V_PLAYER_TILE_SIZE*yRatio/2;


        if (offSetLandX < 0) offSetLandX = 0;
        if (offSetLandX >= player.location.getSizeX()-printLimitX-Graphic_Const.H_PLAYER_TILE_SIZE) offSetLandX = player.location.getSizeX()-printLimitX-Graphic_Const.H_PLAYER_TILE_SIZE;
        if (offSetLandY < 0) offSetLandY = 0;
        if (offSetLandY >= player.location.getSizeY()-printLimitY-Graphic_Const.V_PLAYER_TILE_SIZE) offSetLandY = player.location.getSizeY()-printLimitY-Graphic_Const.V_PLAYER_TILE_SIZE;

        //gc.drawImage(player.location.getBackground(),offSetLandX,offSetLandY, printLimitX, printLimitY,0,0, printLimitX*xRatio,yRatio*printLimitY);
        Tile[][] tiles = player.location.getTiles();
        for(int i=0;i<tiles.length;i++)
            for (int j=0;j<tiles[0].length;j++)
                gc.drawImage(tiles[i][j].getSkin(), i * tileSize * ratio, j * tileSize * ratio, tileSize * ratio, tileSize * ratio);

        gc.drawImage(player.skin.getFrame(t), (player.skin.getPositionX() - offSetLandX)*xRatio, (player.skin.getPositionY()-offSetLandY)*yRatio,Graphic_Const.H_PLAYER_TILE_SIZE*xRatio*tileSize,Graphic_Const.V_PLAYER_TILE_SIZE*yRatio*tileSize);
    }

    @Override
    protected void clear() {
        paintScene();
    }

    @Override
    public void addController() {
        this.setOnKeyPressed(e -> {
                    String code = e.getCode().toString();
                    if (!input.contains(code))
                        input.add(code);
                    if (code.equals("R"))
                        System.out.println(player.skin.getPositionX() +"  "+ player.skin.getPositionY());
                    if (code.equals("ENTER"))
                        new Menu(this,this,new Rectangle2D(0,0,150,70), Options.MenuType.main);
                }
        );

        this.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });
    }

    @Override
    public void exit() {
        lastMenu = null;
    }
}