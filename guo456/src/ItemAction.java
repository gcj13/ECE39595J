package src;

public class ItemAction extends Action{

    private Item owner;
    public ItemAction(Item owner)
    {
        this.owner = owner;
        System.out.println("ItemAction()\n" +
                " owner:  " + this.owner);
    }

    public Item getOwner() {
        return owner;
    }

    public void setOwner(Item owner) {
        this.owner = owner;
    }

    public void Do(){}
}
