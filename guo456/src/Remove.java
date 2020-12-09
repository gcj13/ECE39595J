package src;

public class Remove extends CreatureAction{

    public Remove(String name, Creature owner)
    {
        super(owner);
        System.out.println("Remove:Remove()\n" +
                "   name: " + name);
    }

    public void Do() {
        ObjectDisplayGrid odg = Rogue.getOdg();
        odg.RmChar(getOwner().getPosX(), getOwner().getPosY());
        Rogue.printMsgInfo(message);
    }
}
