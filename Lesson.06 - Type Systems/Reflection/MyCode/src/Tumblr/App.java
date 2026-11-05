package Tumblr;

import java.util.List;

import Tumblr.Array.Apis.Blog;
import Tumblr.Array.Apis.BlogInfo;
import Tumblr.Simple.Apis.User;
import Tumblr.Simple.Apis.UserInfo;

public class App {

    public static void main(String[] args) {
        Client client = new Client("http://myblog.com");

        client.call(User.class, new Tumblr.Simple.Completion<UserInfo>() {

            @Override
            public void onFailure(Exception e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onSuccess(UserInfo info) {
                System.out.println("User: " + info.name + ", " + info.avatar);
            }
        });

        client.call(Blog.class, new Tumblr.Array.Completion<BlogInfo>() {

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
