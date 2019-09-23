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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Update_Profile extends AppCompatActivity {

    EditText name,mobile,alternative,landline,add,city,pin;
    Button update;
    Integer integer1,integer2,integer3;
    Integer integer;
    String sname,smobile,salternative,slandline,sadd,scity,spin;
SQLiteDatabase db;
    public static  String uname="",oldpwd="";
    String myJSON;
    JSONArray peoples = null;
    private static final String TAG_RESULTS="result";
    ArrayList<HashMap<String, String>> personList;
    private ProgressDialog progress;
    final Context context=this;
    public static String receivedValue="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__profile);
        db=openOrCreateDatabase("blood", Context.MODE_PRIVATE,null);


        name=(EditText)findViewById(R.id.editText) ;
        mobile=(EditText)findViewById(R.id.editText2) ;
        alternative=(EditText)findViewById(R.id.editText3) ;
        landline=(EditText)findViewById(R.id.editText4) ;
        add=(EditText)findViewById(R.id.editText5) ;
        city=(EditText)findViewById(R.id.editText6) ;
        pin=(EditText)findViewById(R.id.editText7) ;
        update=(Button)findViewById(R.id.button4);

         getData();

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
                else if(salternative.equals(""))
                {
                    alternative.setError("Enter Mobile Number");
                }
                else if(integer<10||integer>10)
                {
                    alternative.setError("Enter 10 digits");
                }
                else if(slandline.equals(""))
                {
                    landline.setError("Enter Landline Number");
                }
                else if(integer2<8||integer2>8)
                {
                    landline.setError("Enter 8 digits");
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
                 Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
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
            HttpPost post=new HttpPost(weburl.url+"/user-update.php");
            //temp=params[0];
            List<NameValuePair> pairs=new ArrayList<NameValuePair>(1);
            pairs.add(new BasicNameValuePair("e1",sname));
            pairs.add(new BasicNameValuePair("e2",smobile));
            pairs.add(new BasicNameValuePair("e3",salternative));
            pairs.add(new BasicNameValuePair("e4",slandline));
            pairs.add(new BasicNameValuePair("e5",sadd));
            pairs.add(new BasicNameValuePair("e6",scity));
            pairs.add(new BasicNameValuePair("e7",spin));
            pairs.add(new BasicNameValuePair("e8",Welcome.uname));

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

    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost(weburl.url+"/get-user-profile.php?uname="+Welcome.uname);

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }
    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                name.setText( c.getString("v1"));
                mobile.setText( c.getString("v2"));
                alternative.setText( c.getString("v3"));
                landline.setText( c.getString("v4"));
                add.setText( c.getString("v5"));
                city.setText( c.getString("v6"));
                pin.setText( c.getString("v7"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
