package com.decorator;

import com.decorator.components.BaseMessage;
import com.decorator.components.Message;
import com.decorator.decorators.CompressedMessage;
import com.decorator.decorators.EncryptedMessage;
import com.decorator.decorators.TimestampMessage;

public class Main {
    public static void main(String[] args) {
        Message basic = new BaseMessage();
        basic.process("Hello there");
        System.out.println("Message size: " + basic.getSize() + "KB");

        Message encrypted = new EncryptedMessage(basic);
        encrypted.process("Hello there");
        System.out.println("Message size: " + encrypted.getSize() + "KB");

        Message compressed = new CompressedMessage(encrypted);
        compressed.process("Hello there");
        System.out.println("Message size: " + compressed.getSize() + "KB");

        Message timestamp = new TimestampMessage(compressed);
        timestamp.process("Hello there");
        System.out.println("Message size: " + timestamp.getSize() + "KB");
    }
}