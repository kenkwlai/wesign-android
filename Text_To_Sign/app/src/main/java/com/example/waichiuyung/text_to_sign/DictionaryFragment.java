package com.example.waichiuyung.text_to_sign;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DictionaryFragment extends Fragment {
    TextView noun, verb, adjective, pronoun, number, other, all;
    static Boolean all_selected = true;
    static Boolean noun_selected = false;
    static Boolean verb_selected = false;
    static Boolean adjective_selected = false;
    static Boolean pronoun_selected = false;
    static Boolean number_selected = false;
    static Boolean other_selected = false;

    private ListView listView;
    private MyAdapter adapter;

    List<WordList> vocab_list = new ArrayList<WordList>();

    Activity a;



    ArrayList<Vocabulary> vocabularies;


    public DictionaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof Activity){
            a=(Activity) context;
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_dictionary, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            vocabularies = (ArrayList<Vocabulary>)bundle.getSerializable("vocabularies");
        }

        for (Vocabulary word: vocabularies) {
            //Log.v("word: ", word.getWordType());
            vocab_list.add(new WordList(word.getWord(),word.getPath(),word.getPrefix(),word.getFrequency().intValue(),word.getWordType()));
        }

        changeColor(myView);

        listView=(ListView) myView.findViewById(R.id.dict_listView);
        adapter = new MyAdapter(a,vocab_list);

        listView.setAdapter(adapter);


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
                if (all_selected) {
                    // 一開波check左  禁落去唔會uncheck
                    // checkButton();
                } else {
                    // 禁全部 會uncheck 其他5個
                    all.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
                    all_selected=true;
                    noun.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    verb.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    adjective.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    pronoun.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    number.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    other.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    noun_selected= false;
                    verb_selected= false;
                    adjective_selected = false;
                    pronoun_selected = false;
                    number_selected = false;
                    other_selected = false;
                    addList(v);
                }
            }
        });

    }

    public void checkButton(View v) {

        // 5個製禁曬 check 全部 + uncheck 5個
        if (noun_selected && verb_selected && adjective_selected && pronoun_selected && number_selected && other_selected) {
            all.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
            noun.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            verb.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            adjective.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            pronoun.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            number.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            other.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            noun_selected= false;
            verb_selected= false;
            adjective_selected = false;
            pronoun_selected = false;
            number_selected = false;
            other_selected = false;
            all_selected = true;

            // click 是但一個 會 uncheck 全部
        }else if (noun_selected || verb_selected || adjective_selected || pronoun_selected || number_selected || other_selected){
            all.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            all_selected = false;
        }else{
            all.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
            all_selected = true;
        }

        addList(v);
    }

    private void addList(View v){
        Log.v("add list function","ADAD");
        vocab_list.clear();
        if (all_selected){
            for (Vocabulary word: vocabularies) {
                Log.v("word in all: ", word.getWord());
                vocab_list.add(new WordList(word.getWord(),word.getPath(),word.getPrefix(),word.getFrequency().intValue(),word.getWordType()));
            }
        }else if (noun_selected){
            for (Vocabulary word: vocabularies) {
                if (word.getWordType().equals("noun")){
                    Log.v("word in noun: ", word.getWord());
                    vocab_list.add(new WordList(word.getWord(),word.getPath(),word.getPrefix(),word.getFrequency().intValue(),word.getWordType()));
                }

            }
        }

        // listView=(ListView) v.findViewById(R.id.dict_listView);
        // adapter = new MyAdapter(a,vocab_list);

        // listView.setAdapter(adapter);


    }


}
