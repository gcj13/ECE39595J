package src;

import java.util.ArrayList;

public class Passage extends Structure{

    private String passageName;
    private int roomId_1;
    private int roomId_2;
    ArrayList<Integer> PosX = new ArrayList<>();
    ArrayList<Integer> PosY = new ArrayList<>();



    public Passage()
    {
        System.out.println("Passage()");
    }

    public void setName(String name)
    {
        passageName = name;
        System.out.println("Passage:setName()\n" +
                "   passageName: " + name);
    }

    public void setID(int room1, int room2)
    {
        roomId_1 = room1;
        roomId_2 = room2;
        System.out.println("Passage:setID()\n" +
                "   roomId_1: " + roomId_1 + "\n" +
                "   roomId_2: " + roomId_2);
    }

    public void SetPosX(int x)
    {
        this.PosX.add(x);
        System.out.println("Passage:SetPosX()\n" +
                "   PosX: " + x);
    }

    public void setPosY(int y)
    {
        this.PosY.add(y);
        System.out.println("Displayable:setPosY()\n" +
                "   PosY: " + y);
    }

    public ArrayList<Integer> getPosXs() {
        return PosX;
    }

    public ArrayList<Integer> getPosYs() {
        return PosY;
    }

    public String getPassageName() {
        return passageName;
    }

    public int getRoomId_1() {
        return roomId_1;
    }

    public int getRoomId_2() {
        return roomId_2;
    }
}
