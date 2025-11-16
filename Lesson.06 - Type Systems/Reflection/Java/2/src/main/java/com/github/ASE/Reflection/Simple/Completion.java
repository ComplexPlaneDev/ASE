package com.github.ASE.Reflection.Simple;

import com.github.ASE.Reflection.BaseCompletion;

public interface Completion<T> extends BaseCompletion {
    void onSuccess(T result);
}
