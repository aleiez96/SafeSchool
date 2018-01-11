package com.example.alessio.safeschool;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.unive.dais.cevid.datadroid.lib.parser.CsvRowParser;

public class Home extends AppCompatActivity {
    static ProgressBar pb;
    static List<CsvRowParser.Row> rows;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myAsyncp mTask = new myAsyncp();
        mTask.execute();
        pb = findViewById(R.id.progressBar2);
        Button b1 = findViewById(R.id.preferiti);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),
                        pref.class
                );

                startActivity(intent);
            }
        });
        Button b2 = findViewById(R.id.mappa);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),
                        MapsActivity.class
                );
                startActivity(intent);
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
        // Take appropriate action for each action item click
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

    public class myAsyncp extends AsyncTask<Void,Integer,Void> {
        TextView output = (TextView) findViewById(R.id.textView7);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            runOnUiThread(new Thread() {
                public void run() {
                    output.setText("Parsing dei dati...");
                }
            });
            try {
                InputStream is = getResources().openRawResource(R.raw.veneto_sempl);
                CsvRowParser p = new CsvRowParser(new InputStreamReader(is), true, ";");
                rows = p.getAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void a) {
            output.setText("Fine!");
            pb.setVisibility(findViewById(R.id.progressBar2).INVISIBLE);
            output.setVisibility(findViewById(R.id.textView7).INVISIBLE);
        }

    }

}
