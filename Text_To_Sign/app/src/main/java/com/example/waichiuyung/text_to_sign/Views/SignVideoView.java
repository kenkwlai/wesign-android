package com.example.waichiuyung.text_to_sign.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by Ken Lai on 12/3/2017.
 */

public class SignVideoView extends VideoView {

    private PlayPauseListener mListener;

    public SignVideoView(Context context) {
        super(context);
    }

    public SignVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SignVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setPlayPauseListener(PlayPauseListener listener) {
        mListener = listener;
    }

    @Override
    public void pause() {
        super.pause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    public void start() {
        super.start();
        if (mListener != null) {
            mListener.onPlay();
        }
    }

    public interface PlayPauseListener {
        void onPlay();
        void onPause();
    }
}
