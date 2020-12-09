package src;

public class Scroll extends Item{
    
    private String ScrollName;
    private int room_id;
    private int serial;
    private char type;
    
    public Scroll(String name)
    {
        ScrollName = name;
        type = '?';
        System.out.println("Scroll()\n" +
                "   ScrollName: " + ScrollName);
    }

    public void setID(int room, int serial)
    {
        room_id = room;
        this.serial = serial;
        System.out.println("Scroll:setID()\n" +
                "   room_id: " + room + "\n" +
                "   serial: " + serial);
    }

    public String getName() {
        return ScrollName;
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
