package src;

public class Action {
    int intValue;
    char charValue;
    String message;
      
    public void setMessage(String msg) 
   {
       message = msg;
    System.out.println("Action:setMessage()\n" +
            "   msg: " + msg);
   }

   public void setIntValue(int v)
   {
       intValue = v;
    System.out.println("Action:setIntValue()\n" +
            "   IntValue: " + v);
   }

   public void setCharValue(char c)
   {
       charValue = c;
    System.out.println("Action:setCharValue()\n" +
            "   CharValue: " + c);
   }
}
