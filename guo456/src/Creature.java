package src;


import java.util.ArrayList;

public class Creature extends Displayable{
    int hp;
    int maxHit;
    ArrayList<CreatureAction> deathActions = new ArrayList<>();
    ArrayList<CreatureAction> hitActions = new ArrayList<>();

    public Creature()
    {
        System.out.println("Creature()");
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public void setHp(int hp)
    {
        this.hp = hp;
        System.out.println("Creature:setHp()" + "\n" +
                "   hp: " + this.hp);
    }

    public void setMaxHit(int maxHit)
    {
        this.maxHit = maxHit;
        System.out.println("Displayable:setMaxHit()\n" +
                "   maxHit: " + maxHit);
    }

    
    public void addDeathAction(CreatureAction da)
    {
        deathActions.add(da);
        System.out.println("Creature:setDeathAction()" + "\n" +
                "   DeathAction: " + da);
    }

    public void addHitAction(CreatureAction ha)
    {
        hitActions.add(ha);
        System.out.println("Creature:setHitAction()" + "\n" +
                "CreateAction: " + ha);
    }

    public void Death() {
       for (CreatureAction i : deathActions) {
           i.Do();
       }
    }

    public void Hit() {
        for (CreatureAction i : hitActions) {
            i.Do();
        }
    }

}
