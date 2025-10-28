package com.decorator.decorators;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampMessage extends BaseMessage {
  public TimestampMessage(com.decorator.components.Message decoratedMessage) {
    super(decoratedMessage);
  }

  @Override
  public void process(String message) {
    String date = new SimpleDateFormat("MM/dd/yy HH:mm:ss").format(new Date());
    super.process(date + ": " + message);
  }
}
