package com.example.waichiuyung.text_to_sign.Views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.waichiuyung.text_to_sign.Vocabulary;

import java.util.List;

/**
 * Created by Ken Lai on 13/3/2017.
 */

public class SignTextView extends TextView {

    private CharSequence mText;
    private List<Vocabulary> mVocabList;
    private int mIndex = 0;
    private SignTextView mView;
    private HorizontalScrollView mHorizontal;

    public SignTextView(Context context) {
        super(context);
        mView = this;
    }

    public SignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mView = this;
    }

    public SignTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mView = this;
    }

    private Handler mHandler = new Handler();

    private Runnable highlightText = new Runnable() {
        @Override
        public void run() {
            long duration = (long) (mVocabList.get(mIndex).getDuration() * 1000);
            mView.append(mVocabList.get(mIndex).getWord() + " ");
            mView.post(new Runnable() {
                @Override
                public void run() {
                    mHorizontal.fullScroll(View.FOCUS_RIGHT);
                }
            });
            if(mIndex < mVocabList.size() - 1) {
                mIndex++;
                mHandler.postDelayed(highlightText, duration);
            }
        }
    };

    public void setParent(HorizontalScrollView horizontalScrollView) {
        mHorizontal = horizontalScrollView;
    }

    public void setList(List<Vocabulary> lists) {
        mVocabList = lists;
        for (Vocabulary v: mVocabList) {
            mText = mText + v.getWord();
        }
    }

    public void startAnimation() {
        setText("");
        mIndex = 0;
        animateText();
    }

    public void animateText() {
        if (mIndex >= mVocabList.size() - 1) {
            setText("");
            mIndex = 0;
        }
        mHandler.removeCallbacks(highlightText);
        mHandler.postDelayed(highlightText, 200);
    }

    public void pauseAnimation() {
        mHandler.removeCallbacks(highlightText);
    }

    @Override
    public CharSequence getText() {
        return mText;
    }
}
