package com.example.alessio.safeschool;

import android.app.backup.FileBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    final static String fileName = "apertura.txt";
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/apertura";
    //final static String path = R.values.array.xml;
    final static String TAG = FileBackupHelper.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.cappellosmall);
        setContentView(R.layout.activity_main);

        String MY_FILE_NAME = "apertura.txt";
// Create a new output file stream

// Create a new file input stream.
        try {
            FileOutputStream fileos = openFileOutput(MY_FILE_NAME, Context.MODE_PRIVATE);
            FileInputStream fileis = openFileInput(MY_FILE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Read();
        Write();



       /* InputStream is = null;
        Context context = getApplicationContext();
        try {
            is = context.getAssets().open("apertura.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text = "";
        int size = 0;
        try {
            size = is.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[size];
        try {
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        text = new String(buffer);
        int i=Integer.parseInt(text);

        if (i == 1) {
            FileOutputStream fileout = null;
            try {
                fileout = openFileOutput("apertura.txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                outputWriter.write("0");
                outputWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        }
*/


        Button b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        Tutorial.class
                );

                startActivity(intent);
            }
        });
    }

    public void Read(){
        final int READ_BLOCK_SIZE = 100;
        try {
            FileInputStream fileIn=openFileInput("apertura.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Write(){
        try {
            FileOutputStream fileout=openFileOutput("apertura.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write("0");
            Log.i("scritto","scritto");
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}