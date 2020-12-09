package src;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeyStrokePrinter implements InputObserver, Runnable {

    private static int DEBUG = 1;
    private static String CLASSID = "KeyStrokePrinter";
    private static Queue<Character> inputQueue = null;
    private ObjectDisplayGrid odg;
    private static boolean ifDead = false;

    public KeyStrokePrinter(ObjectDisplayGrid grid) {
        inputQueue = new ConcurrentLinkedQueue<>();
        odg = grid;
    }

    @Override
    public void observerUpdate(char ch) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".observerUpdate receiving character " + ch);
        }
        inputQueue.add(ch);
    }

    private void rest(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean processInput() {

        char ch;
        boolean processing = true;
        while (processing) {
            if (inputQueue.peek() == null) {
                //System.out.println("inputQueue == null");
                processing = false;
            } else {
                ch = inputQueue.poll();
                int pPosX = odg.getPlayerPosX();
                int pPosY = odg.getPlayerPosY();
                if (DEBUG > 1) {
                    System.out.println(CLASSID + ".processInput peek is " + ch);
                }
                if (ch == 'h') {
                    if (!odg.Empty(pPosX-1, pPosY) && odg.GetChar(pPosX-1, pPosY) != 'X' ){
                        if (odg.CheckMons(pPosX-1, pPosY)) {

                            ifDead = Rogue.Fight(pPosX-1, pPosY);

                        } else {
                            odg.PopItem(pPosX, pPosY);
                            odg.MoveCreature(null,pPosX-1, pPosY);
                        }

                    }
                }
                else if(ch == 'l'){
                    if (!odg.Empty(pPosX+1, pPosY) && odg.GetChar(pPosX+1, pPosY) != 'X') {
                        if (odg.CheckMons(pPosX+1, pPosY) ) {

                            ifDead = Rogue.Fight(pPosX+1, pPosY);

                        } else {
                            odg.PopItem(pPosX, pPosY);
                            odg.MoveCreature(null, pPosX+1, pPosY);
                        }
                    }
                }
                else if(ch == 'j'){
                    if (!odg.Empty(pPosX, pPosY+1) && odg.GetChar(pPosX, pPosY+1) != 'X'){
                        if (odg.CheckMons(pPosX, pPosY+1)) {
                            ifDead = Rogue.Fight(pPosX, pPosY+1);

                        } else {
                            odg.PopItem(pPosX, pPosY);
                            odg.MoveCreature(null, pPosX, pPosY+1);

                        }
                    }
                }

                else if(ch == 'k'){
                    if (!odg.Empty(pPosX, pPosY-1)  && odg.GetChar(pPosX, pPosY-1) != 'X') {
                        if (odg.CheckMons(pPosX, pPosY-1)) {
                            ifDead = Rogue.Fight(pPosX, pPosY-1);

                        } else {
                            odg.PopItem(pPosX, pPosY);
                            odg.MoveCreature(null, pPosX, pPosY-1);
                        }
                    }
                } else if (ch == 'd') {
                    Rogue.Package();
                    Rogue.printMsgInfo("Please input the index of the item");
                    while (true) {
                        Character c = inputQueue.peek();
                        if (c == null) {
                            rest(60);
                            continue;
                        } else if (c >= '0' && c <= '9') {
                            Rogue.Drop(c-'0');
                            Rogue.Package();
                        }
                        break;
                    }

                } else if (ch == 'i') {
                    Rogue.Package();
                } else if (ch == 'p') {
                    if (odg.CheckItem(pPosX, pPosY) != null) {
                        Rogue.Pick(pPosX, pPosY);
                    } else {
                        Rogue.printMsgInfo("No item to pick");
                    }
                    Rogue.Package();

                } else if (ch == 'c') {
                    Rogue.takeOffArmor();
                    Rogue.Package();
                } else if (ch == 'E') {
                    Rogue.printMsgInfo("Input Y or y to exit");
                    while (true) {
                        Character c = inputQueue.peek();
                        if (c == null) {
                            rest(60);
                            continue;
                        } else if (c >= 'Y' && c <= 'y') {
                            EndGame();
                        }
                        break;
                    }
                } else if (ch == 'T') {
                    Rogue.Package();
                    Rogue.printMsgInfo("Please input the index of Weapon");
                    while (true) {
                        Character c = inputQueue.peek();
                        if (c == null) {
                            rest(60);
                            continue;
                        } else if (c >= '0' && c <= '9') {
                            Rogue.Weapon(c-'0');
                            Rogue.Package();
                        }
                        break;
                    }

                } else if (ch == 'w') {
                    Rogue.Package();
                    Rogue.printMsgInfo("Please input the index of Armor");
                    while (true) {
                        Character c = inputQueue.peek();
                        if (c == null) {
                            rest(60);
                            continue;
                        } else if (c >= '0' && c <= '9') {
                            Rogue.Armor(c-'0');
                            Rogue.Package();
                        }
                        break;
                    }

                } else if (ch == 'r') {
                    Rogue.Package();
                    Rogue.printMsgInfo("Please input the index of Scroll");
                    while (true) {
                        Character c = inputQueue.peek();
                        if (c == null) {
                            rest(60);
                            continue;
                        } else if (c >= '0' && c <= '9') {
                            Rogue.Read(c-'0');
                            Rogue.Package();
                        }
                        break;
                    }

                } else if (ch == '?') {
                    Rogue.printMsgInfo("Supported Command: h, j, k, l, d, i, p, w, c, r, ?, T, H, E");
                } else if (ch == 'H') {
                    Rogue.printMsgInfo("Please input the command for help: ");
                    while (true) {
                        Character c = inputQueue.peek();
                        if (c == null) {
                            rest(60);
                            continue;
                        } else if (c == 'h') {
                            Rogue.printMsgInfo("Go left or Hit the Monster at the left");
                        } else if (c == 'j') {
                            Rogue.printMsgInfo("Go down or Hit the Monster under the player");
                        } else if (c == 'k') {
                            Rogue.printMsgInfo("Go up or Hit the Monster above the player");
                        } else if (c == 'l') {
                            Rogue.printMsgInfo("Go right or Hit the Monster at the right");
                        } else if (c == 'd') {
                            Rogue.printMsgInfo("Drop the item in your package, and input the index of the item");
                        } else if (c == 'i') {
                            Rogue.printMsgInfo("Display the inventory");
                        } else if (c == 'p') {
                            Rogue.printMsgInfo("Pick up the item on the dungeon");
                        } else if (c == 'w') {
                            Rogue.printMsgInfo("Wear the Armor in your package, and input the index of the Armor");
                        } else if (c == 'c') {
                            Rogue.printMsgInfo("Take off the Armor in your package, and input the index of the Armor");
                        } else if (c == 'r') {
                            Rogue.printMsgInfo("Read the Scroll in your package, and input the index of the Scroll");
                        } else if (c == '?') {
                            Rogue.printMsgInfo("Display the supported command");
                        } else if (c == 'T') {
                            Rogue.printMsgInfo("Take out the weapon in your package, and input the index of the weapon");
                        } else if (c == 'H') {
                            Rogue.printMsgInfo("Display the command detail, and input the command for detail");
                        } else if (c == 'E') {
                            Rogue.printMsgInfo("Exited from the game, and input Y/y.");
                        }
                        inputQueue.poll();
                        break;
                    }
                }
                else {
                    System.out.println("character " + ch + " entered on the keyboard");
                }
            }
        }
        return !ifDead;
    }

    public static void EndGame() {
        ifDead = true;
    }


    @Override
    public void run() {
        odg.registerInputObserver(this);
        boolean working = true;
        while (working) {
            rest(20);
            working = (processInput( ));
        }
    }
}
