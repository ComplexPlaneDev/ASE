package com.github.ASE.Reflection.Simple;

import com.github.ASE.Reflection.BaseApi;

public interface Api<T> extends BaseApi {
    void call(Completion<T> onCompletion);
}
