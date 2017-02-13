package com.example.waichiuyung.text_to_sign;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TranslateFragment extends Fragment {

    public TranslateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myView = inflater.inflate(R.layout.fragment_translate, container, false);


        try {
            String link="https://sign-lang-backend.herokuapp.com/translate";
            VideoView videoView = (VideoView) myView.findViewById(R.id.videoView);
            MediaController mediaController = new MediaController(getContext());
            mediaController.setAnchorView(videoView);
            Uri video = Uri.parse(link);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(video);
            videoView.start();
        } catch (Exception e) {
            // TODO: handle exception
            // Toast.makeText(this, "Error connecting", Toast.LENGTH_SHORT).show();
        }



        return myView;

    }


}
