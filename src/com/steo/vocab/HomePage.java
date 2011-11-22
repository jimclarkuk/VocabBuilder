package com.steo.vocab;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class HomePage extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.main);
        setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                R.drawable.elep_icon);
    }
}