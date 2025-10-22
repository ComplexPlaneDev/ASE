/* this is the concrete Decorator which extends the Message Decorator
it also uses the methods to add the decorator content */

public class EncryptionDecorator extends MessageDecorator {
    public EncryptionDecorator(Message inner){
        super(inner);
    }
    @Override
    public String process(){
        return "3nc5yp73d:" + inner.process();
    }
    
}
