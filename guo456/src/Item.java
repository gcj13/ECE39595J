package src;

import java.util.ArrayList;

public class Item extends Displayable{

    private Creature owner;
    private ArrayList<ItemAction> ias;
    public void setOwner(Creature owner)
    {
        this.owner = owner;
        System.out.println("Item: setOwner()\n" +
                "   owner: " + this.owner);
    }

    public Creature getOwner() {
        return owner;
    }

    public String getName(){
        return "";
    }


    public void addItemActions(ItemAction ia) {
        if (ias == null) {
            ias = new ArrayList<>();
        }

        ias.add(ia);
        System.out.println("Item: addItemActions()\n" +
                "   ItemAction: " + ia);
    }

    public void DoAction() {
        for (ItemAction i : ias) {
            i.Do();
        }
    }

    public void SetName(String name){}




}
