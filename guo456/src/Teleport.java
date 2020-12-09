package src;
import java.util.Random;

public class Teleport extends CreatureAction{

    public Teleport(String name, Creature owner){
        super(owner);
        System.out.println("Teleport:Teleport()\n" +
                "   name: " + name);

    }

    public void Do() {
        ObjectDisplayGrid odg = Rogue.getOdg();
        odg.PopItem(getOwner().getPosX(), getOwner().getPosY());

        int randPosX = new Random().nextInt(odg.width);
        int randPosY = new Random().nextInt(odg.gameHeight);

        while (odg.GetChar(randPosX, randPosY) != '.') {
            randPosX = new Random().nextInt(odg.width);
            randPosY = new Random().nextInt(odg.gameHeight);
        }

        odg.MoveCreature(getOwner(), randPosX, randPosY);
        if (getOwner().getType() != '@') {
            Rogue.elems[getOwner().getPosX()][getOwner().getPosY()] = null;

            Rogue.elems[randPosX][randPosY] = getOwner();
        }
        Rogue.printMsgInfo(message);
    }
}
