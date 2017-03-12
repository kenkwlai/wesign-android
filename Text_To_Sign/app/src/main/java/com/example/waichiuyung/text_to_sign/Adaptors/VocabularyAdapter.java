package com.example.waichiuyung.text_to_sign.Adaptors;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.example.waichiuyung.text_to_sign.R;
import com.example.waichiuyung.text_to_sign.Vocabulary;

import java.util.List;

/**
 * Created by Ken Lai on 17/2/2017.
 */

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public ViewHolder(View itemView){
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.translatedSegment);
        }
    }

    private List<Vocabulary> segments;

    public VocabularyAdapter(List<Vocabulary> lists) {
        segments = lists;
    }

    @Override
    public int getItemCount() {
        return segments.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        View segmentView = LayoutInflater.from(context).inflate(R.layout.listitem_translate, viewGroup, false);

        return new ViewHolder(segmentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Vocabulary vocabulary = segments.get(position);
        TextView nameTextView = viewHolder.nameTextView;
        nameTextView.setText(vocabulary.getWord());

        long duration = 0;
        for (int i = 0; i < position; i++) {
            duration += (long) (segments.get(i).getDuration() * 2000);
        }
        duration += (long) (vocabulary.getDuration() * 2000);

        final ObjectAnimator animator = ObjectAnimator.ofInt(nameTextView, "textColor", Color.RED);
        animator.setDuration(duration);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setInterpolator(new DecelerateInterpolator(2));
        animator.start();
    }
}
