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
    static String nome, nome1,nome2;
    String dato1;
    Scuola scuola = new Scuola();
    Vincolo vincolo = new Vincolo();
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
        String query4 = "select * from scuole";
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


        String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola";
        Cursor cursor =dbm.query(query, null);
        TextView uno = findViewById(R.id.textView13);
        TextView due = findViewById(R.id.textView14);
        TextView tre = findViewById(R.id.textView15);
        TextView quattro = findViewById(R.id.textView16);
        quattro.setText("COD."+dato1);
        while(cursor.moveToNext()) {
            int index;
            index = cursor.getColumnIndexOrThrow("id");
            scuola.setId(cursor.getString(index));
            Log.e("1",cursor.getString(index));
            String id = cursor.getString(index);
            if (id.equals(dato1)){
                index = cursor.getColumnIndexOrThrow("nome");
                Log.e("1",cursor.getString(index));
                scuola.setNome(cursor.getString(index).replaceAll("\\s+"," "));
                index = cursor.getColumnIndexOrThrow("istituto_rif_nome");
                nome=cursor.getString(index);
                nome=nome.replaceAll("\\s+"," ");
                index = cursor.getColumnIndexOrThrow("istituto_rif_codice");
                nome = nome + "\n(" + cursor.getString(index)+")";
                uno.setText(nome);
                nome=null;
                index = cursor.getColumnIndexOrThrow("indirizzo");
                nome = cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("cap");
                nome = nome + ", " + cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("comune_nome");
                nome1 = cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("provincia");
                scuola.setProvincia(cursor.getString(index));
                nome1 = nome1 + ", " + cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("regione");
                nome1 = nome1 + ", " + cursor.getString(index);
                nome=nome.replaceAll("\\s+", " ");
                nome1=nome1.replaceAll("\\s+", " ");
                due.setText(nome+"\n"+nome1);
                index = cursor.getColumnIndexOrThrow("tipologia_grado_istruzione");
                scuola.setGrado(cursor.getString(index));
                index = cursor.getColumnIndexOrThrow("sito_web");
                scuola.setSito(cursor.getString(index));
                index = cursor.getColumnIndexOrThrow("indirizzo_email");
                nome2 = "E-mail: " + cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("indirizzo_pec");
                nome2 = nome2+"\nPEC: " + cursor.getString(index);
                tre.setText(nome2);
                break;}

        }

        //Inizializzazione tab e titolo
        TextView titolo = findViewById(R.id.textView4);
        titolo.setText((CharSequence) scuola.getNome());
        setTitle(dato1+" - "+scuola.getNome());
        TextView grado = findViewById(R.id.textView6);
        grado.setText((CharSequence) scuola.getGrado());
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

        //Click per sito web
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

        //Data inserimento nei preferiti
        String queryp = "select * from preferiti";
        Cursor cursor5 = dbm.query(queryp, null);
        Preferito preferito = new Preferito();

        while(cursor5 != null && cursor5.moveToNext()) {
            int index = cursor5.getColumnIndexOrThrow("id_scuola");
            preferito.setId_scuola(cursor5.getString(index));
            if (preferito.getId_scuola().equals(dato1)) {
                index = cursor5.getColumnIndexOrThrow("data_inserimento");
                preferito.setData_inserimento(cursor5.getString(index));
                TextView data = findViewById(R.id.textView8);
                data.setText("Aggiunto ai preferiti in data "+preferito.getData_inserimento()+".");
            }
        }

        //Set vincoli
        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            int index;
            index = cursor.getColumnIndexOrThrow("id");
            String id = cursor.getString(index);
            if (id.equals(dato1)){
                index = cursor.getColumnIndexOrThrow("vincoli_idrogeologici");
                vincolo.setVincoli_idrogeologici(cursor.getString(index));
                index = cursor.getColumnIndexOrThrow("vincoli_paesaggio");
                vincolo.setVincoli_paesaggio(cursor.getString(index));
                index = cursor.getColumnIndexOrThrow("edificio_vetusto");
                vincolo.setEdificio_vetusto(cursor.getString(index));
                index = cursor.getColumnIndexOrThrow("zona_sismica");
                vincolo.setZona_sismica(cursor.getString(index));
                index = cursor.getColumnIndexOrThrow("progettazione_antisismica");
                vincolo.setProgettazione_antisismica(cursor.getString(index));
                break;}

        }
        ImageView a = findViewById(R.id.imageView);
        ImageView b = findViewById(R.id.imageView4);
        ImageView c = findViewById(R.id.imageView7);
        ImageView d = findViewById(R.id.imageView8);
        TextView zona= findViewById(R.id.testoa3);
        if (vincolo.isVincoli_idrogeologici()){
            a.setImageResource(R.mipmap.si);
        }
        else{
            a.setImageResource(R.mipmap.no);
        }
        if (vincolo.isVincoli_paesaggio()){
            b.setImageResource(R.mipmap.si);
        }
        else{
            b.setImageResource(R.mipmap.no);
        }
        if (vincolo.isEdificio_vetusto()){
            c.setImageResource(R.mipmap.si);
        }
        else{
            c.setImageResource(R.mipmap.no);
        }
        if (vincolo.isProgettazione_antisismica()){
            d.setImageResource(R.mipmap.si);
        }
        else{
            d.setImageResource(R.mipmap.no);
        }
        zona.setText(vincolo.getZona_sismica());

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