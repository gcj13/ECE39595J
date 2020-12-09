package src;

public class Dungeon {

    private String name;
    private int width;
    private int gameHeight;
    private int topHeight;
    private int bottomHeight;


    public Dungeon(String name, int width, int gameHeight, int topHeight, int bottomHeight) {
        this.name = name;
        this.width = width;
        this.gameHeight = gameHeight;
        this.topHeight = topHeight;
        this.bottomHeight = bottomHeight;

        System.out.println("Dungeon()\n" +
                "   name: " + this.name + "\n" +
                "   width: " + this.width +"\n" +
                "   topHeight: " + this.topHeight  + "\n" +
                "   gameHeight: " + this.gameHeight + "\n" +
                "   bottomHeight: " + this.bottomHeight);
    }

    public void getDungeon(String name, int width, int gameHeight)
    {
        this.name = name;
        this.width = width;
        this.gameHeight = gameHeight;

        System.out.println("Dungeon:getDungeon()\n" +
                "   name: " + this.name + "\n" +
                "   width: " + this.width +"\n" +
                "   gameHeight: " + this.gameHeight);
    }

    public int getWidth() {
        return width;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public int getTopHeight() {
        return topHeight;
    }

    public int getBottomHeight() {
        return bottomHeight;
    }

    /*
    public void addRoom(Room room)
    {
        if (rooms == null) {
            rooms = new ArrayList<>();
        }

        rooms.add(room);
        System.out.println("Dungeon:addRoom()\n" +
                "   room: " + room);
    }

    public void addCreature(Creature creature)
    {
        if (creatures == null) {
            creatures = new ArrayList<>();
        }

        creatures.add(creature);
        System.out.println("Dungeon:addCreature()\n" +
                "   creature: " + creature);
    }

    public void addPassage(Passage passage)
    {
        if (passages == null) {
            passages = new ArrayList<>();
        }

        passages.add(passage);
        System.out.println("Dungeon:addPassage()\n" +
                "   passage: " + passage);
    }

    public void addItem(Item item)
    {
        if (items == null) {
            items = new ArrayList<>();
        }

        items.add(item);
        System.out.println("Dungeon:addItem()\n" +
                "   item: " + item);
    }

     */
}