package com.example.waichiuyung.text_to_sign;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.waichiuyung.text_to_sign.Factories.RestCall;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import java.util.ArrayList;


import static com.example.waichiuyung.text_to_sign.Config.Config.CONFIG_PREFS;
import static com.example.waichiuyung.text_to_sign.Config.Config.DICTIONARY_URL;
import static com.example.waichiuyung.text_to_sign.Config.Config.VERSION_JSON;
import static com.example.waichiuyung.text_to_sign.Config.Config.VERSION_URL;
import static com.example.waichiuyung.text_to_sign.Config.Config.VOCAB_JSON;

public class LoadingActivity extends Activity{

    private LoadDataTask loadDataTask = null;
    private SharedPreferences prefs;

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
            prefs = getSharedPreferences(CONFIG_PREFS, 0);
            final String version = prefs.getString(VERSION_JSON, "");
            RestCall.syncGet(VERSION_URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    final SharedPreferences.Editor edit = prefs.edit();
                    String versionResponse = null;
                    try {
                        versionResponse = response.getString("version");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (version.compareTo(versionResponse) != 0) {
                        edit.putString(VERSION_JSON, versionResponse);
                        RestCall.syncGet(DICTIONARY_URL, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                edit.putString(VOCAB_JSON, response.toString());
                                edit.commit();
                            }
                        });
                    }
                }
            });
            return vocabularies;
        }

        @Override
        protected void onPostExecute(final ArrayList<Vocabulary> result) {
            loadDataTask = null;

            if (result != null) {
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            } else {
                Toast.makeText(getApplicationContext(),"Server Under Maintenance !!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            loadDataTask = null;
        }

        

    }
}
