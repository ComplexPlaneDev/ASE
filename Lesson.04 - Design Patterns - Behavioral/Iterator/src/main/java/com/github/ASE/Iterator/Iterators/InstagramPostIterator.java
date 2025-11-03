package com.github.ASE.Iterator.Iterators;

import java.util.Iterator;

import com.github.ASE.Iterator.Post;

public class InstagramPostIterator implements com.github.ASE.Iterator.Iterator {
    private final Iterator<Post> postIterator;

    public InstagramPostIterator(Iterator<Post> postIterator) {
        this.postIterator = postIterator;
    }

    @Override
    public boolean hasNext() {
        return postIterator.hasNext();
    }

    @Override
    public Post next() {
        return postIterator.next();
    }
}
