package com.phntechnology.e_blood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    EditText name,password;
    Button login;
    String sname,spass;

    SQLiteDatabase db;
    private ProgressDialog progress;
    final Context context=this;
    public static String receivedValue="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=(EditText)findViewById(R.id.editText);
        password=(EditText) findViewById(R.id.editText2);
        login=(Button) findViewById(R.id.button);
        db=openOrCreateDatabase("blood", Context.MODE_PRIVATE,null);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname=name.getText().toString();
                spass=password.getText().toString();
                if(sname.equals(""))
                {
                    name.setError("Enter Username");
                }
                else if(spass.equals(""))
                {
                    password.setError("Enter password");
                }
                else
                {
                    try {
                        progress = new ProgressDialog(context);
                        progress.setMessage("Wait...");
                        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progress.setIndeterminate(false);
                        progress.setProgress(0);
                        progress.setCancelable(false);
                        progress.show();
                        new backgroundProcessClass().execute("");
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(context, "Error="+ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private class backgroundProcessClass extends AsyncTask<String,Void,Void>
    {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if(receivedValue.contains("Success")) {
                Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                db.execSQL("delete from login");
                Welcome.uname=sname;
                    db.execSQL("insert into login values ('" + sname + "','" + spass + "')");
                    Intent i = new Intent(getApplicationContext(), Panel.class);
                    startActivity(i);
                    finish();
            }
            else
            {
                Toast.makeText(context, "Invalid Authentication", Toast.LENGTH_SHORT).show();
            }
            progress.dismiss();
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... params) {

            HttpClient client=new DefaultHttpClient();
            HttpPost post=new HttpPost(weburl.url+"/login.php");
            //temp=params[0];
            List<NameValuePair> pairs=new ArrayList<NameValuePair>(1);
            pairs.add(new BasicNameValuePair("e1",sname));
            pairs.add(new BasicNameValuePair("e2",spass));

            try
            {
                post.setEntity(new UrlEncodedFormEntity(pairs));
            }
            catch (Exception ex)
            {
                 Toast.makeText(getApplicationContext(), "Error 1="+ex.toString(), Toast.LENGTH_SHORT).show();
            }
            //Perform HTTP Request
            try
            {
                ResponseHandler<String> responseHandler=new BasicResponseHandler();
                receivedValue =client.execute(post,responseHandler);
                // Toast.makeText(getApplicationContext(), receivedValue, Toast.LENGTH_SHORT).show();
                //name.setText(receivedValue);
            }
            catch (Exception ex)
            {
               Toast.makeText(getApplicationContext(), "Error 2="+ex.toString(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }

}
