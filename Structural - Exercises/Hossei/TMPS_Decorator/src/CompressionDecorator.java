/* this is the concrete Decorator which extends the Message Decorator
it also uses the methods to add the decorator content */

public class CompressionDecorator extends MessageDecorator {
    public CompressionDecorator(Message inner){
        super(inner);
    }
    @Override
    public String process(){
        return "c0mpr3553d:" + inner.process();
    }
}
