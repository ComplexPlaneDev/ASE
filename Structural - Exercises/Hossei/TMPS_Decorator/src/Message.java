/* This is the interface Message representing the component
It declares 2 methods process() and getSize() which must be implemented by the concrete components and decorators */

public interface Message {
    String process();
    int getSize();
    
}
