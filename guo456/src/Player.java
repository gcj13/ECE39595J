package src;

public class Player extends Creature{

    private Item weapon;
    private Item armor;
    private int hpMoves;
    private int moves;
    private String player_name;
    private int room_id;
    private int serial;
    private int score;
    private int HallucinateMoves;



    public Player() {
        this.player_name = player_name;
        this.moves = 0;
        this.HallucinateMoves = 0;
        System.out.println("Player()" + "\n" +
                "   player_name: " + this.player_name);
    }

    public void addMoves() {
        moves++;
        if (moves >= hpMoves) {
            hp++;
            moves = 0;
        }
    }

    public void setHpMoves(int hpMoves)
    {
        this.hpMoves = hpMoves;
        System.out.println("Player:setHpMoves()" + "\n" +
                "   hpMoves: " + this.hpMoves);
    }

    public void setWeapon(Item weapon)
    {
        this.weapon = weapon;
        System.out.println("Player:setWeapon()" + "\n" +
                "   weapon: " + this.weapon);
    }

    public void setArmor(Item armor)
    {
        this.armor = armor;
        System.out.println("Player:setArmor()" + "\n" +
                "   armor: " + this.armor);
    }

    public void setId(int room, int serial) {
        room_id = room;
        this.serial = serial;
        System.out.println("Player:setRoomId()" + "\n" +
                "   room_id: " + room_id + "\n" +
                "   serial: " + this.serial);
    }

    public Item getWeapon() {
        return weapon;
    }

    public Item getArmor() {
        return armor;
    }

    public int getHpMoves() {
        return hpMoves;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public int getRoomId() {
        return room_id;
    }

    public int getSerial() {
        return serial;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHallucinateMoves() {
        return HallucinateMoves;
    }

    public void setHallucinateMoves(int hallucinateMoves) {
        HallucinateMoves = hallucinateMoves;
    }
}
