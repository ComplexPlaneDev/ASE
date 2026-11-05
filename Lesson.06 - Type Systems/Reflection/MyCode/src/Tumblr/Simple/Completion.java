package Tumblr.Simple;

import Tumblr.BaseCompletion;

public interface Completion<T extends Item> extends BaseCompletion {
    void onSuccess(T result);
}
