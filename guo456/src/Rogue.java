package src;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

import org.xml.sax.SAXException;
public class Rogue implements Runnable{

	private static final int DEBUG = 0;
	private boolean isRunning;
    public static final int FRAMESPERSECOND = 60;
    public static final int TIMEPERLOOP = 1000000000 / FRAMESPERSECOND;
    private static ObjectDisplayGrid odg = null;
    private Thread keyStrokePrinter;
	static int width;
	static int gameHeight;
	static int topHeight;
	static int bottomHeight;
	static int height;
	private String msg = "";

	static SAXParserFactory saxParserFactory;
	static SAXParser saxParser;
    static DungeonXMLHandler parser;
	
	static Dungeon dungeon;
	static Player player;
	static char[] randChar = {']', ')', '?', 'T', 'S', 'H', 'X', '.', '+', '#'};
	static ArrayList<Item> pack;
	static ArrayList<Room> rooms;
	static ArrayList<Passage> passages;
	static ArrayList<Displayable> ICs;
	static Displayable[][] elems;
	static Stack<Displayable>[][] itemStack;

    public Rogue(int width, int topHeight, int gameHeight, int bottomHeight){
		odg = new ObjectDisplayGrid(width, topHeight, gameHeight, bottomHeight);
    }

    public void run(){
		odg.initializeDisplay();
		odg.setPlayer(player);
		printAll();
    }

    public static void main(String[] args) throws Exception{
        String fileName = null;
        switch (args.length) {

        case 1: fileName = args[0];
			break;
        default: System.out.println("java Rogue <xmlfilename>");
			return;
        }

        saxParserFactory = SAXParserFactory.newInstance();

        try {
            saxParser = saxParserFactory.newSAXParser();
            parser = new DungeonXMLHandler();
            saxParser.parse(new File(fileName), parser);

            player = parser.getPlayer();
			rooms = parser.getRooms();
			dungeon = parser.getDungeon();
			passages = parser.getPassages();
			ICs = parser.getItemCreature();
			pack = parser.getPackItems();
			
			width = dungeon.getWidth();
			gameHeight = dungeon.getGameHeight();
			topHeight = dungeon.getTopHeight();
			bottomHeight = dungeon.getBottomHeight();
			height = gameHeight+topHeight+bottomHeight;
			
		}catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace(System.out);
		}
		
