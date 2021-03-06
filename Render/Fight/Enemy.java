package Fight;

import Universal.AnimatedImage;
import Universal.Stuff.Item;
import Universal.Stuff.Loot;

import java.util.ArrayList;

public class Enemy extends Entity{
    public final double xp;
    private final Loot[] loots;

    public Enemy(int hp, int armor, int speed, int damage, String name, AnimatedImage skin, double xp, Loot[] loots) {
        super(hp, armor, speed, damage, name, skin);
        this.xp = xp;
        this.loots = loots;
    }

    /**
     * Select randomly a target among heroes according to its position
     * complex code and loop to be fair
     * @param heroes list of all heroes alive
     */
    public void SelectTarget (ArrayList<Hero> heroes){
        target=null;
        boolean front = false;
        double rng = Math.random();
        if (rng>0.8) front = true;
        for (Hero h : heroes){
            rng = Math.random();
            if (h.isInFront){
                if (front&target==null) target=h;
                else if (rng>0.5) target=h;
            }
            else {
                if ((!front) & target==null) target=h;
                else if (rng>0.5) target=h;
            }
        }
    }

    public ArrayList<Item> loot(){
        ArrayList<Item> ans = new ArrayList<>();
        double rng = Math.random();
        for (Loot loot : loots)
            if (loot.tryToLoot(rng)) ans.add(loot);
        return ans;
    }

    @Override
    public void act() throws Exception {
        Attack(target);
    }
}
