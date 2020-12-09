package src;

public class CreatureAction extends Action{


    private Creature owner;
    public CreatureAction(Creature owner)
    {
        this.owner = owner;
        System.out.println("CreatureAction:CreationAction()\n" +
                "   owner: "+ this.owner);
    }

    public void setOwner(Creature owner) {
        this.owner = owner;
    }

    public Creature getOwner() {
        return owner;
    }

    public void Do(){};
}
