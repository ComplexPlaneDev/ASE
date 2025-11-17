package com.github.ASE.Reflection.Simple.Apis;

import com.github.ASE.Reflection.Simple.Api;
import com.github.ASE.Reflection.Simple.Completion;

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
