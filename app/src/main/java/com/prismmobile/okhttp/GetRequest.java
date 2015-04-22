package com.prismmobile.okhttp;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by benjunya on 4/22/15.
 * Async Task
 */
public class GetRequest extends AsyncTask<String, Void, Void> {

    private final String TAG = GetRequest.class.getSimpleName();
    public OnTaskCompleted onTaskCompleted;
    boolean connectionStatus;

    public GetRequest(OnTaskCompleted listener) {
        this.onTaskCompleted = listener;
    }


    @Override
    protected Void doInBackground(String... params) {
        OkHttpClient okHttpClient = new OkHttpClient();
        connectionStatus = true;
        Log.i(TAG, "DO IN BACKGROUND IS FIRING");
        Log.i(TAG, params[0]);

        return null;

    }

    @Override
    protected void onPostExecute(Void params) {
        super.onPostExecute(params);
        onTaskCompleted.onTaskCompleted();

    }
}
