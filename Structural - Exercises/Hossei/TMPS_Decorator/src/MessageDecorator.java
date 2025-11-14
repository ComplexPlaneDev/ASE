/* This is the Decorator, its an abstract class implementing the Message interface
It maintains a reference to the decorated Message object
The methods are implemented to delegate to the decorated message */

public abstract class MessageDecorator implements Message {
    protected final Message inner;

    protected MessageDecorator(Message inner){
        this.inner = inner;
    }

    @Override
    public int getSize(){
        return process().length();
    }

    
}
