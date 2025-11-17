package com.github.ASE.Reflection.Array;

import java.util.List;

import com.github.ASE.Reflection.BaseCompletion;

public interface Completion<T> extends BaseCompletion {
    void onSuccess(List<T> result);
}
