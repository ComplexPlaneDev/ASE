package Tumblr;

import java.lang.reflect.InvocationTargetException;

public class Client {
    private String mainUrl;

    public Client(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public <T extends Tumblr.Simple.Item> void call(final Class<? extends Tumblr.Simple.Api<T>> clazz,
                                      final Tumblr.Simple.Completion<T> onCompletion) {

        Class<?>[] cArg = new Class<?>[] {};

        try {
            Tumblr.Simple.Api<T> myApi = clazz.getDeclaredConstructor(cArg).newInstance();
            System.out.println("Calling " + this.mainUrl + myApi.getPath() + " ...");

            myApi.call(onCompletion);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public <T extends Tumblr.Array.Item> void call(final Class<? extends Tumblr.Array.Api<T>> clazz,
            final Tumblr.Array.Completion<T> onCompletion) {

        Class<?>[] cArg = new Class<?>[] {};

        try {
            Tumblr.Array.Api<T> myApi = clazz.getDeclaredConstructor(cArg).newInstance();
            System.out.println("Calling " + this.mainUrl + myApi.getPath() + " ...");

            myApi.call(onCompletion);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
