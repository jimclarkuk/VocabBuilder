package com.steo.vocab;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.steo.vocab.db.VocabDatabaseAdapter;


public class HomePage extends Activity implements OnClickListener {

    private VocabDatabaseAdapter mVocabDatabase;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter; //TODO: Will need custom adapter for later funkification

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //This is to add elephant icon to title bar
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.main);
        setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                R.drawable.elep_icon);

        mVocabDatabase = new VocabDatabaseAdapter(this);
        mVocabDatabase.open();

        mListView = (ListView) findViewById(R.id.homepageListView);
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        mListView.setAdapter(mAdapter);

        showSetList();

        Button newCatBt = (Button) findViewById(R.id.newCat);
        newCatBt.setOnClickListener(this);

        Button newSetBt = (Button) findViewById(R.id.newSet);
        newSetBt.setOnClickListener(this);

        mVocabDatabase.debugDumpTables();
    }

    @SuppressWarnings("unused")
    private void showCategoryList() {

        ArrayList<String> sets = mVocabDatabase.getCategories();
        mAdapter.clear();
        for(String set : sets ) {
            mAdapter.add(set);
        }

        mAdapter.notifyDataSetChanged();

    }

    private void showSetList() {

        ArrayList<String> sets = mVocabDatabase.getSets();
        mAdapter.clear();
        for(String set : sets ) {
            mAdapter.add(set);
        }

        mAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.newSet) {
            new NewSetDialog(this).show();
        }
        else if(v.getId() == R.id.newCat) {

        }
    }
}