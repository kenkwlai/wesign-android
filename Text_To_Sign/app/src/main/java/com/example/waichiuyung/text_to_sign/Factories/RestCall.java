package com.example.waichiuyung.text_to_sign.Factories;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Ken Lai on 10/2/2017.
 */

public class RestCall {
    private static AsyncHttpClient asyncClient = new AsyncHttpClient();
    private static SyncHttpClient syncClient = new SyncHttpClient();

    public static void syncGet(String url, AsyncHttpResponseHandler responseHandler) {
        syncClient.setTimeout(30000);
        syncClient.get(url, responseHandler);
    }

    public static void asyncGet(String url, AsyncHttpResponseHandler responseHandler) {
        asyncClient.setTimeout(30000);
        asyncClient.get(url, responseHandler);
    }

    public static void asyncPost(Context context, String url, StringEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        asyncClient.setTimeout(30000);
        asyncClient.post(context, url, entity, contentType, responseHandler);
    }
}
