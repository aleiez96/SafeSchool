package com.example.alessio.safeschool;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class pref extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{

    ArrayList<String> nameproducts = new ArrayList<>();
    ArrayList<String> preferiti = new ArrayList<>();


    private DataBaseHelper mDBHelper;
    private DbManager dbm;
    private SQLiteDatabase mDb;

    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);



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

        String queryPreferiti = "select * from scuole_veneto inner join preferiti on scuole_veneto.id=preferiti.id_scuola";
        Cursor cursor2 = dbm.query(queryPreferiti, null);
        Preferito preferito = new Preferito();

        while(cursor2 != null && cursor2.moveToNext()) {
            int index = cursor2.getColumnIndexOrThrow("nome");
            String nome=cursor2.getString(index);

            index = cursor2.getColumnIndexOrThrow("id_scuola");
            preferito.setId_scuola(cursor2.getString(index));

            index = cursor2.getColumnIndexOrThrow("data_inserimento");
            preferito.setData_inserimento(cursor2.getString(index));

            index = cursor2.getColumnIndexOrThrow("descrizione");
            preferito.setDescrizione(cursor2.getString(index));
            Log.i("pref",preferito.getId_scuola());
            Log.i("nome",nome);
            nameproducts.add(nome);
            preferiti.add(preferito.getId_scuola());
        }



    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.pref_menu, menu);
        //getMenuInflater().inflate(R.menu.refresh, menu);
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

                    intent.putExtra("Codicescuola", preferiti.get(position));
                    startActivity(intent);
                }
            });
            return true;
        }else
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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


    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(getApplicationContext(),
                pref.class
        );
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}

