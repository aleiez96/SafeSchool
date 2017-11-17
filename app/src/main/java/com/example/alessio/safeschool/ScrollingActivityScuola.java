package com.example.alessio.safeschool;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import static com.example.alessio.safeschool.R.drawable.bt1;

public class ScrollingActivityScuola extends AppCompatActivity {

    boolean check=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_scuola);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Aggiunto ai preferiti", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                if(check==true)
                    fab.setImageResource(R.mipmap.staron);
                else
                    fab.setBackgroundColor(R.mipmap.staroff);
            }
        });

    }
}
