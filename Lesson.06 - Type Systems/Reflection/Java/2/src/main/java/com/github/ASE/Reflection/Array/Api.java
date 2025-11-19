package com.github.ASE.Reflection.Array;

import com.github.ASE.Reflection.BaseApi;

public interface Api<T extends Item> extends BaseApi {
    void call(Completion<T> onCompletion);
}
