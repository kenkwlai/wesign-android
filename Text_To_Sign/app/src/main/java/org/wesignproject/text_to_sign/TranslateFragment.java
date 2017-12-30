package org.wesignproject.text_to_sign;


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
import android.widget.HorizontalScrollView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import org.wesignproject.text_to_sign.Factories.RestCall;
import org.wesignproject.text_to_sign.Views.SignVideoView;
import org.wesignproject.text_to_sign.R;
import org.wesignproject.text_to_sign.Utils.MyUtils;
import org.wesignproject.text_to_sign.Views.SignTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import static org.wesignproject.text_to_sign.Config.Config.TRANSLATE_TEXT_URL;
import static org.wesignproject.text_to_sign.Config.Config.TRANSLATE_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class TranslateFragment extends Fragment {

    private View myView;
    private String translateInput;
    private Button translateButton;
    private EditText translateEdit;
    private SignVideoView videoView;
    private TextView originalText;
    private SignTextView resultContentView;
    private HorizontalScrollView translatedHonrizontal;
    private View progressOverlay;

    //private final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    private List<Vocabulary> vocabularyList;
    private MediaController mediaController;

    public TranslateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myView = inflater.inflate(R.layout.fragment_translate, container, false);
        videoView = (SignVideoView) myView.findViewById(R.id.videoView);
        translateEdit = (EditText) myView.findViewById(R.id.translationText);
        originalText = (TextView) myView.findViewById(R.id.originalText);
        resultContentView = (SignTextView) myView.findViewById(R.id.translatedContent);
        translateButton = (Button) myView.findViewById(R.id.translationButton);
        progressOverlay = myView.findViewById(R.id.progress_overlay);
        translatedHonrizontal = (HorizontalScrollView) myView.findViewById(R.id.translatedTextHorizontal);
        mediaController = new MediaController(getContext());

        translatedHonrizontal.setHorizontalScrollBarEnabled(false);
        resultContentView.setParent(translatedHonrizontal);
        videoView.setPlayPauseListener(new SignVideoView.PlayPauseListener() {
            @Override
            public void onPlay() {
                resultContentView.animateText();
                mediaController.hide();
            }

            @Override
            public void onPause() {
                resultContentView.pauseAnimation();
                mediaController.hide();
            }
        });

        translateEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                Log.i("action: ", Integer.toString(actionId));
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    translateInput = translateEdit.getText().toString();
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
                if (translateInput == null) {
                    if (translateEdit.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Please enter input." , Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        translateInput = translateEdit.getText().toString();
                    }
                }
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
                RestCall.asyncPost(getContext(), TRANSLATE_TEXT_URL, entity, "application/json", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        vocabularyList = new Gson().fromJson(response.toString(), new TypeToken<List<Vocabulary>>() {}.getType());
                        resultContentView.setList(vocabularyList);
                        Log.i("size: ", Integer.toString(vocabularyList.size()));
                    }
                });
                RestCall.asyncPost(getContext(), TRANSLATE_URL, entity, "application/json", new FileAsyncHttpResponseHandler(getContext()) {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                        Log.d("status: ", Integer.toString(statusCode));
                        Toast.makeText(getContext(), "Please try again later.", Toast.LENGTH_LONG).show();
                        MyUtils.animateView(progressOverlay, View.GONE, 0, 200);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, File file) {
                        mediaController.setAnchorView(videoView);
                        mediaController.setMediaPlayer(videoView);
                        videoView.setMediaController(mediaController);
                        videoView.setMediaController(mediaController);
                        videoView.setVideoPath(file.getAbsolutePath());
                        originalText.setText(translateInput);
                        MyUtils.animateView(progressOverlay, View.GONE, 0, 200);
                        videoView.start();
                        resultContentView.startAnimation();
                        mediaController.hide();
                    }
                });
            }
        });

        return myView;
    }
}
