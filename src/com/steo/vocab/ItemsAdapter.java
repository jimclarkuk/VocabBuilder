package com.steo.vocab;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.steo.vocab.db.IDItem;

public class ItemsAdapter extends ArrayAdapter<IDItem<String>>{

    public ItemsAdapter(Context context, int textViewResourceId,
            List<IDItem<String>> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(android.R.layout.simple_list_item_1,
                    null);
        }

        TextView tv = (TextView)convertView;
        tv.setText(getItem(position).item);

        return tv;
    }
}
