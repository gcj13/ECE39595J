package src;

public class DropPack extends CreatureAction{

    public DropPack(String name, Creature owner){
        super(owner);
        System.out.println("DropPack()\n" +
                "   name: " + name);
    }

    public void Do() {
        Rogue.Drop(0);
        Rogue.printMsgInfo(message);
    }
}
