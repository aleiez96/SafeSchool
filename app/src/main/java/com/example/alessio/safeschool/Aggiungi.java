package com.example.alessio.safeschool;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Aggiungi extends AppCompatActivity{

    static DataBaseHelper mDBHelper;
    static DbManager dbm;
    static SQLiteDatabase mDb;

    MaterialSearchView searchView;
    ListView lstView;
    ArrayList<Scuola> lstSource=new ArrayList<>();
    ArrayList<String> lstSource2=new ArrayList<>();
    ArrayList<String> id_s=null;
    ArrayList<String> vincoli=new ArrayList<>();
    ArrayList<String> province=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi);
        aggiungi();

        lstView = (ListView)findViewById(R.id.lstView);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lstSource2);
        lstView.setAdapter(adapter);



        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        Log.i("nome","ok 1");
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }



            @Override
            public void onSearchViewClosed() {
                //If closed Search View , lstView will return default
                lstView = (ListView)findViewById(R.id.lstView);

                ArrayAdapter adapter = new ArrayAdapter(Aggiungi.this,android.R.layout.simple_list_item_1,lstSource2);
                lstView.setAdapter(adapter);

            }
        });



        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText != null && !newText.isEmpty()){
                    lstView.clearDisappearingChildren();
                    String item;
                    ArrayList<String> lstFound = new ArrayList<String>();
                    id_s=new ArrayList<>();
                    Scuola s;
                    for( int i=0;i<lstSource.size() ;i++){
                        item=lstSource.get(i).getNome();
                        Log.i("nome",item);
                        if(item.contains(newText)) {
                            id_s.add(lstSource.get(i).getId());
                            lstFound.add(item);
                        }

                    }
                    HashSet<String> hashSet = new HashSet<String>();
                    hashSet.addAll(lstFound);
                    lstFound.clear();
                    lstFound.addAll(hashSet);

                    ArrayAdapter adapter = new ArrayAdapter(Aggiungi.this,android.R.layout.simple_list_item_1,lstFound);
                    lstView.setAdapter(adapter);
                }
                else{
                    //if search text is null
                    //return default
                    id_s=null;
                    ArrayAdapter adapter = new ArrayAdapter(Aggiungi.this,android.R.layout.simple_list_item_1,lstSource2);
                    lstView.setAdapter(adapter);
                }

                return true;
            }

        });


        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),
                        ScrollingActivityScuola.class
                );
                String.valueOf(parent.getItemIdAtPosition(position));
                if(id_s!=null)
                    intent.putExtra("Codicescuola",id_s.get(position));
                else
                    intent.putExtra("Codicescuola",lstSource.get(position).getId());
                //Log.i("aggiungi activity", String.valueOf(parent.getItemAtPosition(position)));
                //Log.i("aggiungi activity",id_s.get(position) );

                startActivity(intent);
            }
        });



    }



    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.aggiungi_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.p1:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=vincoli.indexOf((Object) "vincoli_idrogeologici");
                    vincoli.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    vincoli.add("vincoli_idrogeologici");
                    queryfiltra();
                }
                break;
            case R.id.p2:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=vincoli.indexOf((Object) "vincoli_paesaggio");
                    vincoli.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    vincoli.add("vincoli_paesaggio");
                    queryfiltra();
                }
                break;
            case R.id.p3:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=vincoli.indexOf((Object) "edificio_vetusto");
                    vincoli.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    vincoli.add("edificio_vetusto");
                    queryfiltra();
                }
                break;
            case R.id.p4:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=vincoli.indexOf((Object) "progettazione_antisismica");
                    vincoli.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    vincoli.add("progettazione_antisismica");
                    queryfiltra();
                }
                break;
            case R.id.tv:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=province.indexOf((Object) item.getTitle());
                    province.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    province.add((String)item.getTitle());
                    queryfiltra();
                }
                break;
            case R.id.pd:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=province.indexOf((Object) item.getTitle());
                    province.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    province.add((String)item.getTitle());
                    queryfiltra();
                }
                break;
            case R.id.vi:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=province.indexOf((Object) item.getTitle());
                    province.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    province.add((String)item.getTitle());
                    queryfiltra();
                }
                break;
            case R.id.vr:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=province.indexOf((Object) item.getTitle());
                    province.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    province.add((String)item.getTitle());
                    queryfiltra();
                }
                break;
            case R.id.bl:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=province.indexOf((Object) item.getTitle());
                    province.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    province.add((String)item.getTitle());
                    queryfiltra();
                }
                break;
            case R.id.rg:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=province.indexOf((Object) item.getTitle());
                    province.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    province.add((String)item.getTitle());
                    queryfiltra();
                }
                break;
            case R.id.ve:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=province.indexOf((Object) item.getTitle());
                    province.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    province.add((String)item.getTitle());
                    queryfiltra();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    public void queryfiltra (){
        if((province.isEmpty()||province.size()==7)&&(vincoli.isEmpty())){
            String query = "select * from scuole_veneto inner join vincoli on scuole_veneto.id=vincoli.id_scuola";
            Cursor cursor = dbm.query(query, null);
            inserimento(cursor);
        }
        else{
            if (vincoli.isEmpty()) {
                for (String provincia : province) {
                    String query = "select * from scuole_veneto inner join vincoli on scuole_veneto.id=vincoli.id_scuola where scuole_veneto.provincia=?";
                    Cursor cursor = dbm.query(query, new String[]{provincia});
                    inserimento(cursor);
                }
            }
            else{
                if (province.isEmpty()){
                    switch (vincoli.size()){
                        case 1:
                            String query = "select * from scuole_veneto inner join vincoli on scuole_veneto.id=vincoli.id_scuola where vincoli." + vincoli.get(0) + "='SI'";
                            Cursor cursor = dbm.query(query, null);
                            inserimento(cursor);
                            break;
                        case 2:
                            query = "select * from scuole_veneto inner join vincoli on scuole_veneto.id=vincoli.id_scuola where vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI'";
                            cursor = dbm.query(query, null);
                            inserimento(cursor);
                            break;
                        case 3:
                            query = "select * from scuole_veneto inner join vincoli on scuole_veneto.id=vincoli.id_scuola where vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI'";
                            cursor = dbm.query(query, null);
                            inserimento(cursor);
                            break;
                        case 4:
                            query = "select * from scuole_veneto inner join vincoli on scuole_veneto.id=vincoli.id_scuola where vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI' and vincoli." + vincoli.get(3) + "='SI'";
                            cursor = dbm.query(query, null);
                            inserimento(cursor);
                            break;
                    }
                }
                else {
                    switch (vincoli.size()){
                        case 1:
                            for (String provincia : province) {
                                String query = "select * from scuole_veneto inner join vincoli on scuole_veneto.id=vincoli.id_scuola where scuole_veneto.provincia=? and vincoli." + vincoli.get(0) + "='SI'";
                                Cursor cursor = dbm.query(query, new String[]{provincia});
                                inserimento(cursor);
                            }
                            break;
                        case 2:
                            for (String provincia : province) {
                                String query = "select * from scuole_veneto inner join vincoli on scuole_veneto.id=vincoli.id_scuola where scuole_veneto.provincia=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI'";
                                Cursor cursor = dbm.query(query, new String[]{provincia});
                                inserimento(cursor);
                            }
                            break;
                        case 3:
                            for (String provincia : province) {
                                String query = "select * from scuole_veneto inner join vincoli on scuole_veneto.id=vincoli.id_scuola where scuole_veneto.provincia=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI'";
                                Cursor cursor = dbm.query(query, new String[]{provincia});
                                inserimento(cursor);
                            }
                            break;
                        case 4:
                            for (String provincia : province) {
                                String query = "select * from scuole_veneto inner join vincoli on scuole_veneto.id=vincoli.id_scuola where scuole_veneto.provincia=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI' and vincoli." + vincoli.get(3) + "='SI'";
                                Cursor cursor = dbm.query(query, new String[]{provincia});
                                inserimento(cursor);
                            }
                            break;
                    }

                }
            }

        }
    }

    public void inserimento (Cursor cursor){
        lstSource.clear();
        lstSource2.clear();
        while (cursor.moveToNext()) {
            int index;
            index = cursor.getColumnIndexOrThrow("id");
            String id = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("nome");
            String nome = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("tipologia_grado_istruzione");
            String grado = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("provincia");
            String provincia = cursor.getString(index);

            Scuola s=new Scuola(nome,id);
            lstSource.add(s);
            lstSource2.add(grado+" - "+nome);
        }
        ArrayAdapter adapter = new ArrayAdapter(Aggiungi.this,android.R.layout.simple_list_item_1,lstSource2);
        lstView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void aggiungi(){

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

        String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola";
        Cursor cursor = dbm.query(query, null);

        while(cursor.moveToNext()) {
            int index;
            index = cursor.getColumnIndexOrThrow("id");
            String id = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("nome");
            String nome = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("tipologia_grado_istruzione");
            String grado = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("provincia");
            String provincia = cursor.getString(index);


            Scuola s=new Scuola(nome,id);
            lstSource.add(s);
            lstSource2.add(nome+" - "+grado);


        }

    }

}
