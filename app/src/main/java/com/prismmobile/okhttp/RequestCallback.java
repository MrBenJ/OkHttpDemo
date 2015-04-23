package com.prismmobile.okhttp;

/**
 * Created by benjunya on 4/22/15.
 */
public interface RequestCallback {

    public void OnSuccess(String data);
    public void OnFailure();
}
