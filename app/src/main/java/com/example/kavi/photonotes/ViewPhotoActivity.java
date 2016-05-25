package com.example.kavi.photonotes;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Kavi on 2/3/16.
 */
public class ViewPhotoActivity extends MainActivity {

    ImageView myImage;
    File imgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewphoto_layout);


        //caption
        TextView caption = (TextView) findViewById(R.id.captionText);
        caption.setText(getIntent().getExtras().getString("caption"));
        caption.setTextColor(Color.BLACK);

        //photo
        if (getIntent().getExtras().getString("filePath")!=null) {
            imgFile = new File(getIntent().getExtras().getString("filePath"));

            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                Bitmap b = Bitmap.createScaledBitmap(myBitmap, 120, 120, false);
                myImage = (ImageView) findViewById(R.id.camera_photo);
                myImage.setImageBitmap(b);
            } else {
                Toast.makeText(getBaseContext(), "No image found", Toast.LENGTH_SHORT).show();
            }

        }else{}
    }

}
