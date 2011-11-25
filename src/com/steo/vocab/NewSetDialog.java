package com.steo.vocab;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

/** Class Must extends with Dialog */
/** Implement onClickListener to dismiss dialog when OK Button is pressed */
public class NewSetDialog extends Dialog implements OnClickListener {

    public NewSetDialog(Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.new_set_dialog);
        Button bt = (Button) findViewById(R.id.okButton);
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.okButton)
            dismiss();
    }
}