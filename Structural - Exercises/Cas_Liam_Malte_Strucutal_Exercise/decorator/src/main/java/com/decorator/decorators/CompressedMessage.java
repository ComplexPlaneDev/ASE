package com.decorator.decorators;

import com.decorator.components.Message;

public class CompressedMessage extends BaseMessage {
  public CompressedMessage(Message decoratedMessage) {
    super(decoratedMessage);
  }

  @Override
  public void process(String message) {
    super.process("compressed: " + message);
  }

  @Override
  public Double getSize() {
    return super.getSize() - 2.7;
  }
}
