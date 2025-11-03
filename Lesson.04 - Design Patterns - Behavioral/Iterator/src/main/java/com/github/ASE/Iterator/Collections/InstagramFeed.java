package com.github.ASE.Iterator.Collections;

import java.util.Comparator;
import java.util.TreeSet;

import com.github.ASE.Iterator.Collection;
import com.github.ASE.Iterator.Iterator;
import com.github.ASE.Iterator.Post;
import com.github.ASE.Iterator.Iterators.InstagramPostIterator;

public class InstagramFeed implements Collection {
    private final TreeSet<Post> posts = new TreeSet<>(
            Comparator.comparing(Post::getAuthor).thenComparingLong(Post::getTimestamp));

    public void addPost(Post post) {
        posts.add(post);
    }

    @Override
    public Iterator createIterator() {
        return new InstagramPostIterator(posts.descendingSet().iterator());
    }
}
