package com.github.ASE.Iterator.Collections;

import java.util.HashMap;
import java.util.Map;

import com.github.ASE.Iterator.Collection;
import com.github.ASE.Iterator.Iterator;
import com.github.ASE.Iterator.Post;
import com.github.ASE.Iterator.Iterators.FacebookPostIterator;

public class FacebookFeed implements Collection {
    private final Map<String, Post> wall = new HashMap<>();

    public void addPost(String id, Post post) {
        wall.put(id, post);
    }

    @Override
    public Iterator createIterator() {
        return new FacebookPostIterator(wall.values().iterator());
    }
}
