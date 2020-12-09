package src;

public class Room extends Structure{
    
    private String roomName;
    private int roomId;

    public Room(String name)
    {
        roomName = name;
        System.out.println("Room:Room()\n" +
                "   roomName: " + roomName);
    }

    public void setId(int room)
    {
        roomId = room;
        System.out.println("Room:setID()\n" +
                "   roomId: " + roomId);
    }

    public String getRoomName() {
        return roomName;
    }

    public int getRoomId() {
        return roomId;
    }
}
