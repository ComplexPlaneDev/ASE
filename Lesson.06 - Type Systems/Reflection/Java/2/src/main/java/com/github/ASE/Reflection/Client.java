package com.github.ASE.Reflection;

import java.lang.reflect.InvocationTargetException;

public class Client {
    private String mainUrl;

    public Client(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public <T> void call(final Class<? extends com.github.ASE.Reflection.Simple.Api<T>> clazz,
            final com.github.ASE.Reflection.Simple.Completion<T> onCompletion) {

        Class<?>[] cArg = new Class<?>[] {};

        try {
            com.github.ASE.Reflection.Simple.Api<T> myApi = clazz.getDeclaredConstructor(cArg).newInstance();
            System.out.println("Calling " + this.mainUrl + myApi.getPath() + " ...");

            myApi.call(onCompletion);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public <T> void call(final Class<? extends com.github.ASE.Reflection.Array.Api<T>> clazz,
            final com.github.ASE.Reflection.Array.Completion<T> onCompletion) {

        Class<?>[] cArg = new Class<?>[] {};

        try {
            com.github.ASE.Reflection.Array.Api<T> myApi = clazz.getDeclaredConstructor(cArg).newInstance();
            System.out.println("Calling " + this.mainUrl + myApi.getPath() + " ...");

            myApi.call(onCompletion);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
