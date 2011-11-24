package com.steo.vocab;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.steo.vocab.db.VocabDatabaseAdapter;


public class HomePage extends Activity implements OnClickListener {

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

        Button newCatBt = (Button) findViewById(R.id.newCat);
        newCatBt.setOnClickListener(this);

        Button newSetBt = (Button) findViewById(R.id.newSet);
        newSetBt.setOnClickListener(this);

        mVocabDatabase.debugDumpTables();
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