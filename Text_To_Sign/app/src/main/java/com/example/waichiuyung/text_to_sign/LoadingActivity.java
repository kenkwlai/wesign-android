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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

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

public class LoadingActivity extends Activity implements Serializable{

    private LoadDataTask loadDataTask = null;
//    ArrayList<Vocabulary> vocabularies = new ArrayList<Vocabulary>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        loadDataTask = new LoadDataTask();
        loadDataTask.execute((Void) null);
    }

    public class LoadDataTask extends AsyncTask<Void,Void,ArrayList<Vocabulary>>{

        public final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

        private String url = "https://sign-lang-backend.herokuapp.com/dictionary";

        OkHttpClient client = new OkHttpClient();

        @Override
        protected ArrayList<Vocabulary> doInBackground(Void... params) {

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                ObjectMapper mapper = new ObjectMapper();

                ArrayList<Vocabulary> vocabularies = mapper.readValue(response.body().string(), new TypeReference<List<Vocabulary>>() {});

//                for (Vocabulary word: vocabularies) {
//                    Log.v("word: ", word.getWord());
//                }

                return vocabularies;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<Vocabulary> result) {
            loadDataTask = null;

            if (result != null) {
//                for (Vocabulary word: result) {
//                    Log.v("word: ", word.getWord());
//                }

                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);


                myIntent.putExtra("wordList",result);
                startActivity(myIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }else{
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
