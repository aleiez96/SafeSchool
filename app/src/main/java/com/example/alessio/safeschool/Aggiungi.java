package com.example.alessio.safeschool;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

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
    ArrayList<String> grado=new ArrayList<>();
    ArrayList<String> lstFound = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi);
        setTitle("Sfoglia");
        aggiungi();
        grado.clear();
        province.clear();
        vincoli.clear();

        lstView = (ListView)findViewById(R.id.lstView);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lstSource2);
        lstView.setAdapter(adapter);



        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }



            @Override
            public void onSearchViewClosed() {
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
                newText = newText.toUpperCase();
                if(newText != null && !newText.isEmpty()){
                    lstView.clearDisappearingChildren();
                    String item;
                    lstFound.clear();
                    id_s=new ArrayList<>();
                    Scuola s;
                    for( int i=0;i<lstSource.size() ;i++){
                        item=lstSource.get(i).getNome();
                        if(item.contains(newText)) {
                            id_s.add(lstSource.get(i).getId());
                            lstFound.add(lstSource.get(i).getId()+" - "+item);
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
                int i=0;
                if(id_s!=null){
                    while (!(lstFound.get(position).equals(lstSource.get(i).getId()+" - "+lstSource.get(i).getNome()))) {
                        i++;
                    }
                    Intent intent=new Intent(getApplicationContext(), ScrollingActivityScuola.class);
                    intent.putExtra("Codicescuola", lstSource.get(i).getId());
                    startActivity(intent);}
                else {
                    while (lstSource.get(i).getNome().equals("SCUOLA INFANZIA") || !(lstSource2.get(position).contains(lstSource.get(i).getNome()))) {
                        i++;}
                    Intent intent=new Intent(getApplicationContext(), ScrollingActivityScuola.class);
                    intent.putExtra("Codicescuola", lstSource.get(i).getId());
                    startActivity(intent);
                }
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
            case R.id.filtri:
                invalidateOptionsMenu();
                province.clear();
                grado.clear();
                vincoli.clear();
                queryfiltra();
                break;
            case R.id.infanzia:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=grado.indexOf((Object) "SCUOLA INFANZIA");
                    grado.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    grado.add("SCUOLA INFANZIA");
                    queryfiltra();
                }
                break;
            case R.id.primaria:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=grado.indexOf((Object) "SCUOLA PRIMARIA");
                    grado.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    grado.add("SCUOLA PRIMARIA");
                    queryfiltra();
                }
                break;
            case R.id.primo:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=grado.indexOf((Object) "SCUOLA PRIMO GRADO");
                    grado.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    grado.add("SCUOLA PRIMO GRADO");
                    queryfiltra();
                }
                break;
            case R.id.compr:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=grado.indexOf((Object) "ISTITUTO COMPRENSIVO");
                    grado.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    grado.add("ISTITUTO COMPRENSIVO");
                    queryfiltra();
                }
                break;
            case R.id.licei:
                if(item.isChecked()) {
                    item.setChecked(false);
                    int i;
                    i=grado.indexOf((Object) "ALTRO");
                    grado.remove(i);
                    queryfiltra();
                }
                else {
                    item.setChecked(true);
                    grado.add("ALTRO");
                    queryfiltra();
                }
                break;
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
        if((province.isEmpty()||province.size()==7)&&(vincoli.isEmpty())&&(grado.isEmpty()||grado.size()==4)){
            String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola";
            Cursor cursor = dbm.query(query, null);
            inserimento(cursor);
        }
        else{
            if (vincoli.isEmpty()&&(grado.isEmpty())) {
                for (String provincia : province) {
                    String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.provincia=?";
                    Cursor cursor = dbm.query(query, new String[]{provincia});
                    inserimento(cursor);
                }
            }
            else{
                if (vincoli.isEmpty()&&(province.isEmpty())) {
                    for (String grado : grado) {
                        if (grado.equals("ALTRO")){
                            String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione<>'SCUOLA INFANZIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMARIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMO GRADO' and scuole.tipologia_grado_istruzione<>'ISTITUTO COMPRENSIVO'";
                            Cursor cursor = dbm.query(query, null);
                            inserimento(cursor);
                        }
                        else{
                            String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione=?";
                            Cursor cursor = dbm.query(query, new String[]{grado});
                            inserimento(cursor);
                        }
                    }
                }
                else {
                    if ((province.isEmpty()) && (grado.isEmpty())) {
                        switch (vincoli.size()) {
                            case 1:
                                String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where vincoli." + vincoli.get(0) + "='SI'";
                                Cursor cursor = dbm.query(query, null);
                                inserimento(cursor);
                                break;
                            case 2:
                                query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI'";
                                cursor = dbm.query(query, null);
                                inserimento(cursor);
                                break;
                            case 3:
                                query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI'";
                                cursor = dbm.query(query, null);
                                inserimento(cursor);
                                break;
                            case 4:
                                query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI' and vincoli." + vincoli.get(3) + "='SI'";
                                cursor = dbm.query(query, null);
                                inserimento(cursor);
                                break;
                        }
                    }
                    else {
                        if (vincoli.isEmpty()) {
                            for (String provincia : province) {
                                for (String grado : grado) {
                                    if (grado.equals("ALTRO")){
                                        String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.provincia=? and scuole.tipologia_grado_istruzione<>'SCUOLA INFANZIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMARIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMO GRADO' and scuole.tipologia_grado_istruzione<>'ISTITUTO COMPRENSIVO'";
                                        Cursor cursor = dbm.query(query, new String[]{provincia});
                                        inserimento(cursor);
                                    }
                                    else {
                                        String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.provincia=? and scuole.tipologia_grado_istruzione=?";
                                        Cursor cursor = dbm.query(query, new String[]{provincia, grado});
                                        inserimento(cursor);
                                    }
                                }
                            }
                        }
                        if (province.isEmpty()) {
                            for (String grado : grado) {
                                if (grado.equals("ALTRO")){
                                    switch (vincoli.size()) {
                                        case 1:
                                            String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione<>'SCUOLA INFANZIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMARIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMO GRADO' and scuole.tipologia_grado_istruzione<>'ISTITUTO COMPRENSIVO' and vincoli." + vincoli.get(0) + "='SI'";
                                            Cursor cursor = dbm.query(query, null);
                                            inserimento(cursor);
                                            break;
                                        case 2:
                                            query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione<>'SCUOLA INFANZIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMARIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMO GRADO' and scuole.tipologia_grado_istruzione<>'ISTITUTO COMPRENSIVO' and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI'";
                                            cursor = dbm.query(query, null);
                                            inserimento(cursor);
                                            break;
                                        case 3:
                                            query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione<>'SCUOLA INFANZIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMARIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMO GRADO' and scuole.tipologia_grado_istruzione<>'ISTITUTO COMPRENSIVO' and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI'";
                                            cursor = dbm.query(query, null);
                                            inserimento(cursor);
                                            break;
                                        case 4:
                                            query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione<>'SCUOLA INFANZIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMARIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMO GRADO' and scuole.tipologia_grado_istruzione<>'ISTITUTO COMPRENSIVO' and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI' and vincoli." + vincoli.get(3) + "='SI'";
                                            cursor = dbm.query(query, null);
                                            inserimento(cursor);
                                            break;
                                    }
                                }
                                else {
                                    switch (vincoli.size()) {
                                        case 1:
                                            String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione=? and vincoli." + vincoli.get(0) + "='SI'";
                                            Cursor cursor = dbm.query(query, new String[]{grado});
                                            inserimento(cursor);
                                            break;
                                        case 2:
                                            query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI'";
                                            cursor = dbm.query(query, new String[]{grado});
                                            inserimento(cursor);
                                            break;
                                        case 3:
                                            query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI'";
                                            cursor = dbm.query(query, new String[]{grado});
                                            inserimento(cursor);
                                            break;
                                        case 4:
                                            query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI' and vincoli." + vincoli.get(3) + "='SI'";
                                            cursor = dbm.query(query, new String[]{grado});
                                            inserimento(cursor);
                                            break;
                                    }
                                }
                            }
                        }
                        if (grado.isEmpty()) {
                            switch (vincoli.size()) {
                                case 1:
                                    for (String provincia : province) {
                                        String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.provincia=? and vincoli." + vincoli.get(0) + "='SI'";
                                        Cursor cursor = dbm.query(query, new String[]{provincia});
                                        inserimento(cursor);
                                    }
                                    break;
                                case 2:
                                    for (String provincia : province) {
                                        String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.provincia=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI'";
                                        Cursor cursor = dbm.query(query, new String[]{provincia});
                                        inserimento(cursor);
                                    }
                                    break;
                                case 3:
                                    for (String provincia : province) {
                                        String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.provincia=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI'";
                                        Cursor cursor = dbm.query(query, new String[]{provincia});
                                        inserimento(cursor);
                                    }
                                    break;
                                case 4:
                                    for (String provincia : province) {
                                        String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.provincia=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI' and vincoli." + vincoli.get(3) + "='SI'";
                                        Cursor cursor = dbm.query(query, new String[]{provincia});
                                        inserimento(cursor);
                                    }
                                    break;
                            }
                        }
                        else {
                            for (String grado : grado) {
                                if (grado.equals("ALTRO")){
                                    switch (vincoli.size()) {
                                        case 1:
                                            for (String provincia : province) {
                                                String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione<>'SCUOLA INFANZIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMARIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMO GRADO' and scuole.tipologia_grado_istruzione<>'ISTITUTO COMPRENSIVO' and scuole.provincia=? and vincoli." + vincoli.get(0) + "='SI'";
                                                Cursor cursor = dbm.query(query, new String[]{provincia});
                                                inserimento(cursor);
                                            }
                                            break;
                                        case 2:
                                            for (String provincia : province) {
                                                String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione<>'SCUOLA INFANZIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMARIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMO GRADO' and scuole.tipologia_grado_istruzione<>'ISTITUTO COMPRENSIVO' and scuole.provincia=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI'";
                                                Cursor cursor = dbm.query(query, new String[]{provincia});
                                                inserimento(cursor);
                                            }
                                            break;
                                        case 3:
                                            for (String provincia : province) {
                                                String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione<>'SCUOLA INFANZIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMARIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMO GRADO' and scuole.tipologia_grado_istruzione<>'ISTITUTO COMPRENSIVO' and scuole.provincia=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI'";
                                                Cursor cursor = dbm.query(query, new String[]{provincia});
                                                inserimento(cursor);
                                            }
                                            break;
                                        case 4:
                                            for (String provincia : province) {
                                                String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.tipologia_grado_istruzione<>'SCUOLA INFANZIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMARIA' and scuole.tipologia_grado_istruzione<>'SCUOLA PRIMO GRADO' and scuole.tipologia_grado_istruzione<>'ISTITUTO COMPRENSIVO' and scuole.provincia=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI' and vincoli." + vincoli.get(3) + "='SI'";
                                                Cursor cursor = dbm.query(query, new String[]{provincia});
                                                inserimento(cursor);
                                            }
                                            break;
                                    }
                                }
                                else {
                                    switch (vincoli.size()) {
                                        case 1:
                                            for (String provincia : province) {
                                                String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.provincia=? and scuole.tipologia_grado_istruzione=? and vincoli." + vincoli.get(0) + "='SI'";
                                                Cursor cursor = dbm.query(query, new String[]{provincia, grado});
                                                inserimento(cursor);
                                            }
                                            break;
                                        case 2:
                                            for (String provincia : province) {
                                                String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.provincia=? and scuole.tipologia_grado_istruzione=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI'";
                                                Cursor cursor = dbm.query(query, new String[]{provincia, grado});
                                                inserimento(cursor);
                                            }
                                            break;
                                        case 3:
                                            for (String provincia : province) {
                                                String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.provincia=? and scuole.tipologia_grado_istruzione=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI'";
                                                Cursor cursor = dbm.query(query, new String[]{provincia, grado});
                                                inserimento(cursor);
                                            }
                                            break;
                                        case 4:
                                            for (String provincia : province) {
                                                String query = "select * from scuole inner join vincoli on scuole.id=vincoli.id_scuola where scuole.provincia=? and scuole.tipologia_grado_istruzione=? and vincoli." + vincoli.get(0) + "='SI' and vincoli." + vincoli.get(1) + "='SI' and vincoli." + vincoli.get(2) + "='SI' and vincoli." + vincoli.get(3) + "='SI'";
                                                Cursor cursor = dbm.query(query, new String[]{provincia, grado});
                                                inserimento(cursor);
                                            }
                                            break;
                                    }
                                }
                            }

                        }
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

            Scuola s=new Scuola(nome,id);
            lstSource.add(s);
            lstSource2.add(id+" - "+nome);
        }
        HashSet<String> hashSet = new HashSet<String>();
        HashSet<Scuola> hashSet2 = new HashSet<Scuola>();
        hashSet.addAll(lstSource2);
        lstSource2.clear();
        lstSource2.addAll(hashSet);
        hashSet2.addAll(lstSource);
        lstSource.clear();
        lstSource.addAll(hashSet2);

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
            lstSource2.add(id+" - "+nome);


        }

    }

}
