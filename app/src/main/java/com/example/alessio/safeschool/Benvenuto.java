package com.example.alessio.safeschool;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;


public class Benvenuto extends AppCompatActivity{
    private DataBaseHelper mDBHelper;
    private DbManager dbm;
    private SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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


        /******************* esempio Scuola ********************/
        String queryScuole = "select * from scuole_veneto limit 1";
        Cursor cursor = dbm.query(queryScuole, null);
        Scuola scuola = new Scuola();

        while(cursor != null && cursor.moveToNext()) {
            int index = cursor.getColumnIndexOrThrow("id");
            scuola.setId(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("nome");
            scuola.setNome(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("provincia");
            scuola.setProvincia(cursor.getString(index));

            index = cursor.getColumnIndexOrThrow("longitudine");
            scuola.setLongitudine(cursor.getString(index));
            //... do something with data
        }


        /**************** esempio Preferito ***************/
        String queryPreferiti = "select * from preferiti limit 1";
        Cursor cursor2 = dbm.query(queryPreferiti, null);
        Preferito preferito = new Preferito();

        while(cursor2 != null && cursor2.moveToNext()) {
            int index = cursor2.getColumnIndexOrThrow("id_scuola");
            preferito.setId_scuola(cursor2.getString(index));

            index = cursor2.getColumnIndexOrThrow("data_inserimento");
            preferito.setData_inserimento(cursor2.getString(index));

            index = cursor2.getColumnIndexOrThrow("descrizione");
            preferito.setDescrizione(cursor2.getString(index));
            Log.i("pref",preferito.getId_scuola());
        }


        /**************** esempio Vincoli ***************/
        String queryVincoli = "select * from vincoli where id_scuola='CTPM01000E' and id_edificio='0870330784'";
        Cursor cursor3 = dbm.query(queryVincoli, null);
        Vincolo vincolo = new Vincolo();

        while(cursor3 != null && cursor3.moveToNext()) {
            int index = cursor3.getColumnIndexOrThrow("id_scuola");
            vincolo.setId_scuola(cursor3.getString(index));

            index = cursor3.getColumnIndexOrThrow("id_edificio");
            vincolo.setId_edificio(cursor3.getString(index));

            index = cursor3.getColumnIndexOrThrow("vincoli_idrogeologici");
            vincolo.setVincoli_idrogeologici(cursor3.getString(index));

            // ecc
        }

        /*******************************************/


        setContentView(R.layout.activity_benvenuto);
        Button b1 = (Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),
                        Home.class
                );

                startActivity(intent);
            }
        });
    }
}
