package src;

public class Displayable {

    private char type;
    private int posX;
    private int posY;
    private int width;
    private int height;

    private boolean visible;
    private int IntValue;
    
    public Displayable()
    {
        System.out.println("Displayable()");
    }

    public void setInvisible()
    {
        visible = false;
        System.out.println("Displayable:Invisible()");
    }

    public void setVisible()
    {
        visible = true;
        System.out.println("Displayable:setVisible()\n");

    }

    public void setMaxHit(int maxHit) {}


    public void setHpMoves(int hpMoves) {}

    public void setHp(int hp) {}


    public void setType(char t)
    {
        this.type = t;
        System.out.println("Displayable:setType()\n" +
                "   Type: " + t);

    }

    public void setIntValue(int v)
    {
        this.IntValue = v;
        System.out.println("Displayable:setIntValue\n" +
                "   IntValue: " + v);
    }

    public void SetPosX(int x)
    {
        this.posX = x;
        System.out.println("Displayable:SetPosX\n" +
                "   PosX: " + x);
    }

    public void setPosY(int y)
    {
        this.posY = y;
        System.out.println("Displayable:setPosY\n" +
                   "   PosY: " + y);
    }

    public void SetWidth(int x)
    {
        this.width = x;
        System.out.println("Displayable:setWidth\n" +
                "   Width: " + x);
    }

    public void setHeight(int y)
    {
        this.height = y;
        System.out.println("Displayable:setHeight\n" +
                "   Height: " + y);

    }


    public char getType() {
        return type;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return width;
    }



    public int getHeight() {
        return height;
    }

    public int width() {
        return getPosX() + getWidth();
    }

    public int height() {
        return getPosY() + getHeight();
    }

    public int getIntValue() {
        return IntValue;
    }
}
