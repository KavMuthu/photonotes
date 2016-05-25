package com.example.kavi.photonotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.security.cert.CertificateParsingException;

/**
 * Created by Kavi on 2/6/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String ID_COLUMN = "ID_NO";
    public static final String CAPTION_COLUMN = "CAPTION";
    public static final String FILE_PATH_COLUMN = "filepath";

    public static final String DATABASE_NAME = "PicturesDB";
    public static final String TABLE_NAME = "PicturesTable";


    public static final int database_version = 1;
    public String CREATE_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "  %s integer primary key autoincrement, " +
                    "  %s text," +
                    "  %s text)",
            TABLE_NAME, ID_COLUMN, CAPTION_COLUMN,FILE_PATH_COLUMN);

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, database_version);
        Log.d("Database operations", "Database created");
    }
    @Override
    public void onCreate(SQLiteDatabase sdb) {

        sdb.execSQL(CREATE_TABLE);
        Log.d("Database operations", "Table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
