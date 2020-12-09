package src;

public class Hallucinate extends ItemAction{
    public Hallucinate(Item owner){
        super(owner);
        System.out.println("Hallucinate()");
    }

    @Override
    public void Do() {
        Rogue.player.setHallucinateMoves(intValue);
        Rogue.printMsgInfo("Hallucinate will process in next " + intValue + " step");
    }
}
