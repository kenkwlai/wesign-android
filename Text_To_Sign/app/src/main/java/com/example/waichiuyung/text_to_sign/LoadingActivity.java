package com.example.waichiuyung.text_to_sign;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waichiuyung.text_to_sign.Factories.RestCall;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import javax.net.ssl.HttpsURLConnection;

import static com.example.waichiuyung.text_to_sign.Config.Config.DICTIONARY_URL;

public class LoadingActivity extends Activity implements Serializable{

    private LoadDataTask loadDataTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        loadDataTask = new LoadDataTask();
        loadDataTask.execute((Void) null);
    }

    public class LoadDataTask extends AsyncTask<Void,Void,ArrayList<Vocabulary>>{
        @Override
        protected ArrayList<Vocabulary> doInBackground(Void... params) {
            final ArrayList<Vocabulary> vocabularies = new ArrayList<>();
            RestCall.syncGet(DICTIONARY_URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    List<Vocabulary> result = new Gson().fromJson(response.toString(), new TypeToken<List<Vocabulary>>() {}.getType());
                    vocabularies.addAll(result);
                }
            });
            return vocabularies;
        }

        @Override
        protected void onPostExecute(final ArrayList<Vocabulary> result) {
            loadDataTask = null;

            if (result != null) {
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                myIntent.putExtra("wordList",result);
                startActivity(myIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            } else {
                Toast.makeText(getApplicationContext(),"Server Under Maintenance !!", Toast.LENGTH_LONG).show();

                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        }

        @Override
        protected void onCancelled() {
            loadDataTask = null;
        }

    }
}
