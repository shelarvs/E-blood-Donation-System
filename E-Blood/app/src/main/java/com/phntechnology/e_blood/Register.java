package com.phntechnology.e_blood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
public class Register extends AppCompatActivity {



    EditText name,mobile,alternative,landline,add,city,pin;
    Button update;
    Integer integer1,integer2,integer3;
    Integer integer;
    public static String sname,smobile,salternative,slandline,sadd,scity,spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=(EditText)findViewById(R.id.editText) ;
        mobile=(EditText)findViewById(R.id.editText2) ;
        alternative=(EditText)findViewById(R.id.editText3) ;
        landline=(EditText)findViewById(R.id.editText4) ;
        add=(EditText)findViewById(R.id.editText5) ;
        city=(EditText)findViewById(R.id.editText6) ;
        pin=(EditText)findViewById(R.id.editText7) ;
        update=(Button)findViewById(R.id.button4);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname=name.getText().toString();
                smobile=mobile.getText().toString();
                salternative=alternative.getText().toString();
                slandline=landline.getText().toString();
                sadd=add.getText().toString();
                scity=city.getText().toString();
                spin=pin.getText().toString();

                integer1=smobile.length();
                integer=salternative.length();
                integer2=landline.length();
                integer3=pin.length();

                if(sname.equals(""))
                {
                    name.setError("Enter name");
                }
                else if(smobile.equals(""))
                {
                    mobile.setError("Enter Mobile Number");
                }
                else if(integer1<10||integer1>10)
                {
                    mobile.setError("Enter 10 digits");
                }
                else if(sadd.equals(""))
                {
                    add.setError("Enter address");
                }
                else if(scity.equals(""))
                {
                    city.setError("Enter city");
                }
                else if(spin.equals(""))
                {
                    pin.setError("Enter Pin");
                }
                else if(integer3<6||integer3>6)
                {
                    pin.setError("Enter 6 digits");
                }
                else
                {
                    Intent i=new Intent(getApplicationContext(),Login_Details.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }


}


