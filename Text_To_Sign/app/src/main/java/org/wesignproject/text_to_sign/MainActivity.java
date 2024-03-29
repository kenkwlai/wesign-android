package org.wesignproject.text_to_sign;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.wesignproject.text_to_sign.Config.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements Serializable{

    private String currentFragment;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private Bundle bundle;
    private SharedPreferences Prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Vocabulary> vocabularies = new ArrayList<>();

        // get vocab list from saved JSON
        Prefs = getSharedPreferences(Config.CONFIG_PREFS, 0);
        String JSON = Prefs.getString(Config.VOCAB_JSON, "");
        if (!JSON.equals("")) {
            List<Vocabulary> result = new Gson().fromJson(JSON, new TypeToken<List<Vocabulary>>() {}.getType());
            vocabularies.addAll(result);
        }

        bundle = new Bundle();
        bundle.putSerializable("vocabularies", vocabularies);

        currentFragment = "Translate";

        // Initial Fragment when users open the app
        TranslateFragment translate = new TranslateFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_area, translate).commit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("翻譯");
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
                                    translate.setArguments(bundle);
                                    transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                                    transaction.replace(R.id.fragment_area, translate);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    currentFragment = "Translate";
                                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                                    toolbar.setTitle("翻譯");
                                    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                                }
                                break;
                            case R.id.action_two:
                                if (currentFragment != "Dictionary") {
                                    DictionaryFragment dictionary = new DictionaryFragment();
                                    dictionary.setArguments(bundle);
                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                    transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                                    transaction.replace(R.id.fragment_area, dictionary);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    currentFragment = "Dictionary";
                                    bottomNavigationView.getMenu().getItem(1).setChecked(true);
                                    toolbar.setTitle("字典");
                                    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                                }
                                break;
                            case R.id.action_three:
                                if (currentFragment != "Bookmark") {
                                    BookmarkFragment bookmark = new BookmarkFragment();
                                    bookmark.setArguments(bundle);
                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                    transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                                    transaction.replace(R.id.fragment_area, bookmark);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    currentFragment = "Bookmark";
                                    bottomNavigationView.getMenu().getItem(2).setChecked(true);
                                    toolbar.setTitle("書籤");
                                    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                                }
                                break;
                        }
                        return false;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
}


