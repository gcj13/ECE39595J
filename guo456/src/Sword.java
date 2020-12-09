package src;

public class Sword extends Item{
  private String swordName;
  private int room_id;
  private int serial;
  private char type;

    public Sword(String name)
    {
        swordName = name;
        type = ')';
        System.out.println("Sword:Sword()\n" +
                "   swordName: " + swordName);
    }

    public void setID(int room, int serial)
    {
        room_id = room;
        this.serial = serial;
      System.out.println("Sword:setID()\n" +
              "   room_id: " + room + "\n" +
              "   serial: " + this.serial);
    }

    public String getName() {
        return swordName;
    }

    public void SetName(String name) {
        swordName = name;
    }

    public int getRoomId() {
        return room_id;
    }

    public int getSerial() {
        return serial;
    }

    @Override
    public char getType() {
        return type;
    }
}
