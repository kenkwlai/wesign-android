package com.example.waichiuyung.text_to_sign;


import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.waichiuyung.text_to_sign.Views.SignVideoView;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment {


    public BookmarkFragment() {
        // Required empty public constructor
    }

    ArrayList<Vocabulary> vocabularies;
    ArrayList<String> bookmark_list = new ArrayList<String>();
    private SharedPreferences bmPrefs;
    public static final String BM_PREFS = "BookMarkFile";
    private ListView listView;
    ArrayList<WordList> vocab_list = new ArrayList<WordList>();
    ArrayAdapter<WordList> VocabArrayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_bookmark, container, false);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            vocabularies = (ArrayList<Vocabulary>) bundle.getSerializable("vocabularies");
        }

        bmPrefs = getActivity().getSharedPreferences(BM_PREFS,0);

        String[] bm = bmPrefs.getString("Bookmark", "").split("\\|");
        for(String score : bm){
            bookmark_list.add(score);
        }

        for (Vocabulary word : vocabularies) {
            if (bookmark_list!= null){
                if (bookmark_list.contains(word.getWord())){
                    vocab_list.add(new WordList(word.getWord(), word.getPath(), word.getPrefix(), word.getDuration().intValue(), word.getType1(), word.getType2()));
                }
            }
        }

        listView = (ListView) myView.findViewById(R.id.bm_listView);
        setupList(myView);

        return myView;
    }

    private void setupList(final View v) {
        VocabArrayAdapter = new BookmarkFragment.VocabListAdapter(vocab_list);
        listView.setAdapter(VocabArrayAdapter);
        VocabArrayAdapter.notifyDataSetChanged();

    }


    private class VocabListAdapter extends ArrayAdapter<WordList> {
        private ArrayList<WordList> vocab_list;
        private ArrayList<String> clicked = new ArrayList<String>();
        private ArrayList<Boolean> bookmarked = new ArrayList<Boolean>();

        public VocabListAdapter(ArrayList<WordList> vocab_list) {
            super(BookmarkFragment.this.getContext(), R.layout.listitem_dictionary, vocab_list);
            this.vocab_list = vocab_list;
        }

        @NonNull
        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.listitem_dictionary, parent, false);
            }

            // init
            final WordList currentWordList = vocab_list.get(position);
            clicked.add("FALSE");
            bookmarked.add(false);


            // Set VideoView Behavior
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    SignVideoView videoView = (SignVideoView) arg1.findViewById(R.id.videoView);
                    final MediaController mediaController = new MediaController(getContext());
                    videoView.setPlayPauseListener(new SignVideoView.PlayPauseListener() {
                        @Override
                        public void onPlay() {
                            mediaController.hide();
                            mediaController.setVisibility(GONE);
                        }

                        @Override
                        public void onPause() {
                            mediaController.hide();
                            mediaController.setVisibility(GONE);
                        }
                    });
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            Log.v("SDD","END");
                            mediaController.setVisibility(VISIBLE);
                        }
                    });

                    clicked.set(position,"TRUE");
                    if (clicked.get(position) != "FALSE") {
                        videoView.setVisibility(View.VISIBLE);
                    }
                    try {
                        String link=vocab_list.get(position).getPath();
                        mediaController.setAnchorView(videoView);
                        Uri video = Uri.parse(link);
                        videoView.setMediaController(mediaController);
                        videoView.setVideoURI(video);
                        videoView.start();
                        mediaController.hide();
                    } catch (Exception e) {
                        // Toast.makeText(this, "Error connecting", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            SignVideoView videoView = (SignVideoView) itemView.findViewById(R.id.videoView);
            if (clicked.get(position) == "FALSE") {
                videoView.setVisibility(View.GONE);
            }

            // Set Vocab Text
            ((TextView) itemView.findViewById(R.id.vocab)).setText(currentWordList.getWord());

            // Set bookmark button behavior
            final ImageView bookmark_button = (ImageView) itemView.findViewById(R.id.setBookmark);
            if (bookmarked.get(position) || bookmark_list.contains(currentWordList.getWord())) {
                bookmark_button.setColorFilter(getResources().getColor(R.color.after_bm));
                bookmarked.set(position,true);
            } else {
                bookmark_button.setColorFilter(getResources().getColor(R.color.b4_bm));
            }

            final SharedPreferences.Editor bmEdit = bmPrefs.edit();
            bmEdit.clear();
            bookmark_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bookmarked.get(position)) {
                        bookmark_button.setColorFilter(bookmark_button.getResources().getColor(R.color.b4_bm));
                        bookmark_list.remove(currentWordList.getWord());
                        bookmarked.set(position,false);
                    } else {
                        bookmark_button.setColorFilter(bookmark_button.getResources().getColor(R.color.after_bm));
                        bookmarked.set(position,true);
                        bookmark_list.add(currentWordList.getWord());
                    }

                    for (String vocab : bookmark_list){
                        Log.v("bm ed ", vocab);
                    }
                    // Add bookmark to preference
                    StringBuilder bmBuild = new StringBuilder("");

                    for (int s = 0; s < bookmark_list.size(); s++) {
                        if (s > 0) bmBuild.append("|");//pipe separate the score strings
                        bmBuild.append(bookmark_list.get(s));
                    }

                    bmEdit.putString("Bookmark",bmBuild.toString());
                    bmEdit.commit();
                }
            });



            VocabArrayAdapter.notifyDataSetChanged();
            return itemView;
        }


    }
}

