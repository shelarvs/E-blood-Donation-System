package com.phntechnology.e_blood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Remove_Donor extends AppCompatActivity {

    public static String[] l,b;

    String eUrl;

    Spinner person;
    EditText loc,blood;
    String sloc,sblood,sperson;
    Button update,remove,donate;
    String myJSON;
    int cnt=0;
    JSONArray peoples = null;
    private static final String TAG_RESULTS="result";
    ArrayList<HashMap<String, String>> personList;
    private ProgressDialog progress;
    final Context context=this;
    public static String receivedValue="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove__donor);

        person=(Spinner)findViewById(R.id.sp);
        loc=(EditText)findViewById(R.id.location);
        blood=(EditText)findViewById(R.id.blood);

        update=(Button)findViewById(R.id.update);
        remove=(Button)findViewById(R.id.remove);
        donate=(Button)findViewById(R.id.donated);

        try {
            progress=new ProgressDialog(context);
            progress.setMessage("Wait...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(false);
            progress.setProgress(0);
            progress.setCancelable(false);
            progress.show();

        }
        catch (Exception ex)
        {
            Toast.makeText(context, "Error="+ex.toString(), Toast.LENGTH_SHORT).show();
        }
        getData();

        person.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loc.setText(l[position]);
                blood.setText(b[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sperson=person.getSelectedItem().toString();
                try {
                    progress=new ProgressDialog(context);
                    progress.setMessage("Wait...");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(false);
                    progress.setProgress(0);
                    progress.setCancelable(false);
                    progress.show();
                    new backgroundProcessClass1().execute("");
                }
                catch (Exception ex)
                {
                    Toast.makeText(context, "Error="+ex.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sloc=loc.getText().toString();
                sblood=blood.getText().toString();
                sperson=person.getSelectedItem().toString();

                if(sloc.equals(""))
                {
                    loc.setError("Enter Location");
                }
                else if(sblood.equals(""))
                {
                    blood.setError("Enter Blood");
                }
                else
                {
                    try {
                        eUrl = "donor-update.php";
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
                    catch (Exception ex)
                    {
                        Toast.makeText(context, "Error="+ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sperson=person.getSelectedItem().toString();

                eUrl="donor-delete.php";
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

                  Toast.makeText(getApplicationContext(), receivedValue, Toast.LENGTH_SHORT).show();


                finish();

            progress.dismiss();

            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... params) {


            HttpClient client=new DefaultHttpClient();
            HttpPost post=new HttpPost(weburl.url+eUrl);
            //temp=params[0];
            List<NameValuePair> pairs=new ArrayList<NameValuePair>(1);
            pairs.add(new BasicNameValuePair("e1",Welcome.uname));
            pairs.add(new BasicNameValuePair("e2",sperson));
            pairs.add(new BasicNameValuePair("e3",sblood));
            pairs.add(new BasicNameValuePair("e4",sloc));
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
                HttpPost httppost = new HttpPost(weburl.url+"/get-donor.php?uname="+Welcome.uname);

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
                }
                catch (Exception e) {
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
                cnt++;
            }

            if(cnt==0)
            {
                Toast.makeText(context, "No Donor Available", Toast.LENGTH_SHORT).show();
                finish();
            }
            List<String> categories=new ArrayList<String>();
            l=new String[cnt];
            b=new String[cnt];
            for(int i=0;i<peoples.length();i++){
                cnt++;
                JSONObject c = peoples.getJSONObject(i);
                categories.add( c.getString("v3"));
                l[i]=( c.getString("v2"));
                b[i]=( c.getString("v1"));
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            person.setAdapter(dataAdapter);
            progress.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class backgroundProcessClass1 extends AsyncTask<String,Void,Void>
    {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Toast.makeText(getApplicationContext(), receivedValue, Toast.LENGTH_SHORT).show();


            finish();

            progress.dismiss();

            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... params) {


            HttpClient client=new DefaultHttpClient();
            HttpPost post=new HttpPost(weburl.url+"donate.php");
            //temp=params[0];
            List<NameValuePair> pairs=new ArrayList<NameValuePair>(1);
            pairs.add(new BasicNameValuePair("e1",Welcome.uname));
            pairs.add(new BasicNameValuePair("e2",sperson));
            pairs.add(new BasicNameValuePair("e3",sblood));
            pairs.add(new BasicNameValuePair("e4",sloc));
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
