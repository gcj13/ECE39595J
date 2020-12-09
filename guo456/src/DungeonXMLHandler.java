package src;

import java.util.ArrayList;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class DungeonXMLHandler extends DefaultHandler {

    // the two lines that follow declare a DEBUG flag to control
    // debug print statements and to allow the class to be easily
    // printed out.  These are not necessary for the parser.
    private static final int DEBUG = 1;
    private static final String CLASSID = "src.DungeonXMLHandler";

    // data can be called anything, but it is the variables that
    // contains information found while parsing the xml file
    private StringBuilder data = null;


    private Stack<Displayable> displayableStack = null;
    private Stack<Action> actionStack = null;

    private Dungeon dungeon = null;
    private Room roomBeingParsed = null;
    private Monster monsterBeingParsed = null;
    private Action actionBeingParsed = null;
    private Item itemBeingParsed = null;
    private Passage passageBeingParsed = null;
    private Player playerBeingParsed = null;

    private Player player = new Player();
    ArrayList<Room> rooms = new ArrayList<>();
    //ArrayList<Monster> monsters = new ArrayList<>();
    ArrayList<Item> packItems = new ArrayList<>();
    //ArrayList<Item> dunItems = new ArrayList<>();
    ArrayList<Displayable>ItemCreature = new ArrayList<>();
    ArrayList<Passage> passages = new ArrayList<>();
    //ArrayList<Scroll> scrolls = new ArrayList<>();
    //ArrayList<Sword> swords = new ArrayList<>();
    //ArrayList<Armor> armors = new ArrayList<>();



    private boolean bvisible = false;
    private boolean bposX = false;
    private boolean bposY = false;
    private boolean bwidth = false;
    private boolean bheight = false;
    private boolean bhp = false;
    private boolean bmaxhit = false;
    private boolean bactionMessage = false;
    private boolean bactionIntValue = false;
    private boolean bactionCharValue = false;
    private boolean bItemIntValue = false;
    private boolean btype = false;
    private boolean bhpMoves = false;


    public DungeonXMLHandler() {
        displayableStack = new Stack<>();
        actionStack = new Stack<>();
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Item> getPackItems() {
        return packItems;
    }
    public ArrayList<Passage> getPassages() {
        return passages;
    }

    public ArrayList<Displayable> getItemCreature() {
        return ItemCreature;
    }

    public Player getPlayer() {
        return player;
    }

    /*
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }



    public ArrayList<Item> getDunItems() {
        return dunItems;
    }

    public ArrayList<Scroll> getScrolls() {
        return scrolls;
    }

    public ArrayList<Sword> getSwords() {
        return swords;
    }

    public ArrayList<Armor> getArmors() {
        return armors;
    }

     */

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        // parse Dungeon and ObjectDisplayGrid.
        if (qName.equalsIgnoreCase("Dungeon")) {

            String dungeonName = attributes.getValue("name");
            int gameHeight = Integer.parseInt(attributes.getValue("gameHeight"));
            int width = Integer.parseInt(attributes.getValue("width"));
            int topHeight = Integer.parseInt(attributes.getValue("topHeight"));
            int bottomHeight = Integer.parseInt(attributes.getValue("bottomHeight"));

            dungeon = new Dungeon(dungeonName, width, gameHeight, topHeight, bottomHeight);
        }
        // parse Room
        else if(qName.equalsIgnoreCase("Room")) {
            int roomId = Integer.parseInt(attributes.getValue("room"));
            String roomName = "TestRoom_" + roomId;
            Room room = new Room(roomName);
            room.setId(roomId);
            //dungeon.addRoom(room);

            roomBeingParsed = room;
            rooms.add(roomBeingParsed);
            displayableStack.push(roomBeingParsed);
        }
        // parse Displayable
        else if(qName.equalsIgnoreCase("visible")) {
            bvisible = true;
        } else if(qName.equalsIgnoreCase("posX")) {
            bposX = true;
        } else if(qName.equalsIgnoreCase("posY")) {
            bposY = true;
        } else if(qName.equalsIgnoreCase("width")) {
            bwidth = true;
        } else if(qName.equalsIgnoreCase("height")) {
            bheight = true;
        } else if(qName.equalsIgnoreCase("type")) {
            btype = true;
        } else if(qName.equalsIgnoreCase("hp")) {
            bhp = true;
        } else if(qName.equalsIgnoreCase("hpMoves")) {
            bhpMoves = true;
        }  else if(qName.equalsIgnoreCase("maxhit")) {
            bmaxhit = true;
        } else if(qName.equalsIgnoreCase("actionMessage")) {
            bactionMessage = true;
        } else if(qName.equalsIgnoreCase("actionIntValue")) {
            bactionIntValue = true;
        } else if(qName.equalsIgnoreCase("actionCharValue")) {
            bactionCharValue = true;
        } else if(qName.equalsIgnoreCase("ItemIntValue")){
            bItemIntValue = true;
        }

        // parse Monster
        else if(qName.equalsIgnoreCase("Monster")) {

            String monsterName = attributes.getValue("name");
            Monster monster = new Monster();

            int monster_RoomId = Integer.parseInt(attributes.getValue("room"));
            int monster_Serial = Integer.parseInt(attributes.getValue("serial"));
            
            monster.setName(monsterName);
            monster.setID(monster_RoomId, monster_Serial);

            monsterBeingParsed = monster;
            //monsters.add(monsterBeingParsed);
            ItemCreature.add(monster);
            displayableStack.push(monsterBeingParsed);
        }

        // parse Player
        else if(qName.equalsIgnoreCase("Player")) {

            int playerRoom = Integer.parseInt(attributes.getValue("room"));
            int playerSerial = Integer.parseInt(attributes.getValue("serial"));

            Player player = new Player();
            player.setId(playerRoom, playerSerial);
            player.setType('@');
            playerBeingParsed = player;
            this.player = player;
            ItemCreature.add(player);
            displayableStack.push(playerBeingParsed);

        }

        // parse CreatureAction
        else if(qName.equalsIgnoreCase("CreatureAction")) {

            String creatureName = attributes.getValue("name");
            String creatureType = attributes.getValue("type");


            if(creatureName.equals("Remove")){
                if(monsterBeingParsed != null){
                    actionBeingParsed = new Remove(creatureName, monsterBeingParsed);
                }
                else{
                    actionBeingParsed = new Remove(creatureName, playerBeingParsed);
                }
            }

            else if(creatureName.equals("YouWin")){
                if(monsterBeingParsed != null){
                    actionBeingParsed = new YouWin(creatureName, monsterBeingParsed);
                }

                else{
                    actionBeingParsed = new YouWin(creatureName, playerBeingParsed);
                }
            }

            else if(creatureName.equals("Teleport")){
                if(monsterBeingParsed != null){
                    actionBeingParsed = new Teleport(creatureName, monsterBeingParsed);
                }

                else{
                    actionBeingParsed = new Teleport(creatureName, playerBeingParsed);
                }
            }

            else if(creatureName.equals("ChangeDisplayedType")){
                if(monsterBeingParsed != null){
                    actionBeingParsed = new ChangedDisplayType(creatureName, monsterBeingParsed);
                }

                else{
                    actionBeingParsed = new ChangedDisplayType(creatureName, playerBeingParsed);
                }
            }

            else if(creatureName.equals("UpdateDisplay")){
                if(monsterBeingParsed != null){
                    actionBeingParsed = new UpdateDisplay(creatureName, monsterBeingParsed);
                }

                else{
                    actionBeingParsed = new UpdateDisplay(creatureName, playerBeingParsed);
                }
            }

            else if(creatureName.equals("EndGame")){
                if(monsterBeingParsed != null){
                    actionBeingParsed = new EndGame(creatureName, monsterBeingParsed);
                }

                else{
                    actionBeingParsed = new EndGame(creatureName, playerBeingParsed);
                }
            }

            else if(creatureName.equals("DropPack")){
                if(monsterBeingParsed != null){
                    actionBeingParsed = new DropPack(creatureName, monsterBeingParsed);
                }

                else{
                    actionBeingParsed = new DropPack(creatureName, playerBeingParsed);
                }
            }


            if(monsterBeingParsed != null){
                if(creatureType.equals("death")){
                    monsterBeingParsed.addDeathAction((CreatureAction) actionBeingParsed);
                }
                else if(creatureType.equals("hit")){
                    monsterBeingParsed.addHitAction((CreatureAction) actionBeingParsed);
                }
            } else if(playerBeingParsed != null){
                if(creatureType.equals("death")){
                    playerBeingParsed.addDeathAction((CreatureAction) actionBeingParsed);
                }
                else if(creatureType.equals("hit")){
                    playerBeingParsed.addHitAction((CreatureAction) actionBeingParsed);
                }
            }

            actionStack.push(actionBeingParsed);
            
        }

        // parse Scroll
        else if(qName.equalsIgnoreCase("Scroll")) {

            String scrollName = attributes.getValue("name");
            int scrollRoom = Integer.parseInt(attributes.getValue("room"));
            int scrollSerial = Integer.parseInt(attributes.getValue("serial"));

            Scroll scroll = new Scroll(scrollName);
            scroll.setID(scrollRoom, scrollSerial);
            


            if(playerBeingParsed != null)
            {
                //playerBeingParsed.setArmor(scroll);
                //scroll.setOwner(playerBeingParsed);
                packItems.add(scroll);
            } else {
                ItemCreature.add(scroll);
                //scrolls.add(scroll);
                //dunItems.add(scroll);
            }
            
            itemBeingParsed = scroll;
            displayableStack.push(itemBeingParsed);

        }
        // parse Armor
        else if(qName.equalsIgnoreCase("Armor")){
            String armor_name = attributes.getValue("name");
            int armor_room = Integer.parseInt(attributes.getValue("room"));
            int armor_serial = Integer.parseInt(attributes.getValue("serial"));

            Armor armor = new Armor(armor_name);
            armor.setID(armor_room, armor_serial);

            if(playerBeingParsed != null)
            {
                //playerBeingParsed.setArmor(armor);
                //armor.setOwner(playerBeingParsed);
                packItems.add(armor);
            } else {
                ItemCreature.add(armor);
                //dunItems.add(armor);
                //armors.add(armor);
            }
            
            itemBeingParsed = armor;
            displayableStack.push(itemBeingParsed);
        }

        // parse Sword
        else if(qName.equalsIgnoreCase("Sword")){
            String swordName = attributes.getValue("name");
            int swordRoom = Integer.parseInt(attributes.getValue("room"));
            int swordSerial = Integer.parseInt(attributes.getValue("serial"));

            Sword swd = new Sword(swordName);
            swd.setID(swordRoom, swordSerial);
            if(playerBeingParsed != null)
            {
                //playerBeingParsed.setWeapon(swd);
                //swd.setOwner(playerBeingParsed);
                packItems.add(swd);
            } else {
                ItemCreature.add(swd);
                //swords.add(swd);
                //dunItems.add(swd);
            }
            itemBeingParsed = swd;
            displayableStack.push(itemBeingParsed);
        }

        // parse ItemAction
        else if(qName.equalsIgnoreCase("ItemAction")) {

            String itemName = attributes.getValue("name");

            if(itemName.equals("BlessArmor")){
                actionBeingParsed = new BlessCurseOwner(itemBeingParsed);
            }

            else if(itemName.equals("Hallucinate")){
                actionBeingParsed = new Hallucinate(itemBeingParsed);
            }

            actionStack.push(actionBeingParsed);
            itemBeingParsed.addItemActions((ItemAction)actionBeingParsed);
        }

        // parse Passage
        else if(qName.equalsIgnoreCase("Passage")){

            int room1 = Integer.parseInt(attributes.getValue("room1"));
            int room2 = Integer.parseInt(attributes.getValue("room2"));     
            Passage passage = new Passage();
            passage.setID(room1, room2);
            passageBeingParsed = passage;
            passages.add(passageBeingParsed);
            displayableStack.push(passageBeingParsed);
        } 

        data = new StringBuilder();      
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    
        if(bvisible){
            Displayable x = displayableStack.peek();
            
            x.setVisible();
            bvisible = false;
        }

        else if(bposX){
            Displayable x = displayableStack.peek();
            x.SetPosX(Integer.parseInt(data.toString()));
            bposX = false;
        }

        else if(bposY){

            Displayable x = displayableStack.peek();
            
            x.setPosY(Integer.parseInt(data.toString()));
            bposY = false;
        }

        else if(bwidth){
            Displayable x = displayableStack.peek();
            
            x.SetWidth(Integer.parseInt(data.toString()));
            bwidth = false;
        }

        else if(bheight){
            Displayable x = displayableStack.peek();
            
            x.setHeight(Integer.parseInt(data.toString()));
            bheight = false;
        }
        
        else if(bhp){
            Displayable x = displayableStack.peek();

            x.setHp(Integer.parseInt(data.toString()));
            bhp = false;
        }

        else if(bmaxhit){
            Displayable x = displayableStack.peek();
            
            x.setMaxHit(Integer.parseInt(data.toString()));
            bmaxhit = false;
        }

        else if(bactionMessage){
            Action x = actionStack.peek();
            x.setMessage(data.toString());
            bactionMessage = false;
        }

        else if(bactionCharValue){
            Action x = actionStack.peek();

            x.setCharValue(data.toString().charAt(0));
            bactionCharValue = false;
        }

        else if(bactionIntValue){
            Action x = actionStack.peek();
            x.setIntValue(Integer.parseInt(data.toString()));
            bactionIntValue = false;
        }

        else if(bItemIntValue){
            Displayable x = displayableStack.peek();
            
            x.setIntValue(Integer.parseInt(data.toString()));
            bItemIntValue = false;
        }

        else if(btype){
            Displayable x = displayableStack.peek();
            
            x.setType(data.toString().charAt(0));
            btype = false;
        }

        else if(bhpMoves){
            Displayable x = displayableStack.peek();
                
            x.setHpMoves(Integer.parseInt(data.toString()));
            bhpMoves = false;
        }

        if(qName.equalsIgnoreCase("Dungeon")){
            //dungeon = null;
        }

        else if(qName.equalsIgnoreCase("Rooms")){
        }

        else if(qName.equalsIgnoreCase("Room")){

            if (dungeon != null) {
                roomBeingParsed.setPosY(dungeon.getTopHeight()
                        +roomBeingParsed.getPosY());
            }

            roomBeingParsed = null;
            displayableStack.pop();
        }

        else if(qName.equalsIgnoreCase("Monster")){
            if (roomBeingParsed != null) {
                monsterBeingParsed.SetPosX(monsterBeingParsed.getPosX()
                        +roomBeingParsed.getPosX());
                monsterBeingParsed.setPosY(monsterBeingParsed.getPosY()
                        +roomBeingParsed.getPosY()+dungeon.getTopHeight());
            }
            monsterBeingParsed = null;
            displayableStack.pop();
        }

        else if(qName.equalsIgnoreCase("Player")){
            if (roomBeingParsed != null) {
                playerBeingParsed.SetPosX(playerBeingParsed.getPosX()
                        +roomBeingParsed.getPosX());
                playerBeingParsed.setPosY(playerBeingParsed.getPosY()
                        +roomBeingParsed.getPosY()+dungeon.getTopHeight());
            }
            playerBeingParsed = null;
            displayableStack.pop();

        }

        else if(qName.equalsIgnoreCase("Sword")){
            if (roomBeingParsed != null) {
                itemBeingParsed.SetPosX(itemBeingParsed.getPosX()
                        +roomBeingParsed.getPosX());
                itemBeingParsed.setPosY(itemBeingParsed.getPosY()
                        +roomBeingParsed.getPosY()+dungeon.getTopHeight());
            }

            itemBeingParsed = null;
            displayableStack.pop();
        }

        else if(qName.equalsIgnoreCase("Armor")){
            if (roomBeingParsed != null) {
                itemBeingParsed.SetPosX(itemBeingParsed.getPosX()
                        +roomBeingParsed.getPosX());
                itemBeingParsed.setPosY(itemBeingParsed.getPosY()
                        +roomBeingParsed.getPosY()+dungeon.getTopHeight());
            }
            itemBeingParsed = null;
            displayableStack.pop();
        }

        else if(qName.equalsIgnoreCase("Scroll")){
            if (roomBeingParsed != null) {
                itemBeingParsed.SetPosX(itemBeingParsed.getPosX()
                        +roomBeingParsed.getPosX());
                itemBeingParsed.setPosY(itemBeingParsed.getPosY()
                        +roomBeingParsed.getPosY()+dungeon.getTopHeight());
            }
            itemBeingParsed = null;
            displayableStack.pop();
        }

        else if(qName.equalsIgnoreCase("Passage")){
            if (dungeon != null) {
                ArrayList<Integer>PosYs = passageBeingParsed.getPosYs();
                for (int i = 0; i < PosYs.size(); i++) {
                    PosYs.set(i, PosYs.get(i) + dungeon.getTopHeight());
                }
            }

            passageBeingParsed = null;
            displayableStack.pop();
        }

        else if(qName.equalsIgnoreCase("CreatureAction")){
            actionBeingParsed = null;
            actionStack.pop();
        }

        else if(qName.equalsIgnoreCase("ItemAction")){
            actionBeingParsed = null;
            actionStack.pop();
        }

        else if(qName.equalsIgnoreCase("Passages")){
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }

}