package com.github.ASE.Iterator.Iterators;

import java.util.List;
import java.util.NoSuchElementException;

import com.github.ASE.Iterator.Iterator;
import com.github.ASE.Iterator.Post;

public class TwitterPostIterator implements Iterator {
    private final List<Post> posts;
    private int position = 0;

    public TwitterPostIterator(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean hasNext() {
        return position < posts.size();
    }

    @Override
    public Post next() {
        if (hasNext()) {
            return posts.get(position++);
        }

        throw new NoSuchElementException();
    }
}
