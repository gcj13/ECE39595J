package src;

public class ChangedDisplayType extends CreatureAction {

    public ChangedDisplayType(String name, Creature owner) {
        super(owner);
        System.out.println("ChangedDisplayType()\n" +
                "   name: " + name);
    }

    public void Do() {
        ObjectDisplayGrid odg = Rogue.getOdg();
        odg.RmChar(getOwner().getPosX(), getOwner().getPosY());
        odg.AddChar(charValue, getOwner().getPosX(), getOwner().getPosY());
        Rogue.printMsgInfo(message);
    }
}