package com.example.alessio.safeschool;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;


public class ScrollingActivityScuola extends AppCompatActivity {

    boolean check=true;
    Scuole s;

    private DataBaseHelper mDBHelper;
    private DbManager dbm;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_scuola);
        Intent intent = getIntent();
        String dato1 = intent.getStringExtra("nome");
        String dato2 = intent.getStringExtra("id");
       /* String dato1 = intent.getStringExtra("Codicescuola");
        setTitle(dato1);
        for (final CsvRowParser.Row r : prow) {
            if (r.get("CODICESCUOLA").equals(dato1)){
                nome=r.get("DENOMINAZIONEISTITUTORIFERIMENTO")+" - "+r.get("DENOMINAZIONESCUOLA");
                break;
            }
        }
        */
        s=aggiungi(dato2);
      //  Log.i("sito",s.sito);
        TextView testo = findViewById(R.id.textView1);
        testo.setText(dato1+" - "+dato2);
/*"\n"+"indirizzo"+s.indirizzo+"\nindirizzo email"+s.indirizzo_email*/
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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

    public Scuole aggiungi(String id){
        /********************* TEST DB **********************/
        mDBHelper = new DataBaseHelper(this);
        dbm = new DbManager(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        String query = "select * from scuole_veneto where id='"+id+"'";
        Cursor cursor = dbm.query(query, null);

        while(cursor.moveToNext()) {
            int index;


            index = cursor.getColumnIndexOrThrow("indirizzo");
            String indirizzo= cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("indirizzo_email");
            String indirizzo_email = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("sito_web");
            String sito = cursor.getString(index);
            //... do something with data
            Scuole s=new Scuole(sito,indirizzo,indirizzo_email);


        }
        /*******************************************/
        return s;
    }

}