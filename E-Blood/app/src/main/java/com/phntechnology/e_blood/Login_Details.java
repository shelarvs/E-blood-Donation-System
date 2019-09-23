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
import android.widget.TextView;
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

public class Login_Details extends AppCompatActivity {

    private ProgressDialog progress;
    final Context context=this;

    public static String receivedValue="";
    SQLiteDatabase db;
    EditText et1,et2,et3;
    Button bn;
    public static String uname,pwd,cpwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__details);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText)findViewById(R.id.et3);
        bn=(Button)findViewById(R.id.bn);
        db=openOrCreateDatabase("blood", Context.MODE_PRIVATE,null);

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname=et1.getText().toString();
                pwd=et2.getText().toString();
                cpwd=et3.getText().toString();
                if(uname.equals(""))
                {
                    et1.setError("Enter Username");
                }
                else if(pwd.equals(""))
                {
                    et2.setError("Enter Password");
                }
                else if(cpwd.equals(""))
                {
                    et3.setError("Enter Password");
                }
                else if(!pwd.equals(cpwd))
                {
                    et3.setError("Password not matched");
                }
                else
                {

                    try {
                        progress=new ProgressDialog(context);
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
             if(receivedValue.contains("exists"))
            {
                Toast.makeText(getApplicationContext(), "Username Already Exists", Toast.LENGTH_SHORT).show();
            }
            else {
                db.execSQL("insert into login values ('" + uname + "','" + pwd + "')");
                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                Welcome.uname=uname;
                Intent i = new Intent(getApplicationContext(), Panel.class);
                startActivity(i);

                finish();
            }
            progress.dismiss();

            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... params) {


            HttpClient client=new DefaultHttpClient();
            HttpPost post=new HttpPost(weburl.url+"/user-register.php");
            //temp=params[0];
            List<NameValuePair> pairs=new ArrayList<NameValuePair>(1);
            pairs.add(new BasicNameValuePair("e1",Register.sname));
            pairs.add(new BasicNameValuePair("e2",Register.smobile));
            pairs.add(new BasicNameValuePair("e3",Register.salternative));
            pairs.add(new BasicNameValuePair("e4",Register.slandline));
            pairs.add(new BasicNameValuePair("e5",Register.sadd));
            pairs.add(new BasicNameValuePair("e6",Register.scity));
            pairs.add(new BasicNameValuePair("e7",Register.spin));
            pairs.add(new BasicNameValuePair("e8",uname));
            pairs.add(new BasicNameValuePair("e9",pwd));

            try
            {
                post.setEntity(new UrlEncodedFormEntity(pairs));
            }
            catch (Exception ex)
            {
                //  Toast.makeText(getApplicationContext(), "Error 1="+ex.toString(), Toast.LENGTH_SHORT).show();
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

                // Toast.makeText(getApplicationContext(), "Error 2="+ex.toString(), Toast.LENGTH_SHORT).show();
            }



            return null;
        }
    }
}
