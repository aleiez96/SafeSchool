package com.example.alessio.safeschool;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;


public class ScrollingActivityScuola extends AppCompatActivity {

    boolean check=true;
    static String nome;
    String dato1;
    int seq=0;


    static DataBaseHelper mDBHelper;
    static DbManager dbm;
    static SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scrolling_scuola);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        dato1 = intent.getStringExtra("Codicescuola");
        setTitle(dato1);



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

        ////////////dati scuola//////////////
        String query = "select * from scuole_veneto";
        Cursor cursor =dbm.query(query, null);

        while(cursor.moveToNext()) {
            int index;
            index = cursor.getColumnIndexOrThrow("id");
            String id = cursor.getString(index);
            if (id.equals(dato1)){
                index = cursor.getColumnIndexOrThrow("istituto_rif_nome");
                nome = "\nistituto riferimento: "+cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("nome");
                nome = nome + "\n nome scuola: " + cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("indirizzo");
                nome = nome + "\n indirizzo: " + cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("provincia");
                nome = nome + "\n provincia: " + cursor.getString(index);
                break;}

        }

        //////////controllo se esiste preferito////////////////
        String queryPreferiti = "select * from preferiti where id_scuola='"+dato1+"'";
        Cursor cursor2 = dbm.query(queryPreferiti, null);
        Preferito preferito = new Preferito();

        while(cursor2 != null && cursor2.moveToNext()) {
            int index = cursor2.getColumnIndexOrThrow("id_scuola");
            preferito.setId_scuola(cursor2.getString(index));

            /*index = cursor2.getColumnIndexOrThrow("data_inserimento");
            preferito.setData_inserimento(cursor2.getString(index));

            index = cursor2.getColumnIndexOrThrow("descrizione");
            preferito.setDescrizione(cursor2.getString(index));*/
            //check=true;
            Log.i("pref","ciao");
        }



        Log.i("provaaa",nome);
        TextView testo = findViewById(R.id.textView1);

        testo.setText(nome);


        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                Preferito p=new Preferito(dato1,date,"preferito");

                if (check) {
                    fab.setImageResource(android.R.drawable.btn_star_big_on);
                    Snackbar.make(view, "Aggiunto ai preferiti", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    check=false;


                    //////inserimento scuola//////////
                    ContentValues values = new ContentValues();
                    values.put("id_scuola",p.getId_scuola() ); // Shop Name
                    values.put("data_inserimento", p.getData_inserimento()); // Shop Phone Number
                    values.put("descrizione", p.getDescrizione());

                    dbm.insert("preferiti",  values);
                    Log.i("premo stella",p.getId_scuola()+p.getData_inserimento());





                }
                else {
                    fab.setImageResource(android.R.drawable.btn_star_big_off);
                    Snackbar.make(view, "Eliminato dai preferiti", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    /////////////eliminazione scuola//////////////  DA CONTROLLARE!
                    String[] dati={dato1};
                    dbm.delete("preferiti","id_scuola='"+dato1+"'",null);
                    Log.i("cancellato","cancellato");


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