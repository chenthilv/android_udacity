package com.hollywood.movies.task;

/**
 * Created by chenthil on 4/8/17.
 */

public interface AsyncTaskCompleteListener<T> {

    public void onTaskComplete(T result);
}
