package Tumblr.Simple.Apis;

import Tumblr.Simple.Item;

public class UserInfo implements Item {
    public String name;
    public String avatar;

    public UserInfo(String n, String a) {
        this.name = a;
        this.avatar = a;
    }
}
