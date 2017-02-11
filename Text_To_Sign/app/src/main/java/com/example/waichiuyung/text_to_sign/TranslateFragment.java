package com.example.waichiuyung.text_to_sign;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.waichiuyung.text_to_sign.Factories.RestCall;
import com.example.waichiuyung.text_to_sign.Utils.MyUtils;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.Charset;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import static com.example.waichiuyung.text_to_sign.Config.Config.TRANSLATE_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class TranslateFragment extends Fragment {

    private View myView;
    private String translateInput;
    private Button translateButton;
    private EditText translateEdit;
    private VideoView videoView;
    private TextView resultView;
    private View progressOverlay;

    public TranslateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myView = inflater.inflate(R.layout.fragment_translate, container, false);
        videoView = (VideoView) myView.findViewById(R.id.videoView);
        translateEdit = (EditText) myView.findViewById(R.id.translationText);
        resultView = (TextView) myView.findViewById(R.id.translatedText);
        translateButton = (Button) myView.findViewById(R.id.translationButton);
        progressOverlay = myView.findViewById(R.id.progress_overlay);

        translateEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                Log.i("action: ", Integer.toString(actionId));
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    translateInput = translateEdit.getText().toString();
                    resultView.setText(translateInput);
                    Log.i("text: ", translateInput);
                    handled = true;
                    InputMethodManager inputManager =
                            (InputMethodManager) getContext().
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(
                            getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return handled;
            }
        });

        translateButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonParams = new JSONObject();
                StringEntity entity = null;
                try {
                    jsonParams.put("sentence", translateInput);
                    jsonParams.put("mode", "deaf");
                    entity = new StringEntity(jsonParams.toString(), Charset.forName("UTF-8"));
                    Log.i("json: ", jsonParams.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MyUtils.animateView(progressOverlay, View.VISIBLE, 0.5f, 200);
                RestCall.asyncPost(getContext(), TRANSLATE_URL, entity, "application/json", new FileAsyncHttpResponseHandler(getContext()) {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                        Log.d("status: ", Integer.toString(statusCode));
                        Toast.makeText(getContext(),"Please retry later.", Toast.LENGTH_LONG).show();
                        MyUtils.animateView(progressOverlay, View.GONE, 0, 200);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, File file) {
                        MediaController mediaController = new MediaController(getContext());
                        mediaController.setAnchorView(videoView);
                        videoView.setMediaController(mediaController);
                        videoView.setVideoPath(file.getAbsolutePath());
                        MyUtils.animateView(progressOverlay, View.GONE, 0, 200);
                        videoView.start();
                    }
                });
            }
        });

        return myView;

    }

}
