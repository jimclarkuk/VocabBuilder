package com.steo.vocab;

import java.util.ArrayList;

import junit.framework.Assert;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    //TODO: Will need custom adapter for later funkification
    private ArrayAdapter<String> mAdapter;
    private final ArrayList<String> mListData = new ArrayList<String>();

    static final private int SET_MODE = 0;
    static final private int CAT_MODE = 1;

    private int mMode = SET_MODE;

    private Button mNewButton;

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
                android.R.layout.simple_list_item_1, mListData);
        mListView.setAdapter(mAdapter);

        mNewButton = (Button) findViewById(R.id.newButton);
        mNewButton.setOnClickListener(this);

        populateUI();

        mVocabDatabase.debugDumpTables();
    }

    private void populateUI() {

        mListData.clear();

        switch(mMode) {
            case SET_MODE:
                mListData.addAll(mVocabDatabase.getSets());
                mNewButton.setText(R.string.new_set);
                break;

            case CAT_MODE:
                mListData.addAll(mVocabDatabase.getCategories());
                mNewButton.setText(R.string.new_category);
                break;
            default:
                Assert.assertTrue("Things got fucked up", false);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.optionViewCat: mMode = CAT_MODE; break;
            case R.id.optionViewSet: mMode = SET_MODE; break;
            default: return super.onOptionsItemSelected(item);
        }

        populateUI();
        return true;
    }

    @Override
    public void onDestroy() {
        mVocabDatabase.close();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.newButton && mMode == SET_MODE) {
            new NewSetDialog(this).show();
        }
    }
}