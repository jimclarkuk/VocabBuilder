package com.steo.vocab;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Window;

import com.steo.vocab.db.VocabDatabaseAdapter;

public class HomePage extends Activity {

    private VocabDatabaseAdapter mVocabDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.main);
        setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                R.drawable.elep_icon);

        mVocabDatabase = new VocabDatabaseAdapter(this);
        mVocabDatabase.open();

        ArrayList<Category> cats = mVocabDatabase.getCategories();
        for(Category c : cats) {
            Log.w("STEO", "Cat: " + c.category);
        }

        mVocabDatabase.getVocab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onDestroy() {
        mVocabDatabase.close();
    }
}