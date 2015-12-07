package com.abhijeetonline.titi.titi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by asengu02 on 11/28/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static Context mContext = null;

    // Database Name
    private static final String DATABASE_NAME = "titiDBManager";

    // Contacts table name
    private static final String TABLE_LOCATION = "locations";

    // Contacts Table Columns names

    private static final String KEY_CELL_ID = "cellId";
    private static final String KEY_LOCATION = "location";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOCATION + "("
                + KEY_CELL_ID + " TEXT," + KEY_LOCATION + " TEXT" +
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);

        // Create tables again
        onCreate(db);
    }

    public void addLocation(String cellId, String locationName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CELL_ID, cellId);
        values.put(KEY_LOCATION, locationName);
        // Inserting Row
        db.insert(TABLE_LOCATION, null, values);
        db.close(); // Closing database connection
    }

    public String getLocation(String cellId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String locationName="";

        Cursor cursor = db.query(TABLE_LOCATION, new String[]{KEY_CELL_ID,
                        KEY_LOCATION}, KEY_CELL_ID + "=?",
                new String[]{cellId}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            locationName = cursor.getString(1);
        }
        cursor.close();
        return locationName;
    }

    public List<String> getAllLearntLocations(List<String> values) {
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        //final TiTiGlobal globalVariable = (TiTiGlobal)(mContext.getApplicationContext());
       // String currentCellId = globalVariable.getCurrentCellId();

        if (cursor.moveToFirst()) {
            do {
                String location = cursor.getString(0)+ ":"+cursor.getString(1);
                //if(cursor.getString(0).compareTo(currentCellId)==0)
                    //location = location+"<----Current CellID";
                values.add(location);
            } while (cursor.moveToNext());
        }

        // return contact list
        return values;
    }

    public void deleteLearntLocation(String cellId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String table = TABLE_LOCATION;
        String whereClause = KEY_CELL_ID + "=?";
        String[] whereArgs = new String[] { cellId };
        db.delete(table, whereClause, whereArgs);
        db.close();
    }
}
