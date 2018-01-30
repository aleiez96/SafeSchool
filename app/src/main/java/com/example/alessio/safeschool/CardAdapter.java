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

public class CardAdapter extends ArrayAdapter<Integer> {

    public CardAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        pl.droidsonroids.gif.GifTextView imgtut=convertView.findViewById(R.id.imageViewcard);
        imgtut.setBackgroundResource(getItem(position));
        return convertView;
    }
}
