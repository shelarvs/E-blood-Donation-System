package com.phntechnology.e_blood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

public class Change_Password extends AppCompatActivity {

    EditText op,np,cp;
    SQLiteDatabase db;
    Button change;
    String sop,snp,scp,test;
    private ProgressDialog progress;
    final Context context=this;
    public static String receivedValue="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__password);

        op=(EditText)findViewById(R.id.op);
        np=(EditText)findViewById(R.id.np);
        cp=(EditText)findViewById(R.id.cp);
        change=(Button)findViewById(R.id.change);
        db=openOrCreateDatabase("blood", Context.MODE_PRIVATE,null);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sop=op.getText().toString();
                snp=np.getText().toString();
                scp=cp.getText().toString();

                if(sop.equals(""))
                {
                    op.setError("Enter old password");
                }
                else if(snp.equals(""))
                {
                    np.setError("Enter new password");
                }
                else if (scp.equals(""))
                {
                    cp.setError("Enter new password");
                }
                else if(!snp.equals(scp))
                {
                    cp.setError("Passwords not matched");
                }

                else
                {
                    try {
                        Cursor c = db.rawQuery("select * from login", null);
                        if (c.moveToFirst()) {
                            test = c.getString(1);
                        }
                        if(test.equals(sop)) {
                            progress = new ProgressDialog(context);
                            progress.setMessage("Wait...");
                            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progress.setIndeterminate(false);
                            progress.setProgress(0);
                            progress.setCancelable(false);
                            progress.show();
                            new backgroundProcessClass().execute("");
                        }
                        else
                        {
                            op.setError("Incorrect Old Password");
                            Toast.makeText(context, "Incorrect Old Password", Toast.LENGTH_SHORT).show();
                        }
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
             if(receivedValue.contains("incorrect"))
            {
                Toast.makeText(getApplicationContext(), "Incorrect Old Password", Toast.LENGTH_SHORT).show();
            }
            else {

                 Toast.makeText(getApplicationContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();

                finish();
            }
            progress.dismiss();

            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... params) {


            HttpClient client=new DefaultHttpClient();
            HttpPost post=new HttpPost(weburl.url+"/change-password.php");
            //temp=params[0];
            List<NameValuePair> pairs=new ArrayList<NameValuePair>(1);
            pairs.add(new BasicNameValuePair("e1",sop));
            pairs.add(new BasicNameValuePair("e2",snp));
            pairs.add(new BasicNameValuePair("e3",Welcome.uname));

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
