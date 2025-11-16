package com.github.ASE.Reflection;

import java.util.List;

import com.github.ASE.Reflection.Array.Apis.Blog;
import com.github.ASE.Reflection.Array.Apis.BlogInfo;
import com.github.ASE.Reflection.Simple.Apis.User;
import com.github.ASE.Reflection.Simple.Apis.UserInfo;

public class App {

    public static void main(String[] args) {
        Client client = new Client("http://myblog.com");

        client.call(User.class, new com.github.ASE.Reflection.Simple.Completion<UserInfo>() {

            @Override
            public void onFailure(Exception e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onSuccess(UserInfo info) {
                System.out.println("User: " + info.name + ", " + info.avatar);
            }
        });

        client.call(Blog.class, new com.github.ASE.Reflection.Array.Completion<BlogInfo>() {

            @Override
            public void onFailure(Exception e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onSuccess(List<BlogInfo> result) {
                for (final BlogInfo blog : result) {
                    System.out.println("Blog: " + blog.name + ", " + blog.url);
                }
            }
        });
    }
}
