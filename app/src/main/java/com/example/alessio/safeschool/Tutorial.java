package com.example.alessio.safeschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wenchao.cardstack.CardStack;

public class Tutorial extends AppCompatActivity implements CardStack.CardEventListener {

    private CardStack cardstack;
    private CardAdapter cardadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        Button b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),
                        Home.class
                );

                startActivity(intent);
            }
        });
        
        initcards();
        /*cardstack= findViewById(R.id.cardstack);
        cardstack.setContentResource(R.id.cardView);
        Log.i("ciao","ciao");
        cardstack.setAdapter(cardadapter);
        cardstack.setListener(this);*/
    }

    private void initcards() {

        cardadapter= new CardAdapter(this,0);
        cardadapter.add(R.id.mappa);
        cardadapter.add(R.id.preferiti);
    }

    @Override
    public boolean swipeEnd(int section, float distance) {
        return (distance>300)?true:false;
    }

    @Override
    public boolean swipeStart(int section, float distance) {
        return false;
    }

    @Override
    public boolean swipeContinue(int section, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void discarded(int mIndex, int direction) {

    }

    @Override
    public void topCardTapped() {

    }
}
