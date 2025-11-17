package com.github.ASE.Reflection;

public class Person {
    private String name;
    public int age;

    public Person(String name) {
        this.name = name;
        this.age = 10;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void sayHello() {
        System.out.println("Hello, my name is " + name);
    }
}
