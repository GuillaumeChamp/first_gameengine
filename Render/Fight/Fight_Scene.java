package Fight;
import Universal.Game_Scene;
import Universal.Stuff.Inventory;
import Universal.Stuff.Item;
import Universal.Stuff.Loot;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Fight_Scene extends Game_Scene {
    //TODO : might hold enemy but not the hero except on static field
    private final Image background = new Image(".//Resources/OutDoor.Level/town_land.png");
    private ArrayList<Enemy> enemies;
    private ArrayList<Hero> heroes;
    private boolean endOfTurn;
    private ArrayList<Item> loots;
    private double xpearn = 0;

    /**
     * Create a new scene to be ready for a fight
     * @param root root of the app
     */
    public Fight_Scene(Group root){
        super(root,null);
        //TODO make a loader of hero
        //TODO read the list of enemy and load them
    }

    private Entity determineOrder(ArrayList<Entity> entities){
        Entity first = entities.get(0);
        for (Entity e: entities) {
            if (e.getSpeed()> first.getSpeed()) first = e;
        }
        return first;
    }

    private void playTurn(){
        endOfTurn = false;
        ArrayList<Entity> entities = new ArrayList<>();
        for(Enemy e: enemies) e.SelectTarget(heroes);
        entities.addAll(enemies);
        entities.addAll(heroes);
        while (!entities.isEmpty()){
            Entity actor = determineOrder(entities);
            entities.remove(actor);
            try {
                actor.act();
            }catch (Exception e){
                if (e.getMessage().equals("EndOfEntity")) deadHandler(actor,entities);
            }
        }
        for (Entity e : heroes) e.RemoveAlteration();
        for (Entity e : enemies) e.RemoveAlteration();
    }
    private void win(){
        //TODO : swap the scene and grant loot and xps
        int number = heroes.size();
        for (Hero hero : heroes) hero.grantXp(xpearn/number);
        for (Item item : loots) Inventory.add(item);
    }
    private void loss(){
        //TODO : swap the scene and go back to an int and maybe lose money
    }
    @Override
    public void Tick(double t) {
        //TODO : make the attack selection
        if (endOfTurn) playTurn();
    }

    @Override
    public void addController() {
        //TODO : load control map
    }

    /**
     * Sub-function of playTurn use to handle a dead entity
     * @param actor killer of the entity (the one who target the cause of the exception)
     * @param entities list of the waiting entities
     */
    private void deadHandler(Entity actor, ArrayList<Entity> entities){
        Entity dead = actor.getTarget();
        entities.remove(dead);
        if(dead.getClass()==Hero.class) {
            heroes.remove(dead);
            if (heroes.isEmpty()) loss();
        }
        if (dead.getClass()==Enemy.class) {
            try {
                enemies.remove(dead);
                loots.addAll(((Enemy) dead).loot());
                xpearn =+ ((Enemy) dead).xp;
                if (enemies.isEmpty()) win();
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
    }
}
