package com.example.alessio.safeschool;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        String query = "select * from scuole";
        Cursor cursor = dbm.query(query, null);

        while(cursor.moveToNext()) {
            int index;
            index = cursor.getColumnIndexOrThrow("id");
            String id = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("nome");
            String nome = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow("provincia");
            String provincia = cursor.getString(index);

            //... do something with data
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
