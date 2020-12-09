package src;

public class UpdateDisplay extends CreatureAction{

    public UpdateDisplay(String name, Creature owner){
        super(owner);
        System.out.println("UpdateDisplay()\n" +
                "   name: " + name);
    }

    public void Do() {
        if (getOwner().getType() == '@') {
            Rogue.printMsgInfo(message);
        }
    }
}
