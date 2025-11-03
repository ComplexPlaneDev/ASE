package com.github.ASE.Iterator;

import com.github.ASE.Iterator.Collections.FacebookFeed;
import com.github.ASE.Iterator.Collections.InstagramFeed;
import com.github.ASE.Iterator.Collections.TwitterFeed;

public class App {

    public static void main(String[] args) {
        // Create feeds from different platforms
        Collection twitterFeed = new TwitterFeed();
        ((TwitterFeed) twitterFeed).addPost(new Post("Just had coffee!", "Alice"));
        ((TwitterFeed) twitterFeed).addPost(new Post("Hello world!", "Bob"));

        Collection instagramFeed = new InstagramFeed();
        ((InstagramFeed) instagramFeed).addPost(new Post("Sunny day!", "Charlie"));
        ((InstagramFeed) instagramFeed).addPost(new Post("New outfit", "Alice"));

        Collection facebookFeed = new FacebookFeed();
        ((FacebookFeed) facebookFeed).addPost("post1", new Post("Family reunion!", "Bob"));
        ((FacebookFeed) facebookFeed).addPost("post2", new Post("Vacation photos", "Charlie"));

        // Unified traversal
        System.out.println("Twitter Feed:");
        printPosts(twitterFeed.createIterator());

        System.out.println("\nInstagram Feed:");
        printPosts(instagramFeed.createIterator());

        System.out.println("\nFacebook Feed:");
        printPosts(facebookFeed.createIterator());
    }

    private static void printPosts(Iterator iterator) {
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
