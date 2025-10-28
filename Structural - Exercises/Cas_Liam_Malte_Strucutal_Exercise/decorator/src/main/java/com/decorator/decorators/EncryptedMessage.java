package com.decorator.decorators;

public class EncryptedMessage extends BaseMessage {
  public EncryptedMessage(com.decorator.components.Message decoratedMessage) {
    super(decoratedMessage);
  }

  @Override
  public void process(String message) {
    super.process("H3A09F20:" + message);
  }

  @Override
  public Double getSize() {
    return super.getSize();
  }
}
