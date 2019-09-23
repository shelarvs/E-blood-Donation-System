package com.phntechnology.e_blood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Confirm extends AppCompatActivity {
    EditText et;
    ListView lv;
    Button bn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        et=(EditText)findViewById(R.id.et);
        lv=(ListView)findViewById(R.id.lv);
        bn=(Button)findViewById(R.id.bn);

    }
}
