package com.example.waichiuyung.text_to_sign;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    private String currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentFragment = "Translate";

        TranslateFragment translate = new TranslateFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_area, translate).commit();



        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_one:

                                if (currentFragment != "Translate") {

                                    TranslateFragment translate = new TranslateFragment();
                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                                    transaction.replace(R.id.fragment_area, translate);
                                    transaction.addToBackStack(null);
                                    transaction.commit();


                                    currentFragment = "Translate";
                                }
                                break;
                            case R.id.action_two:
                                if (currentFragment != "Dictionary") {

                                    DictionaryFragment dictionary = new DictionaryFragment();
                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                    if (currentFragment == "Translate"){
                                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                    }else{
                                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                                    }
                                    transaction.replace(R.id.fragment_area, dictionary);
                                    transaction.addToBackStack(null);
                                    transaction.commit();

                                    currentFragment = "Dictionary";
                                }                                break;
                            case R.id.action_three:
                                if (currentFragment != "Bookmark") {

                                    BookmarkFragment bookmark = new BookmarkFragment();
                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                    transaction.replace(R.id.fragment_area, bookmark);
                                    transaction.addToBackStack(null);
                                    transaction.commit();

                                    currentFragment = "Bookmark";
                                }                                break;
                        }
                        return false;
                    }
                });

    }





}
