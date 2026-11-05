package Tumblr.Array;

import java.util.List;

import Tumblr.BaseCompletion;

public interface Completion<T extends Item> extends BaseCompletion {
    void onSuccess(List<T> result);
}
