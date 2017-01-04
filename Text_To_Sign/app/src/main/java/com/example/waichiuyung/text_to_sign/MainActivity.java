package com.example.waichiuyung.text_to_sign;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    private String currentFragment;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentFragment = "Translate";

        // Initial Fragment when users open the app
        TranslateFragment translate = new TranslateFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_area, translate).commit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Translate");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        bottomNavigationView = (BottomNavigationView)
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
                                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                                    toolbar.setTitle("Translate");
                                    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
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
                                    bottomNavigationView.getMenu().getItem(1).setChecked(true);
                                    toolbar.setTitle("Dictionary");
                                    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                                }
                                break;
                            case R.id.action_three:
                                if (currentFragment != "Bookmark") {
                                    BookmarkFragment bookmark = new BookmarkFragment();
                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                    transaction.replace(R.id.fragment_area, bookmark);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    currentFragment = "Bookmark";
                                    bottomNavigationView.getMenu().getItem(2).setChecked(true);
                                    toolbar.setTitle("Bookmark");
                                    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                                }
                                break;
                        }
                        return false;
                    }
                });

    }





}
