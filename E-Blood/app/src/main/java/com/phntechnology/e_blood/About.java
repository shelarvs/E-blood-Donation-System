package com.phntechnology.e_blood;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class About extends AppCompatActivity {

    TextView about;
    SQLiteDatabase db;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        about=(TextView)findViewById(R.id.textView8);
        db=openOrCreateDatabase("blood", Context.MODE_PRIVATE,null);
        db.execSQL("create table if not exists login (Username varchar,Password varchar)");
        about.setText("");
        try {

            Cursor c = db.rawQuery("select * from login", null);
            if (c.moveToFirst()) {
                url=c.getString(0);
                url=url+"\n"+c.getString(1);
            }
        }
        catch (Exception ex)
        {

        }
        about.setText(url);

    }
}
