package com.example.alessio.safeschool;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScrollingActivityScuola extends AppCompatActivity {

    boolean check=true;
    static String nome;
    String dato1;
    Scuola scuola = new Scuola();
    static DataBaseHelper mDBHelper;
    static DbManager dbm;
    static SQLiteDatabase mDb;
    TabHost tabHost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scrolling_scuola);
        super.onCreate(savedInstanceState);
        final FloatingActionButton fab = findViewById(R.id.fab);
        Intent intent = getIntent();
        dato1 = intent.getStringExtra("Codicescuola");


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
        String query4 = "select * from scuole_veneto";
        Cursor cursor4 =dbm.query(query4, null);
        Log.e("cursor", cursor4.getColumnName(0));
        Log.e("cursor", cursor4.getColumnName(1));
        Log.e("cursor", cursor4.getColumnName(2));
        Log.e("cursor", cursor4.getColumnName(3));
        Log.e("cursor", cursor4.getColumnName(4));
        Log.e("cursor", cursor4.getColumnName(5));
        Log.e("cursor", cursor4.getColumnName(6));
        Log.e("cursor", cursor4.getColumnName(7));
        Log.e("cursor", cursor4.getColumnName(8));
        Log.e("cursor", cursor4.getColumnName(9));
        Log.e("cursor", cursor4.getColumnName(10));
        Log.e("cursor", cursor4.getColumnName(11));
        Log.e("cursor", cursor4.getColumnName(12));
        Log.e("cursor", cursor4.getColumnName(13));
        Log.e("cursor", cursor4.getColumnName(14));
        Log.e("cursor", cursor4.getColumnName(15));
        Log.e("cursor", cursor4.getColumnName(16));
        Log.e("cursor", cursor4.getColumnName(17));
        Log.e("cursor", cursor4.getColumnName(18));
        Log.e("cursor", cursor4.getColumnName(19));
        Log.e("cursor", cursor4.getColumnName(20));
        String query3 = "select * from vincoli";
        Cursor cursor3 =dbm.query(query3, null);
        Log.e("cursor", cursor3.getColumnName(0));
        Log.e("cursor", cursor3.getColumnName(1));
        Log.e("cursor", cursor3.getColumnName(2));
        Log.e("cursor", cursor3.getColumnName(3));
        Log.e("cursor", cursor3.getColumnName(4));
        Log.e("cursor", cursor3.getColumnName(5));
        Log.e("cursor", cursor3.getColumnName(6));
        Log.e("cursor", cursor3.getColumnName(7));


        String query = "select * from scuole_veneto inner join vincoli on scuole_veneto.id=vincoli.id_scuola";
        Cursor cursor =dbm.query(query, null);
        while(cursor.moveToNext()) {
            int index;
            index = cursor.getColumnIndexOrThrow("id");
            String id = cursor.getString(index);
            if (id.equals(dato1)){
                index = cursor.getColumnIndexOrThrow("istituto_rif_nome");
                nome = "\nistituto riferimento: "+cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("nome");
                scuola.setNome(cursor.getString(index));
                nome = nome + "\n nome scuola: " + cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("indirizzo");
                nome = nome + "\n indirizzo: " + cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("provincia");
                nome = nome + "\n provincia: " + cursor.getString(index);
                scuola.setProvincia(cursor.getString(index));
                index = cursor.getColumnIndexOrThrow("tipologia_grado_istruzione");
                scuola.setGrado(cursor.getString(index));
                index = cursor.getColumnIndexOrThrow("sito_web");
                scuola.setSito(cursor.getString(index));
                Log.e("sito",cursor.getString(index));
                break;}

        }
        TextView titolo = findViewById(R.id.textView4);
        titolo.setText((CharSequence) scuola.getNome());
        setTitle(dato1+" - "+scuola.getNome());
        TextView grado = findViewById(R.id.textView6);
        grado.setText((CharSequence) scuola.getGrado());
        TextView testo = findViewById(R.id.textView5);
        testo.setText(nome);
        TabHost host = findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Uno");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Informazioni");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Due");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Vincoli");
        host.addTab(spec);


        ImageView i1 = findViewById(R.id.sito);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scuola.getSito().equals("Non Disponibile")){
                    Toast.makeText(ScrollingActivityScuola.this,"Sito non disponibile", Toast.LENGTH_SHORT).show();
                }
                else{
                    Uri uri = Uri.parse("http://"+scuola.getSito()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);}
            }
        });
        TextView i2 = findViewById(R.id.textView7);
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scuola.getSito().equals("Non Disponibile")){
                    Toast.makeText(ScrollingActivityScuola.this,"Sito non disponibile", Toast.LENGTH_SHORT).show();
                }
                else{
                    Uri uri = Uri.parse("http://"+scuola.getSito()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);}
            }
        });



        //////////controllo se esiste preferito////////////////
        String queryPreferiti = "select * from preferiti";
        Cursor cursor2 = dbm.query(queryPreferiti, null);

        while(cursor2!=null && cursor2.moveToNext()) {
            int index;
            index = cursor2.getColumnIndexOrThrow("id_scuola");
            String id = cursor2.getString(index);
            if (id.equals(dato1)){
                fab.setImageResource(android.R.drawable.btn_star_big_on);
                check=false;
            }
        }



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
                    values.put("id_scuola",p.getId_scuola() );
                    values.put("data_inserimento", p.getData_inserimento());
                    values.put("descrizione", p.getDescrizione());

                    dbm.insert("preferiti",  values);
                }
                else {
                    fab.setImageResource(android.R.drawable.btn_star_big_off);
                    Snackbar.make(view, "Eliminato dai preferiti", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    /////////////eliminazione scuola//////////////
                    dbm.delete("preferiti","id_scuola='"+dato1+"'",null);
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