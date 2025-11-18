package com.github.ASE.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App {

    public static void main(String[] args) {
        Class<?> clazz = Person.class; // Get Class object

        System.out.println("Class Name: " + clazz.getName());

        // List constructors
        for (Constructor ctr : clazz.getConstructors()) {
            System.out.println("Constructor: " + ctr.getName() + ", " + ctr.getParameterCount());
        }

        // List fields
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println("Field: " + field.getName() + " | Type: " + field.getType());
        }

        // List methods
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println("Method: " + method.getName());
        }

        try {
            Constructor<?> constructor = Person.class.getConstructor(String.class, int.class);
            Person person = (Person) constructor.newInstance("Alice", 30);

            Method method = person.getClass().getMethod("sayHello");
            method.invoke(person); // Calls sayHello()

            Field nameField = person.getClass().getDeclaredField("name");
            nameField.setAccessible(true); // Bypass private modifier
            nameField.set(person, "Bob");

            person.sayHello();

            Class<?> dynamicClass = Class.forName("com.github.ASE.Reflection.Person");
            Person obj = (Person) dynamicClass.getConstructor(String.class, int.class).newInstance("Eve", 25);
            obj.sayHello();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        User user = new User(1, "alice");

        String sql = ORM.generateInsertSQL(user);
        System.out.println(sql);
    }
}
