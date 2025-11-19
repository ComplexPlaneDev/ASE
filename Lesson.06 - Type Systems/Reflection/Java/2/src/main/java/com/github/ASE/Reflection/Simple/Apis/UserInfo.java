package com.github.ASE.Reflection.Simple.Apis;

import com.github.ASE.Reflection.Simple.Item;

public class UserInfo implements Item {
    public String name;
    public String avatar;

    public UserInfo(String n, String a) {
        this.name = a;
        this.avatar = a;
    }
}
