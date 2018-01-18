package com.example.alessio.safeschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class pref extends AppCompatActivity {

    String[] nameproducts = new String[] { "PDEE89502B", "TVIC87900C", "TVEE87902G" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.pref_menu, menu);

        final ArrayList<String> listp = new ArrayList<String>();
        for (int i = 0; i < nameproducts.length; ++i) {
            listp.add(nameproducts[i]);
        }

        final ListView mylist = (ListView) findViewById(R.id.listView1);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_list_item_1, listp);
        mylist.setAdapter(adapter);



        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),
                        ScrollingActivityScuola.class
                );

                intent.putExtra("Codicescuola","TVEE87902G");
                startActivity(intent);
            }
        });
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
