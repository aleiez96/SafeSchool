package com.example.alessio.safeschool;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.support.coreutils.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityInfo extends AppCompatActivity {

    public static String credits(Context ctx) {
        ApplicationInfo ai = ctx.getApplicationInfo();
        StringBuffer buf = new StringBuffer();
        buf.append("\tVERSION.RELEASE {").append(Build.VERSION.RELEASE).append("}");
        buf.append("\n\tVERSION.INCREMENTAL {").append(Build.VERSION.INCREMENTAL).append("}");
        buf.append("\n\tVERSION.SDK {").append(Build.VERSION.SDK_INT).append("}");
        buf.append("\n\tBOARD {").append(Build.BOARD).append("}");
        buf.append("\n\tBRAND {").append(Build.BRAND).append("}");
        buf.append("\n\tDEVICE {").append(Build.DEVICE).append("}");
        buf.append("\n\tFINGERPRINT {").append(Build.FINGERPRINT).append("}");
        buf.append("\n\tHOST {").append(Build.HOST).append("}");
        buf.append("\n\tID {").append(Build.ID).append("}");
        return String.format(
                "---SVILUPPATA DA---\n" +
                        "SAM_TEAM:\n" +
                        "Massimiliano Gonella\n" +
                        "Alessio Iezzi\n" +
                        "Filippo Ormitti\n" +
                        "Silvia Zottin\n" +
                        "Corso di laurea in Informatica Università Fa' Foscari \n\n" +
                        "--- APP ---\n" +
                        "%s v%s [%s]\n" +
                        "(c) %s %s @ %s - %s \n\n" +
                        "--- ANDROID ---\n%s",
                ctx.getString(ai.labelRes),
                android.support.coreutils.BuildConfig.VERSION_NAME,
                android.support.coreutils.BuildConfig.BUILD_TYPE,
                R.string.credits_year, R.string.credits_project, R.string.credits_company, R.string.credits_authors,
                buf);
    }

    /**
     * Metodo di creazione dell'activity che imposta il layout e la text view con la stringa con i crediti.
     * @param saveInstanceState
     */
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_info);
        TextView tv_1 = (TextView) findViewById(R.id.textView_1);
        tv_1.setText(credits(this));
    }

}

