package com.example.alessio.safeschool;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by massimiliano on 29/01/18.
 */

public class CardAdapter extends ArrayAdapter {

    public CardAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView imgtut=convertView.findViewById(R.id.imageViewcard);
        Log.i("ciao","ciao");
        imgtut.setImageResource((int) getItem(position));
        return convertView;
    }
}
