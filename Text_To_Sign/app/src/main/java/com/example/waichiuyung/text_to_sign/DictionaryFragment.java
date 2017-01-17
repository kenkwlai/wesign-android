package com.example.waichiuyung.text_to_sign;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DictionaryFragment extends Fragment {
    TextView noun, verb, adjective, adverb, number, all;
    Boolean all_selected = true;
    Boolean noun_selected = false;
    Boolean verb_selected = false;
    Boolean adjective_selected = false;
    Boolean adverb_selected = false;
    Boolean number_selected = false;

    public DictionaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_dictionary, container, false);

        changeColor(myView);

        return myView;
    }

    public void changeColor(View v) {
        all = (TextView) v.findViewById(R.id.all);
        noun = (TextView) v.findViewById(R.id.noun);
        verb = (TextView) v.findViewById(R.id.verb);
        adjective = (TextView) v.findViewById(R.id.adjective);
        adverb = (TextView) v.findViewById(R.id.adverb);
        number = (TextView) v.findViewById(R.id.number);

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
                checkButton();
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
                checkButton();
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
                checkButton();
            }
        });

        adverb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adverb_selected) {
                    adverb.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    adverb_selected = false;
                } else {
                    adverb.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
                    adverb_selected = true;
                }
                checkButton();
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
                checkButton();
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (all_selected) {
                    // 一開波check左  禁落去唔會uncheck
                    checkButton();
                } else {
                    // 禁全部 會uncheck 其他5個
                    all.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
                    all_selected=true;
                    noun.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    verb.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    adjective.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    adverb.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    number.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
                    noun_selected= false;
                    verb_selected= false;
                    adjective_selected = false;
                    adverb_selected = false;
                    number_selected = false;

                }
            }
        });

    }

    public void checkButton() {

        // 5個製禁曬 check 全部 + uncheck 5個
        if (noun_selected && verb_selected && adjective_selected && adverb_selected && number_selected) {
            all.setBackground(getResources().getDrawable(R.drawable.round_rect_shape));
            noun.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            verb.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            adjective.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            adverb.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            number.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            noun_selected= false;
            verb_selected= false;
            adjective_selected = false;
            adverb_selected = false;
            number_selected = false;
            all_selected = true;

            // click 是但一個 會 uncheck 全部
        }else if (noun_selected || verb_selected || adjective_selected || adverb_selected || number_selected){
            all.setBackground(getResources().getDrawable(R.drawable.round_empty_rect_shape));
            all_selected = false;
        }


    }
}
