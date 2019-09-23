package com.phntechnology.e_blood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Get_IP extends AppCompatActivity {

    EditText ip;
    Button click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__ip);


        ip=(EditText) findViewById(R.id.editText8);
        click=(Button)findViewById(R.id.button2);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ip.getText().toString().equals(""))
                {
                    ip.setError("ENter IP Address");
                }
                else
                {
                    weburl.url="http://"+ip.getText().toString()+"/projects/blood/";
                    Intent i=new Intent(getApplicationContext(),Welcome.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
