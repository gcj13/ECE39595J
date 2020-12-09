package src;

public class Monster extends Creature{

   private String monsterName;
   private int room_id;
   private int serial;
   
   public Monster( )
   {
      System.out.println("Monster()");
   }

   public void setName(String name)
   {
      monsterName = name;
      System.out.println("Monster:setName()\n" +
              "   monsterName: " + monsterName);

   }
    
   public void setID(int room, int serial)
   {
      room_id = room;
      this.serial = serial;
      System.out.println("Monster:setID()\n" +
              "   room_id: " + room_id + "\n" +
              "   serial: " + this.serial);

   }

   public String getMonsterName() {
      return monsterName;
   }

   public int getRoomId() {
      return room_id;
   }

   public int getSerial() {
      return serial;
   }
}
