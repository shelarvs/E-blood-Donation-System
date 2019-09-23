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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

public class Donate_Blood extends AppCompatActivity {

    ListView lv1;
    TextView tv1,tv2;
    EditText loc,donor;
    Spinner sp;
    Button bn,add;
    ArrayAdapter<String> adapter;

    public static String n,l="",b;
    String[] location =new String[10];
    int cnt=0,ext;
    private ProgressDialog progress;
    final Context context=this;
    public static String receivedValue="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate__blood);

        lv1=(ListView)findViewById(R.id.lv1);
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        sp=(Spinner)findViewById(R.id.sp);
        bn=(Button)findViewById(R.id.bn);
        add=(Button)findViewById(R.id.add);
        loc=(EditText)findViewById(R.id.location);
        donor=(EditText)findViewById(R.id.donor);

        List<String> categories=new ArrayList<String>();
        categories.add("A+");
        categories.add("A-");
        categories.add("B+");
        categories.add("B-");
        categories.add("O+");
        categories.add("O-");
        categories.add("AB+");
        categories.add("AB-");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loc.getText().toString().equals(""))
                {
                    loc.setError("Enter Location");

                }
                else{
                    try {

                                addLocation();
                                loc.clearFocus();

                    } catch (Exception ex) {
                        Toast.makeText(Donate_Blood.this, "Error=" + ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });



        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (cnt == 0) {
                        loc.setError("Please Enter Single Location");
                    } else if (donor.getText().toString().equals("")) {
                        donor.setError("Enter Donor Name");
                    } else {
                        n = donor.getText().toString();
                        b = sp.getSelectedItem().toString();
                        for (int i = 0; i < cnt; i++) {
                                l = l  + location[i]+",";
                        }

                        try {
                            progress = new ProgressDialog(context);
                            progress.setMessage("Wait...");
                            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progress.setIndeterminate(false);
                            progress.setProgress(0);
                            progress.setCancelable(false);
                            progress.show();
                            new backgroundProcessClass().execute("");
                            Intent i=new Intent(getApplicationContext(),Upload_Report.class);
                            startActivity(i);
                            finish();
                        } catch (Exception ex) {
                            Toast.makeText(context, "Error=" + ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(context, "Error="+ex.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void addLocation()
    {
        location[cnt]=loc.getText().toString().toLowerCase();
        cnt++;
        CustomListAdapter adapter=new CustomListAdapter(this, location);
        lv1.setAdapter(adapter);
        loc.setText("");
    }
    private class backgroundProcessClass extends AsyncTask<String,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //Toast.makeText(context, "Received="+receivedValue, Toast.LENGTH_LONG).show();
            if(receivedValue.contains("Not"))
            {
                Toast.makeText(getApplicationContext(), "Not Added", Toast.LENGTH_SHORT).show();
            }
            else {
                 Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
            }
            progress.dismiss();

            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpClient client=new DefaultHttpClient();
            HttpPost post=new HttpPost(weburl.url+"/donate-blood.php");
            //temp=params[0];
            List<NameValuePair> pairs=new ArrayList<NameValuePair>(1);
            pairs.add(new BasicNameValuePair("e1",Welcome.uname));
            pairs.add(new BasicNameValuePair("e2",b));
            pairs.add(new BasicNameValuePair("e3",l));
            pairs.add(new BasicNameValuePair("e4",n));
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
