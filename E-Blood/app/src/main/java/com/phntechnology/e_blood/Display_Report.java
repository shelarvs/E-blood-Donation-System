package com.phntechnology.e_blood;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Display_Report extends AppCompatActivity implements  LoadImageTask.Listener {

    private ImageView mImageView;
    public static final String IMAGE_URL = weburl.url+"uploads/"+Donor_Details.rp;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__report);

        mImageView = (ImageView) findViewById(R.id.imageView2);



        new LoadImageTask(this).execute(IMAGE_URL);
    }


    public void onImageLoaded(Bitmap bitmap) {

        mImageView.setImageBitmap(bitmap);
    }


    public void onError() {
        Toast.makeText(this, "URL="+IMAGE_URL, Toast.LENGTH_SHORT).show();
    }
}
