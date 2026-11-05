package Tumblr.Array;

import Tumblr.BaseApi;

public interface Api<T extends Item> extends BaseApi {
    void call(Completion<T> onCompletion);
}
