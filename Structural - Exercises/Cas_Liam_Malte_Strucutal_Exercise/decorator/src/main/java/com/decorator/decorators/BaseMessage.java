package com.decorator.decorators;

import com.decorator.components.Message;

public abstract class BaseMessage implements Message {
  protected com.decorator.components.Message decoratedMessage;

  public BaseMessage(com.decorator.components.Message decoratedMessage) {
    this.decoratedMessage = decoratedMessage;
  }

  @Override
  public void process(String message) {
    decoratedMessage.process(message);
  }

  @Override
  public Double getSize() {
    return decoratedMessage.getSize();
  }
}
