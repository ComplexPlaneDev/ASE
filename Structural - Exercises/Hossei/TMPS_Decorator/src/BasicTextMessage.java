/* This is a concrete class implementing the Message nterface
it provides the 2 methods */

public class BasicTextMessage implements Message{
    private final String content;

    public BasicTextMessage(String content){
        this.content = content;
    }
    @Override
    public String process(){
        return content;
    }

    @Override
    public int getSize(){
        return process().length();
    }
}
