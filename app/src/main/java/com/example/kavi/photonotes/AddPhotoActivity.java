package com.example.kavi.photonotes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Environment;

/**
 * Created by Kavi on 2/3/16.
 */


public class AddPhotoActivity extends MainActivity {

    public ImageButton cameraButton;
    EditText caption_editText;
    Button saveButton;
    String caption_val;
    DataBaseHelper db;
    SQLiteDatabase sdb;
    private String mCurrentPhotoPath, filePath;
    File image;
    public static Cursor cursor;
    public static String caption,fullPath;
    CustomAdapter adapter;


    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addphoto_layout);


        caption_editText = (EditText) findViewById(R.id.caption);
        cameraButton = (ImageButton) findViewById(R.id.camera_button);
        saveButton = (Button) findViewById(R.id.save_button);


        cameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dispatchTakePictureIntent();

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(caption_editText.getText().length()==0){
                    Toast.makeText(getBaseContext(),"Please enter a caption",Toast.LENGTH_LONG).show();
                }
                else {
                    insert();

                }
            }
        });

    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        filePath = image.getAbsolutePath();
        Log.d("File Path ", filePath);
        return image;


    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
        galleryAddPic();
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);

    }

    public void insert() {
        caption_val = caption_editText.getText().toString();

        SQLiteDatabase sdb = new DataBaseHelper(this).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.CAPTION_COLUMN, caption_val);
        contentValues.put(DataBaseHelper.FILE_PATH_COLUMN, filePath);
        sdb.insert(DataBaseHelper.TABLE_NAME, null, contentValues);


        Intent intent= new Intent();
        intent .putExtra("caption", caption_val);
        intent .putExtra("filePath", filePath);
        setResult(RESULT_OK, intent);
        Toast.makeText(getBaseContext(), "value inserted", Toast.LENGTH_LONG).show();
        finish();

    }

}
