package src;

public class Armor extends Item{

    private String ArmorName;
    private int room_id;
    private int serial;
    private char type;

    public Armor(String name)
    {
        ArmorName = name;
        type = ']';
        System.out.println("Armor:Armor()\n" +
                "ArmorName: " + ArmorName);
    }

    public String getName() {
        return ArmorName;
    }

    public void setArmorName(String armorName) {
        ArmorName = armorName;
        System.out.println("Armor:setName()\n" +
                "   ArmorName: " + ArmorName);
    }

    public void setID(int room, int serial)
    {
        room_id = room;
        this.serial = serial;
        System.out.println("Armor:setID()\n" +
                "   room: " + room + "\n"+
                "   serial: " + serial);
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public void setType(char type) {
        this.type = type;
    }

    public int getRoomId() {
        return room_id;
    }

    public int getSerial() {
        return serial;
    }

    public void SetName(String name) {
        ArmorName = name;
    }

    public char getType() {
        return type;
    }
}
