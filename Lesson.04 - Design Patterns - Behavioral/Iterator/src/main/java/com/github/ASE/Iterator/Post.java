package com.github.ASE.Iterator;

public class Post {
    private final String content;
    private final String author;
    private final long timestamp;

    public Post(String content, String author) {
        this.content = content;
        this.author = author;
        this.timestamp = System.currentTimeMillis();
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "@" + author + ": " + content;
    }
}
