package src;
import asciiPanel.AsciiPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;


public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject {

    private static final int DEBUG = 0;
    private static final String CLASSID = ".ObjectDisplayGrid";

    private static AsciiPanel terminal;
    private Char[][] objectGrid = null;

    private List<InputObserver> inputObservers = null;

    int width;
    int topHeight;
    int gameHeight;
    int bottomHeight;
    int height;

    private Player player;


    public ObjectDisplayGrid(int width, int topHeight, int gameHeight, int bottomHeight){
        this.width = width;
        this.topHeight = topHeight;
        this.gameHeight = gameHeight;
        this.bottomHeight = bottomHeight;
        height = gameHeight + topHeight + bottomHeight;

        terminal = new AsciiPanel(this.width, this.height);

        objectGrid =  new Char[this.width][this.height];

        initializeDisplay();

        super.add(terminal);
        super.setSize(this.width * 9, this.height * 17);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
        terminal.setVisible(true);
        super.addKeyListener(this);
        inputObservers = new ArrayList<>();
        super.repaint();
    }

    @Override
    public void registerInputObserver(InputObserver observer) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        }
        inputObservers.add(observer);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        }
        KeyEvent keypress = (KeyEvent) e;
        notifyInputObservers(keypress.getKeyChar());
    }

    private void notifyInputObservers(char ch) {
        for (InputObserver observer : inputObservers) {
            observer.observerUpdate(ch);
            if (DEBUG > 0) {
                System.out.println(CLASSID + ".notifyInputObserver " + ch);
            }
        }
    }

    public void keyPressed(KeyEvent e){

    }

    public void keyReleased(KeyEvent e){

    }

    public final void initializeDisplay() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                AddChar(' ', i, j);
            }
        }
        terminal.repaint();
    }

    public void addObjectToDisplay(char c, int x, int y) {

        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y] = new Char(c);
                writeToTerminal(x, y);
            }
        }
    }

    public void addObjectNotDisplay(char c, int x, int y) {

        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y] = new Char(c);
            }
        }
    }

    public void removeObjectToDisplay(int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y] = new Char('.');
                writeToTerminal(x, y);
            }
        }
    }

    private void writeToTerminal(int x, int y) {
        char ch = objectGrid[x][y].getChar();
        terminal.write(ch, x, y);
        terminal.repaint();
    }


    public Displayable getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPlayerPosX() {
        return player.getPosX();
    }

    public int getPlayerPosY() {
        return player.getPosY();
    }

    public void setPlayerPosX(int x) {
        player.SetPosX(x);
    }

    public void setPlayerPosY(int y) {
        player.setPosY(y);
    }

    public char GetChar(int posX, int posY) {
        return objectGrid[posX][posY].getChar();
    }

    public boolean CheckMons(int posX, int posY) {
        return GetChar(posX, posY) == 'T' ||
                GetChar(posX, posY) == 'S' ||
                GetChar(posX, posY) == 'H';
    }

    public Displayable CheckItem(int posX, int posY) {

        return Rogue.itemStack[posX][posY].empty()?null:Rogue.itemStack[posX][posY].peek();
    }

    public void AddChar(char c,int posX, int posY) {
        addObjectToDisplay(c, posX, posY);
    }

    public void SetChar(char c, int posX, int posY) {
        addObjectNotDisplay(c, posX, posY);
    }

    public void RmChar(int posX, int posY) {
        removeObjectToDisplay(posX, posY);
    }

    public void Paint() {
        for (int i = 0; i < width; i++) {
            for (int j = topHeight; j < topHeight + gameHeight; j++) {
                writeToTerminal(i, j);
            }
        }
    }

    public void CleanRow(int posY) {
        for (int i = 0; i < width; i++){
            addObjectToDisplay(' ', i, posY);
        }
    }

    public void MoveCreature(Creature c, int posX, int posY) {
        if (c == null) {
            c = player;
        }
        AddChar(c.getType(), posX, posY);
        c.SetPosX(posX);
        c.setPosY(posY);
        if (c.getType() == player.getType()) {
            player.addMoves();
            if (player.getHallucinateMoves() >= 0) {
                Rogue.HallucinateProcess();
                player.setHallucinateMoves(player.getHallucinateMoves()-1);
            }
            Rogue.printPlayerInfo();
        }
    }

    public void AddRow(int posY, String msg) {
        for (int i = 0; i < msg.length(); i++){
            this.AddChar(msg.charAt(i), i, posY);
        }
    }

    public boolean Empty(int posX, int posY) {
        return objectGrid[posX][posY].getChar() == ' ';
    }

    public void PopItem(int posX, int posY) {
        Displayable item = CheckItem(posX, posY);
        if (item != null) {
            AddChar(item.getType(), posX, posY);
        } else {
            RmChar(posX, posY);
        }

    }
}

