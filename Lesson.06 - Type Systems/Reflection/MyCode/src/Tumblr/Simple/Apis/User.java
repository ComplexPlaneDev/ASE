package Tumblr.Simple.Apis;

import Tumblr.Simple.Api;
import Tumblr.Simple.Completion;

public class User implements Api<UserInfo> {

    @Override
    public void call(Completion<UserInfo> onCompletion) {
        onCompletion.onSuccess(new UserInfo("MyName", "MyAvatar"));
    }

    @Override
    public String getPath() {
        return "/userInfo";
    }
}
