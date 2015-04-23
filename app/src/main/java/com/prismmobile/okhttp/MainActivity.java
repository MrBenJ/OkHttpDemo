package com.prismmobile.okhttp;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends ActionBarActivity implements RequestCallback{
    private final OkHttpClient client = new OkHttpClient();
    private final String TAG = MainActivity.class.getSimpleName();
    private TextView statusText;
    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();
    String endGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        statusText = (TextView) findViewById(R.id.statusText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    RunHttpConnection("http://www.steamnaut.com/data/data.json", "GET", MainActivity.this);
                }
                catch(Exception exception) {
                    exception.printStackTrace();
                }



            }
        });
    }

    @Override
    public void OnSuccess(String data) {
        statusText.setText(data);
    }

    @Override
    public void OnFailure() {

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void RunHttpConnection(String url, String requestType, final RequestCallback callback) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();


        if(requestType.equals("GET")) {
            client.newCall(request).enqueue(new Callback() {
                Handler mainThread = new Handler(getBaseContext().getMainLooper());
                @Override
                public void onFailure(Request request, IOException throwable) {
                    throwable.printStackTrace();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }

                    else {
                        Log.i(TAG, "GET request successful!");
                        JsonArray data = parser.parse(response.body().string()).getAsJsonArray();


                        if(data.isJsonArray()) {

                            for(int i = 0; i < data.size(); i++) {
                                JsonObject person = data.get(i).getAsJsonObject();
                                String name = person.get("name").getAsString();
                                endGame += name;
                                Log.i(TAG, "Loop iteration: " + i);

                            }

                        }
                        mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.OnSuccess(endGame);
                            }
                        });

                        Log.i(TAG, "We good!");



                    }
                }


            });
        }
        else if(requestType.equals("POST")) {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException throwable) {
                    throwable.printStackTrace();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }

                    else {
                        Log.i(TAG, "POST request successful!");
                    }
                }


            });
        }
    }


}

