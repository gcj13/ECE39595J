package src;

public class BlessCurseOwner extends ItemAction{

    public BlessCurseOwner(Item owner){
        super(owner);
        System.out.println("BlessCurseOwner()");
    }

    public void Do() {
        if (charValue == 'w' && Rogue.player.getWeapon() != null) {
            Item weapon = Rogue.player.getWeapon();
            weapon.setIntValue(weapon.getIntValue() + intValue);
            weapon.SetName(weapon.getIntValue() + " Sword");
        } else if (charValue == 'a' && Rogue.player.getArmor() != null) {
            Item armor = Rogue.player.getArmor();
            armor.setIntValue(armor.getIntValue() + intValue);
            armor.SetName(armor.getIntValue() + " Armor");
        } else {
            message = "Scroll do not bless or curse any item";
        }
        Rogue.printMsgInfo(message);
    }

}
