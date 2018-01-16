package com.example.alessio.safeschool;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class ScrollingActivityScuola extends AppCompatActivity {
    boolean check=true;
    static String nome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_scuola);
        Intent intent = getIntent();
        String dato1 = intent.getStringExtra("Codicescuola");
        String dato2 = intent.getStringExtra("nome");
        String dato3 = intent.getStringExtra("id");
        setTitle(dato1);

        String query = "select * from scuole_veneto";
        Cursor cursor = MapsActivity.dbm.query(query, null);

        while(cursor.moveToNext()) {
            int index;
            index = cursor.getColumnIndexOrThrow("id");
            String id = cursor.getString(index);
            if (id.equals(dato1)){
                index = cursor.getColumnIndexOrThrow("istituto_rif_nome");
                nome = cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("nome");
                nome = nome + " - " + cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("indirizzo");
                nome = nome + " | " + cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("provincia");
                nome = nome + ", " + cursor.getString(index);
                break;}

        }


        TextView testo = findViewById(R.id.textView1);
        testo.setText(nome);
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (check == true) {
                    fab.setImageResource(android.R.drawable.btn_star_big_on);
                    Snackbar.make(view, "Aggiunto ai preferiti", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    check=false;
                }
                else {
                    fab.setImageResource(android.R.drawable.btn_star_big_off);
                    Snackbar.make(view, "Eliminato dai preferiti", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    check=true;
                }
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        Intent intent;
        switch (item.getItemId()) {
            case R.id.tutorial_item:
                intent=new Intent(getApplicationContext(),Tutorial.class);
                startActivity(intent);
                return true;
            case R.id.info_item:
                intent=new Intent(getApplicationContext(),ActivityInfo.class);
                startActivity(intent);


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}