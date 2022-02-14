package Universal.Stuff;

public class Equipment extends Item{
    enum Slot {hand1,hand2,head,chestplate,arms,legs,foot,accessory}
    boolean isEquip;
    Slot slot;

    public Equipment(Slot slot){
        this.slot = slot;
    }

    //TODO : build equip and un-equip
}
