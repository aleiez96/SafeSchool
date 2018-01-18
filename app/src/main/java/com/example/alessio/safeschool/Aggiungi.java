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
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.alessio.safeschool.MapsActivity.mDBHelper;

public class Aggiungi extends AppCompatActivity {

    static DataBaseHelper mDBHelper;
    static DbManager dbm;
    static SQLiteDatabase mDb;

    MaterialSearchView searchView;
    ListView lstView;
    ArrayList<Scuole> lstSource=new ArrayList<>();
    ArrayList<String> lstSource2=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi);
        aggiungi();
       /* Button b1 = (Button)findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener().OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),
                        ScrollingActivityScuola.class
                );

                startActivity(intent);
            }
        });
*/
        /*Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Material Search");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));*/

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
                    String item;
                    List<String> lstFound = new ArrayList<String>();
                    for( int i=0;i<lstSource.size() ;i++){
                        item=lstSource.get(i).getNome();
                        Log.i("nome",item);
                        if(item.contains(newText))
                            lstFound.add(item);

                    }

                    ArrayAdapter adapter = new ArrayAdapter(Aggiungi.this,android.R.layout.simple_list_item_1,lstFound);
                    lstView.setAdapter(adapter);
                }
                else{
                    //if search text is null
                    //return default
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

                intent.putExtra("Codicescuola",lstSource.get(position).getId());
                Log.i("id",lstSource.get(position).getId());
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

        int id=item.getItemId();
        if(item.isChecked())
            item.setChecked(false);
        else
            item.setChecked(true);


        switch (id) {
            case R.id.action_search:
                return true;
            case R.id.checkable_item1:
                Toast.makeText(this, "Filtro regione", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.checkable_item2:
                Toast.makeText(this, "Filtro provincia", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.p1:
                Toast.makeText(this, "Filtro pericolosità", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.p2:
                Toast.makeText(this, "Filtro pericolosità", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void aggiungi(){
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

        String query = "select * from scuole_veneto";
        Cursor cursor = dbm.query(query, null);

        while(cursor.moveToNext()) {
            int index;
            index = cursor.getColumnIndexOrThrow("id");
            String id = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("nome");
            String nome = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("provincia");
            String provincia = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("longitudine");
            String longitudine = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("sito_web");
            String sito = cursor.getString(index);

            Scuole s=new Scuole(nome,id);
            lstSource.add(s);
            lstSource2.add(nome);


        }
        /*******************************************/
    }

}
