package com.github.ASE.Reflection.Array.Apis;

import java.util.ArrayList;

import com.github.ASE.Reflection.Array.Api;
import com.github.ASE.Reflection.Array.Completion;

public class Blog implements Api<BlogInfo> {

    @Override
    public void call(Completion<BlogInfo> onCompletion) {
        onCompletion.onSuccess(new ArrayList<BlogInfo>() {
            {
                add(new BlogInfo("My Blog1", "https://blog1.myblog.com"));
                add(new BlogInfo("My Blog2", "https://blog2.myblog.com"));
                add(new BlogInfo("My Blog3", "https://blog3.myblog.com"));
            }
        });
    }

    @Override
    public String getPath() {
        return "/getBlogsInfo";
    }
}
