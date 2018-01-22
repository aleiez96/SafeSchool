package com.example.alessio.safeschool;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class pref extends AppCompatActivity {

    ArrayList<String> nameproducts = new ArrayList<String>();

    private DataBaseHelper mDBHelper;
    private DbManager dbm;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

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

        String queryPreferiti = "select * from preferiti";
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
            nameproducts.add(preferito.getId_scuola());
        }

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.pref_menu, menu);
        if(nameproducts.size()>=0) {

            final ArrayList<String> listp = new ArrayList<String>();
            for (int i = 0; i < nameproducts.size(); ++i) {
                listp.add(nameproducts.get(i));
            }

            final ListView mylist = (ListView) findViewById(R.id.listView1);
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listp);
            mylist.setAdapter(adapter);


            mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(),
                            ScrollingActivityScuola.class
                    );

                    intent.putExtra("Codicescuola", nameproducts.get(position));
                    startActivity(intent);
                }
            });
            return true;
        }else
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        Intent intent;

        if(item.isChecked())
            item.setChecked(false);
        else
            item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.tutorial_item:
                intent=new Intent(getApplicationContext(),Tutorial.class);
                startActivity(intent);
                return true;
            case R.id.info_item:
                intent=new Intent(getApplicationContext(),ActivityInfo.class);
                startActivity(intent);
            case R.id.aggiungi:
                intent=new Intent(getApplicationContext(),Aggiungi.class);
                startActivity(intent);


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
