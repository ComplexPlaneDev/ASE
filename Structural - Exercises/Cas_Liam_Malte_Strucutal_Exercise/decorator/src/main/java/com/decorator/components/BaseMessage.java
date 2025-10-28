package com.decorator.components;

public class BaseMessage implements Message {
  @Override
  public void process(String message) {
    System.out.println(message);
  }

  @Override
  public Double getSize() {
    return 5.2;
  }
}
