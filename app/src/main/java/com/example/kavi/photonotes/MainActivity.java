package com.example.kavi.photonotes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Cursor cursor;
    static String caption,fullPath;
    String[] c;
    ListView listview;
    List<PhotoCaption> photoCaptionList;
    CustomAdapter adapter;
    public static int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPhotoIntent = new Intent(MainActivity.this, AddPhotoActivity.class);
                startActivityForResult(addPhotoIntent,REQUEST_CODE);
            }
        });

        query();

    }
    public void query() {

        listview = (ListView) findViewById(R.id.listView);

        SQLiteDatabase sdb = new DataBaseHelper(this).getReadableDatabase();
        photoCaptionList = new ArrayList<>();

        String where = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {DataBaseHelper.ID_COLUMN, DataBaseHelper.CAPTION_COLUMN,DataBaseHelper.FILE_PATH_COLUMN};
        cursor = sdb.query(DataBaseHelper.TABLE_NAME, resultColumns, where, whereArgs, groupBy, having, order);
        if(cursor!=null) {
            if  (cursor.moveToFirst()) {
                do {
                    caption = cursor.getString(1);
                    fullPath = cursor.getString(2);
                    photoCaptionList.add(new PhotoCaption(caption,fullPath));
                }while (cursor.moveToNext());
            }
        }
        adapter = new CustomAdapter(this, R.layout.row_layout, photoCaptionList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoCaption photoCaption = photoCaptionList.get(position);

                Intent viewIntent = new Intent(MainActivity.this, ViewPhotoActivity.class);
                viewIntent.putExtra("caption", photoCaption.getCaption());
                viewIntent.putExtra("filePath", photoCaption.getFilePath());
                startActivity(viewIntent);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            photoCaptionList.add(new PhotoCaption(data.getStringExtra("caption"),data.getStringExtra("filePath")));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //uninstall menu item
        if (id == R.id.action_uninstall) {
            Uri packageURI = Uri.parse("package:com.example.kavi.photonotes");
            Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
