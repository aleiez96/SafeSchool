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


        //saveToFile("1");
     /*  String s = ReadFile(MainActivity.this);
       Log.i("sssssssssssss",s);
        if (s.equals("1"))
            saveToFile("0");
        else {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        }*/



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


    public static String ReadFile(Context context) {
        String line = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return line;
    }

    public static boolean saveToFile(String data) {
        try {
            new File(path).mkdir();
            File file = new File(path + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());

            return true;
        } catch (FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return false;


    }


    public void PlayWithRawFiles() throws IOException {
        String str = "";
        StringBuffer buf = new StringBuffer();
        InputStream is = getResources().getAssets().open("apertura.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        if (is != null) {

            while ((str = reader.readLine()) != null) {
                buf.append("0\n");
                Log.i("val",buf.substring(0));
            }
        }
        is.close();


    }//


    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("apertura.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
