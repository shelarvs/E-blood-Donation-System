package com.phntechnology.e_blood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class Search_Result extends AppCompatActivity {
    TextView tv;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__result);



        tv=(TextView)findViewById(R.id.tv);
        lv=(ListView)findViewById(R.id.lv);


    }
}
