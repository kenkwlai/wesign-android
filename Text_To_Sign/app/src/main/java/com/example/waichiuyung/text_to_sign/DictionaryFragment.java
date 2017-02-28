package com.example.waichiuyung.text_to_sign;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.waichiuyung.text_to_sign.Utils.MyUtils;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DictionaryFragment extends Fragment {
    TextView noun, verb, adjective, pronoun, number, other, all;
    SearchView searchView;
    static Boolean all_selected = true;
    static Boolean noun_selected = false;
    static Boolean verb_selected = false;
    static Boolean adjective_selected = false;
    static Boolean pronoun_selected = false;
    static Boolean number_selected = false;
    static Boolean other_selected = false;

    private ListView listView;

    ArrayList<WordList> vocab_list = new ArrayList<WordList>();
    ArrayList<String> bookmark_list = new ArrayList<String>();

    ArrayList<Vocabulary> vocabularies;
    ArrayAdapter<WordList> VocabArrayAdapter;

    Activity mActivity;

    private SharedPreferences bmPrefs;
    public static final String BM_PREFS = "BookMarkFile";

    public DictionaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.fragment_dictionary, container, false);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            vocabularies = (ArrayList<Vocabulary>) bundle.getSerializable("vocabularies");
        }

        for (Vocabulary word : vocabularies) {
            vocab_list.add(new WordList(word.getWord(), word.getPath(), word.getPrefix(), word.getFrequency().intValue(), word.getType1(), word.getType2()));
        }

        bmPrefs = getActivity().getSharedPreferences(BM_PREFS,0);

        String[] bm = bmPrefs.getString("Bookmark", "").split("\\|");
        for(String score : bm){
            bookmark_list.add(score);
        }

        changeColor(myView);
        searchView = (SearchView) myView.findViewById(R.id.searchView);
        final SearchManager searchManager = (SearchManager)
                mActivity.getSystemService(Context.SEARCH_SERVICE);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return false;
            }

            public boolean onQueryTextSubmit(String query) {
                VocabArrayAdapter.getFilter().filter(query);
                Log.v("query",query);
                return true;
            }
        };
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(mActivity.getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(queryTextListener);



        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (searchView.getQuery().length()>0){
                    Log.v("1","first x");
                    searchView.setQuery("", false);
                }else{
                    searchView.onActionViewCollapsed();
                    setupList(addList());
                }
                return false;
            }
        });


        listView = (ListView) myView.findViewById(R.id.dict_listView);
        setupList(vocab_list);


        return myView;
    }


    public void changeColor(View v) {
        all = (TextView) v.findViewById(R.id.all);
        noun = (TextView) v.findViewById(R.id.noun);
        verb = (TextView) v.findViewById(R.id.verb);
        adjective = (TextView) v.findViewById(R.id.adjective);
        pronoun = (TextView) v.findViewById(R.id.pronoun);
        number = (TextView) v.findViewById(R.id.number);
        other = (TextView) v.findViewById(R.id.other);
        searchView = (SearchView) v.findViewById(R.id.searchView);




        noun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noun_selected) {
                    noun.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    noun_selected = false;
                } else {
                    noun.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
                    noun_selected = true;
                }
                checkButton(v);
            }
        });

        verb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verb_selected) {
                    verb.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    verb_selected = false;
                } else {
                    verb.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
                    verb_selected = true;
                }
                checkButton(v);
            }
        });

        adjective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adjective_selected) {
                    adjective.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    adjective_selected = false;
                } else {
                    adjective.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
                    adjective_selected = true;
                }
                checkButton(v);
            }
        });

        pronoun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pronoun_selected) {
                    pronoun.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    pronoun_selected = false;
                } else {
                    pronoun.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
                    pronoun_selected = true;
                }
                checkButton(v);
            }
        });

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number_selected) {
                    number.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    number_selected = false;
                } else {
                    number.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
                    number_selected = true;
                }
                checkButton(v);
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (other_selected) {
                    other.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    other_selected = false;
                } else {
                    other.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
                    other_selected = true;
                }
                checkButton(v);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("", false);
                searchView.onActionViewCollapsed();
                if (all_selected) {
                    // 一開波check左  禁落去唔會uncheck
                    // checkButton();
                } else {
                    // 禁全部 會uncheck 其他5個
                    all.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
                    all_selected = true;
                    noun.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    verb.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    adjective.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    pronoun.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    number.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    other.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    noun_selected = false;
                    verb_selected = false;
                    adjective_selected = false;
                    pronoun_selected = false;
                    number_selected = false;
                    other_selected = false;
                    setupList(addList());
                }
            }
        });

    }

    public void checkButton(View v) {

        searchView.setQuery("", false);
        searchView.onActionViewCollapsed();

        // 5個製禁曬 check 全部 + uncheck 5個
        if (noun_selected && verb_selected && adjective_selected && pronoun_selected && number_selected && other_selected) {
            all.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
            noun.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            verb.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            adjective.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            pronoun.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            number.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            other.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            noun_selected = false;
            verb_selected = false;
            adjective_selected = false;
            pronoun_selected = false;
            number_selected = false;
            other_selected = false;
            all_selected = true;

            // click 是但一個 會 uncheck 全部
        } else if (noun_selected || verb_selected || adjective_selected || pronoun_selected || number_selected || other_selected) {
            all.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            all_selected = false;
        } else {
            all.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
            all_selected = true;
        }
        setupList(addList());
    }

    private ArrayList<WordList> addList() {
        vocab_list.clear();

        if (all_selected) {
            for (Vocabulary word : vocabularies) {
                vocab_list.add(new WordList(word.getWord(), word.getPath(), word.getPrefix(), word.getFrequency().intValue(), word.getType1(), word.getType2()));
            }
        }
        if (noun_selected) {
            for (Vocabulary word : vocabularies) {
                if (MyUtils.matchType(word, "noun")) {
                    vocab_list.add(new WordList(word.getWord(), word.getPath(), word.getPrefix(), word.getFrequency().intValue(), word.getType1(), word.getType2()));
                }

            }
        }
        if (verb_selected) {
            for (Vocabulary word : vocabularies) {
                if (MyUtils.matchType(word, "verb")) {
                    vocab_list.add(new WordList(word.getWord(), word.getPath(), word.getPrefix(), word.getFrequency().intValue(), word.getType1(), word.getType2()));
                }

            }
        }
        if (adjective_selected) {
            for (Vocabulary word : vocabularies) {
                if (MyUtils.matchType(word, "adjective")) {
                    vocab_list.add(new WordList(word.getWord(), word.getPath(), word.getPrefix(), word.getFrequency().intValue(), word.getType1(), word.getType2()));
                }

            }
        }
        if (pronoun_selected) {
            for (Vocabulary word : vocabularies) {
                if (MyUtils.matchType(word, "pronoun")) {
                    vocab_list.add(new WordList(word.getWord(), word.getPath(), word.getPrefix(), word.getFrequency().intValue(), word.getType1(), word.getType2()));
                }

            }
        }
        if (number_selected) {
            for (Vocabulary word : vocabularies) {
                if (MyUtils.matchType(word, "number")) {
                    vocab_list.add(new WordList(word.getWord(), word.getPath(), word.getPrefix(), word.getFrequency().intValue(), word.getType1(), word.getType2()));
                }

            }
        }
        if (other_selected) {
            for (Vocabulary word : vocabularies) {
                if (!(MyUtils.matchType(word, "noun") || MyUtils.matchType(word, "verb") || MyUtils.matchType(word, "adjective") || MyUtils.matchType(word, "pronoun") || MyUtils.matchType(word, "number"))) {
                    vocab_list.add(new WordList(word.getWord(), word.getPath(), word.getPrefix(), word.getFrequency().intValue(), word.getType1(), word.getType2()));
                }

            }
        }

        return vocab_list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    private void setupList(ArrayList<WordList> list) {
        VocabArrayAdapter = new VocabListAdapter(list);
        listView.setAdapter(VocabArrayAdapter);
        VocabArrayAdapter.notifyDataSetChanged();
    }

    private class VocabListAdapter extends ArrayAdapter<WordList> {
        private ArrayList<WordList> vocab_list;
        private ArrayList<String> clicked = new ArrayList<String>();
        private ArrayList<Boolean> bookmarked = new ArrayList<Boolean>();
        private WordFilter wordFilter;
        private ArrayList<WordList> filteredList;


        public VocabListAdapter(ArrayList<WordList> vocab_list) {
            super(DictionaryFragment.this.getContext(), R.layout.listitem_dictionary, vocab_list);
            this.vocab_list = addList();
            this.filteredList = vocab_list;
            getFilter();
        }



        @Override
        public Filter getFilter() {
            if (wordFilter == null) {
                wordFilter = new WordFilter();
            }
            return wordFilter;
        }

        @NonNull
        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.listitem_dictionary, parent, false);
            }

            // init
            final WordList currentWordList = filteredList.get(position);
            clicked.add("FALSE");
            bookmarked.add(false);


            // Set VideoView Behavior
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    VideoView videoView = (VideoView) arg1.findViewById(R.id.videoView);
                    clicked.set(position,"TRUE");
                    if (clicked.get(position) != "FALSE") {
                        videoView.setVisibility(View.VISIBLE);
                    }
                    try {
                        String link=vocab_list.get(position).getPath();
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
                }
            });
            VideoView videoView = (VideoView) itemView.findViewById(R.id.videoView);
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

        private class WordFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint!=null && constraint.length()>0) {
                    ArrayList<WordList> tempList = new ArrayList<WordList>();

                    // search content in friend list
                    for (WordList word : vocab_list) {
                        if (word.getWord().contains(constraint.toString())) {
                            tempList.add(word);
                        }
                    }

                    filterResults.count = tempList.size();
                    filterResults.values = tempList;
                } else {
                    filterResults.count = vocab_list.size();
                    filterResults.values = vocab_list;
                }

                return filterResults;
            }

            /**
             * Notify about filtered list to ui
             * @param constraint text
             * @param results filtered result
             */
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                filteredList = (ArrayList<WordList>) results.values;
                VocabArrayAdapter.clear();
                setupList(filteredList);
            }
        }


    }
}
