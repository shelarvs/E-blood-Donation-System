package com.phntechnology.e_blood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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

public class Search_Donor extends AppCompatActivity {

    public static String[] name,mobile,mobile1,landline,address,city,pincode,blood_type,loca,person;
    public static int pos=0;
    ListView lv;
    Button bn;
    Spinner sp;
    EditText location;

    int cnt=0;
    public String blood,loc,sign;

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
        setContentView(R.layout.activity_search__donor);

        lv=(ListView)findViewById(R.id.lv);
        bn=(Button) findViewById(R.id.bn);
        sp=(Spinner) findViewById(R.id.sp);
        location=(EditText)findViewById(R.id.location);

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

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blood=sp.getSelectedItem().toString();
                if(blood.contains("+"))
                {
                    sign="plus";
                    blood=blood.replace("+","");
                }
                else if(blood.contains("-"))
                {
                    sign="minus";
                    blood=blood.replace("-","");
                }


                loc=location.getText().toString().toLowerCase();
                if(loc.equals(""))
                {
                    location.setError("Enter Location");
                }
                else
                {
                    getData();
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    pos = position;
                    String Slecteditem = mobile[+position];
                    if (Slecteditem.equals("")) {
                    } else {
                        Intent i = new Intent(getApplicationContext(), Donor_Details.class);
                        startActivity(i);
                    }
                }
                catch (Exception ex)
                {

                }
            }
        });
    }

    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost(weburl.url+"/search-donors.php?blood="+blood+"&loc="+loc+"&sign="+sign);

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
            name=new String[cnt];
             mobile=new String[cnt];
             mobile1=new String[cnt];
             landline=new String[cnt];
             address=new String[cnt];
             city=new String[cnt];
             pincode=new String[cnt];
             blood_type=new String[cnt];
             loca=new String[cnt];
             person=new String[cnt];

            for(int i=0;i<peoples.length();i++){
                cnt++;
                JSONObject c = peoples.getJSONObject(i);
                name[i]=( c.getString("v1"));
                mobile[i]=( c.getString("v2"));
                mobile1[i]=( c.getString("v3"));
                landline[i]=( c.getString("v4"));
                address[i]=( c.getString("v5"));
                city[i]=( c.getString("v6"));
                pincode[i]=( c.getString("v7"));
                blood_type[i]=( c.getString("v8"));
                loca[i]=( c.getString("v9"));
                person[i]=(c.getString("v10"));
            }
            Search_Adapter adapter=new Search_Adapter(this, name,blood_type,loca,mobile);
            lv.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
