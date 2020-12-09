package src;

public class EndGame extends CreatureAction{

    public EndGame(String name, Creature owner){
        super(owner);
        System.out.println("EndGame()\n" +
                "   name: " + name);
    }

    public void Do() {
        KeyStrokePrinter.EndGame();
        Rogue.printMsgInfo(message);
    }
}