		Rogue rogue = new Rogue(width, topHeight, gameHeight, bottomHeight);
        elems = new Displayable[width][topHeight+gameHeight];
        itemStack = (Stack<Displayable>[][]) new Stack[width][topHeight+gameHeight];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < topHeight + gameHeight; j++) {
				itemStack[i][j] = new Stack<>();
			}
		}

        Thread rogueThread = new Thread(rogue);
        rogueThread.start();

        rogue.keyStrokePrinter = new Thread(new KeyStrokePrinter(odg));
        rogue.keyStrokePrinter.start();

        rogueThread.join();
        rogue.keyStrokePrinter.join();//block main thread forever.
	}

	private void printAll(){
		printDungeon();
		printItemsCreatures();
		printPlayerInfo();
		printMsgInfo("");
		printPackInfo("");
	}

	private static void printDungeon(){

		for (Room r : rooms){
			int row, col;
			for(row = r.getPosX(); row < (r.width() - 1); row++) {
				odg.AddChar('X', row, r.getPosY());
				odg.AddChar('X', row,r.height() - 1);
			}
			for(col = r.getPosY(); col <= (r.height() - 1); col++) {
				odg.AddChar('X', r.getPosX(), col);
				odg.AddChar('X', r.width() - 1,  col);
			}
			for(row = r.getPosX() + 1; row < (r.width() - 1); row++) {
				for(col = r.getPosY() + 1; col < (r.height() - 1); col++){
					odg.AddChar('.', row, col);
				}
			}
		}

		for (Passage p : passages) {
			int i, j;
			ArrayList<Integer> poXs = p.getPosXs();
			ArrayList<Integer> poYs = p.getPosYs();
			int point = poXs.size();

			odg.AddChar('+', poXs.get(0), poYs.get(0));
			PushPassage(poXs.get(0), poYs.get(0), '+');
			for (i=0; i<(point-1); i++) {
				if (poXs.get(i).equals(poXs.get(i + 1))) {

					if (poYs.get(i) > poYs.get(i+1)) {
						for (j = poYs.get(i) - 1; j >= poYs.get(i + 1); j--) {
							odg.AddChar('#', poXs.get(i), j);
							PushPassage(poXs.get(i), j, '#');


						}
					} else if (poYs.get(i) < poYs.get(i+1)) {
						for ( j= poYs.get(i) + 1; j <= poYs.get(i+1); j++) {
							odg.AddChar('#', poXs.get(i), j);
							PushPassage(poXs.get(i), j, '#');
						}
					}
				} else if (poYs.get(i).equals(poYs.get(i + 1))) {
					if (poXs.get(i) > poXs.get(i+1)) {
						for (j = poXs.get(i) - 1; j >= poXs.get(i + 1); j--) {
							odg.AddChar('#', j, poYs.get(i));
							PushPassage(j, poYs.get(i), '#');
						}
					} else if (poXs.get(i) < poXs.get(i+1)) {
						for ( j= poXs.get(i) + 1; j <= poXs.get(i+1); j++) {
							odg.AddChar('#', j, poYs.get(i));
							PushPassage(j, poYs.get(i), '#');
						}
					}
				}
			}
			odg.AddChar('+', poXs.get(point-1), poYs.get(point-1));
			PushPassage(poXs.get(point-1), poYs.get(point-1), '+');
		}
	}

	private void printItemsCreatures() {
		for (Displayable ic : ICs) {
			char ch = ic.getType();
			odg.AddChar(ch, ic.getPosX(), ic.getPosY());
			elems[ic.getPosX()][ic.getPosY()] = ic;
			if (ic.getType() == ']' || ic.getType() == ')' || ic.getType() == '?') {
				itemStack[ic.getPosX()][ic.getPosY()].push((Item) ic);
			}
		}
	}

	public static void printPlayerInfo(){
		String playerInfo = "HP: " + player.getHp() +
				"  Score:  " + player.getScore();
		odg.CleanRow(0);
		odg.AddRow(0, playerInfo);
	}
	public static void printPackInfo(String msg){
		String packInfo = "Pack: " + msg;
		odg.CleanRow(height-3);
		odg.AddRow(height-3, packInfo);
	}

	public static void printMsgInfo(String msg){
		String msgInfo = "Info: " + msg;
		odg.CleanRow(height-1);
		odg.AddRow(height-1, msgInfo);
	}

	public static ObjectDisplayGrid getOdg() {
		return odg;
	}

	public static boolean Fight(int PosX, int PosY) {
    	boolean ifDead = false;
    	Monster monster = (Monster) elems[PosX][PosY];

    	int pPoints = new Random().nextInt(player.maxHit+1);
    	int mPoints = new Random().nextInt(monster.maxHit+1);

    	if (player.getWeapon() != null) {
    		pPoints += player.getWeapon().getIntValue();
		}

    	if (player.getArmor() != null) {
    		mPoints -= player.getArmor().getIntValue();
		}

		String msg = "Player hit to " + monster.getMonsterName() + " " + pPoints + " points, ";
		msg += monster.getMonsterName() + " hit to player" + " " + mPoints + " points";
		printMsgInfo(msg);

		player.Hit();
		monster.Hit();

		if ((player.hp = player.hp-mPoints) <= 0) {
			player.hp = 0;
			System.out.println("Player Dead");
			printMsgInfo("Player Dead");
			player.Death();
			ifDead = true;

		}

    	if ((monster.hp = monster.hp - pPoints) <= 0) {
    		odg.RmChar(PosX, PosY);
    		elems[PosX][PosY] = null;
			//System.out.println("Monster Dead");
			monster.Death();
		}
		printPlayerInfo();

		return ifDead;
	}

	public static void Package() {

    	StringBuilder packageMsg = new StringBuilder();

		for (int i = 0; i < pack.size(); i++){
			Item item = pack.get(i);
			packageMsg.append(i).append(". ").append(item.getName());
			if (player.getArmor() != null && player.getArmor() == item) {
				packageMsg.append("(a)");
			} else if (player.getWeapon() != null && player.getWeapon() == item) {
				packageMsg.append("(w)");
			}
			packageMsg.append(" ");
		}
		printPackInfo(packageMsg.toString());
	}

	public static void Pick(int posX, int posY) {
    	pack.add((Item) itemStack[posX][posY].pop());
		printMsgInfo("Pick the item: " + pack.get(pack.size()-1).getName());

	}

	public static void Drop(int index) {
    	if (index < pack.size()) {
    		Item item = pack.get(index);
    		if (item == player.getArmor()) {
    			player.setArmor(null);
			}
    		if (item == player.getWeapon()) {
    			player.setWeapon(null);
			}
    		itemStack[player.getPosX()][player.getPosY()].push(item);
    		printMsgInfo("Drop the item: " + item.getName());
    		pack.remove(index);
		}
	}

	public static void Empty() {
    	for (int i = 0; i < pack.size(); i++) {
    		Drop(i);
		}

	}

	public static void takeOffArmor() {
    	if (player.getArmor() == null) {
    		Rogue.printMsgInfo("No armor is being worn");
    		return;
		}
		Rogue.printMsgInfo("Has take off the Armor: " + player.getArmor().getName());
		player.setArmor(null);
	}

	public static void Weapon(int index) {
    	if (index < pack.size() && pack.get(index).getType() == ')') {
    		player.setWeapon(pack.get(index));
    		Rogue.printMsgInfo("Set Weapon: " + player.getWeapon().getName());
    		return;
		}
    	Rogue.printMsgInfo("Can not set Weapon");
	}

	public static void Armor(int index) {
		if (index < pack.size() && pack.get(index).getType() == ']') {
			player.setArmor(pack.get(index));
			Rogue.printMsgInfo("Set Armor: " + player.getArmor().getName());
			return;
		}
		Rogue.printMsgInfo("Can not set Armor");
	}

	public static void Read(int index) {
		if (index < pack.size() && pack.get(index).getType() == '?') {
			pack.get(index).DoAction();
			pack.remove(index);
			return;
		}
		Rogue.printMsgInfo("Can not read this Scroll");
	}

	public static void PushPassage(int posX, int posY, char type) {
    	Item item = new Item();
    	item.setType(type);
    	item.SetPosX(posX);
    	item.setPosY(posY);
    	itemStack[posX][posY].push(item);
	}

	public static void HallucinateProcess() {
    	if (player.getHallucinateMoves() > 0) {
			for (int i = 0; i < width; i++) {
				for (int j = topHeight; j < topHeight + gameHeight; j++) {
					char realChar = odg.GetChar(i,j);
					if (realChar == ' ') {
						continue;
					}
					odg.AddChar(randChar[new Random().nextInt(randChar.length)], i,j);
					odg.SetChar(realChar, i, j);
				}
			}
		} else if (player.getHallucinateMoves() == 0) {
    		odg.Paint();
		}
	}

}