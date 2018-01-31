package com.example.alessio.safeschool;

import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wenchao.cardstack.CardStack;

public class Tutorial extends AppCompatActivity implements CardStack.CardEventListener {

    private CardStack cardstack;
    private CardAdapter cardadapter;
    private PopupWindow mPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Tutorial");
        setContentView(R.layout.activity_tutorial);
        Button b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),
                        Home.class
                );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
            }
        });
        
        initcards();
        cardstack= findViewById(R.id.cardstack);
        cardstack.setContentResource(R.layout.video_layout);
        cardstack.setStackMargin(20);
        cardstack.setAdapter(cardadapter);

        cardstack.setListener(this);


    }

    private void initcards() {

        cardadapter= new CardAdapter(this,0);
        cardadapter.add(R.drawable.giphy1);
        cardadapter.add(R.drawable.giphy2);
        cardadapter.add(R.drawable.giphy3);
        cardadapter.add(R.drawable.giphy4);
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
        if (mIndex==4){
            Button bu=findViewById(R.id.button1);
            bu.setVisibility(View.VISIBLE);
            TextView tx=findViewById(R.id.textView11);
            tx.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void topCardTapped() {
        final ConstraintLayout mRelative = findViewById(R.id.cl);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.popup_layout2, null);
        mPopupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.showAtLocation(mRelative, Gravity.CENTER, 0, 0);
    }
}
