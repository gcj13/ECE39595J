package src;

public class YouWin extends CreatureAction{

    public YouWin(String name, Creature owner)
    {
        super(owner);
        System.out.println("YouWin()\n" +
                "   name: " + name);
    }

    public void Do() {
        Rogue.player.setScore(Rogue.player.getScore() + intValue);
        Rogue.printMsgInfo(message);
    }
}
