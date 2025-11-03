package com.github.ASE.Iterator.Collections;

import java.util.ArrayList;
import java.util.List;

import com.github.ASE.Iterator.Collection;
import com.github.ASE.Iterator.Iterator;
import com.github.ASE.Iterator.Post;
import com.github.ASE.Iterator.Iterators.TwitterPostIterator;

public class TwitterFeed implements Collection {
    private final List<Post> tweets = new ArrayList<>();

    public void addPost(Post post) {
        tweets.add(post);
    }

    @Override
    public Iterator createIterator() {
        return new TwitterPostIterator(tweets);
    }
}
