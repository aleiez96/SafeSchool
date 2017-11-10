package com.example.alessio.safeschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.*;


public class Benvenuto extends AppCompatActivity {

    //PackageManager myPackageManager = getPackageManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benvenuto);
       /* //let’s disable the ActivityTutorial
        myPackageManager.setComponentEnabledSetting(new ComponentName(this, Benvenuto.class), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        //let’s enable the ActivityMain
        //myPackageManager.setComponentEnabledSetting(new ComponentName(this, MainActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
*/
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
