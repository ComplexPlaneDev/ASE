package com.github.ASE.Iterator.Iterators;

import java.util.Iterator;

import com.github.ASE.Iterator.Post;

public class FacebookPostIterator implements com.github.ASE.Iterator.Iterator {
    private final Iterator<Post> postIterator;

    public FacebookPostIterator(Iterator<Post> postIterator) {
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
