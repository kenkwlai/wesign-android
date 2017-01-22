package com.example.waichiuyung.text_to_sign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by waichiuyung on 20/1/2017.
 */

public class MyAdapter extends BaseAdapter {

    private LayoutInflater myInflater;
    private List<WordList> vocab_list;

    public MyAdapter(Context context, List<WordList> vocab_list){
        myInflater = LayoutInflater.from(context);
        this.vocab_list= vocab_list;
    }

    private class ViewHolder {
        TextView wordText;
        ImageView bookMark;
        public ViewHolder(TextView wordText, ImageView bookmark){
            this.wordText = wordText;
            this.bookMark = bookmark;
        }
    }

    @Override
    public int getCount() {
        return vocab_list.size();
    }

    @Override
    public Object getItem(int position) {
        return vocab_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return vocab_list.indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = myInflater.inflate(R.layout.listitem_dictionary, null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.vocab),
                    (ImageView) convertView.findViewById(R.id.setBookmark)
            );
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.bookMark.setColorFilter(holder.bookMark.getResources().getColor(R.color.systemBar));


        WordList vocab = (WordList) getItem(position);
        holder.wordText.setText(vocab.getWord());
        return convertView;

    }

}
