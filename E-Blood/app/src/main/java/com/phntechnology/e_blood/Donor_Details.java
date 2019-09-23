package com.phntechnology.e_blood;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Donor_Details extends AppCompatActivity {

    TextView name,address,city,pincode,person,blood,loca;
    Button mobile,mobile1,landline,report;
    public static String rp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor__details);

        name=(TextView)findViewById(R.id.name);
        address=(TextView)findViewById(R.id.address);
        city=(TextView)findViewById(R.id.city);
        pincode=(TextView)findViewById(R.id.pincode);
        person=(TextView)findViewById(R.id.person);
        blood=(TextView)findViewById(R.id.blood);
        loca=(TextView)findViewById(R.id.loca);

        mobile=(Button)findViewById(R.id.mobile);
        mobile1=(Button)findViewById(R.id.mobile1);
        landline=(Button)findViewById(R.id.landline);
        report=(Button)findViewById(R.id.report);

        name.setText(Search_Donor.name[Search_Donor.pos]);
        address.setText(Search_Donor.address[Search_Donor.pos]);
        city.setText(Search_Donor.city[Search_Donor.pos]);
        pincode.setText(Search_Donor.pincode[Search_Donor.pos]);
        person.setText(Search_Donor.person[Search_Donor.pos]);
        blood.setText(Search_Donor.blood_type[Search_Donor.pos]);
        loca.setText(Search_Donor.loca[Search_Donor.pos]);

        mobile.setText(Search_Donor.mobile[Search_Donor.pos]);
        mobile1.setText(Search_Donor.mobile1[Search_Donor.pos]);
        landline.setText(Search_Donor.landline[Search_Donor.pos]);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rp=person.getText().toString()+".jpg";
                Intent i=new Intent(getApplicationContext(),Display_Report.class);
                startActivity(i);
            }
        });

        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + mobile.getText().toString()));
                startActivity(callIntent);
            }
        });
        mobile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + mobile1.getText().toString()));
                startActivity(callIntent);
            }
        });
        landline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + landline.getText().toString()));
                startActivity(callIntent);
            }
        });
    }
}
