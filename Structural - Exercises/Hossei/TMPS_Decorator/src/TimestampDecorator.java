/* this is the concrete Decorator which extends the Message Decorator
it also uses the methods to add the decorator content */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampDecorator extends MessageDecorator {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TimestampDecorator(Message inner){
        super(inner);
    }
    @Override
    public String process(){
        String currentTime = LocalDateTime.now().format(FORMATTER);
        return "[" + currentTime + "]" + inner.process();
        }
}
