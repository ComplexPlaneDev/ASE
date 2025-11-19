package com.github.ASE.Reflection.Array.Apis;

import com.github.ASE.Reflection.Array.Item;

public class BlogInfo implements Item {
    public String name;
    public String url;

    public BlogInfo(String n, String u) {
        this.name = n;
        this.url = u;
    }
}
