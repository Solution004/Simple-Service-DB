package com.example.simplejsonrequestdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;



public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Database.DATABASE_NAME, null, Database.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Database.CREATE_ONE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Database.TABLE_NAME);
        // Create tables again
        onCreate(sqLiteDatabase);

    }

    // Updating timer status
    public void insertData(Actors data, boolean isFirst) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Database.KEY_USER_ID, data.getUserId());
            values.put(Database.KEY_IMAGE, data.getImage());
            values.put(Database.KEY_NAME, data.getName());
            values.put(Database.KEY_DESC, data.getDescription());
            values.put(Database.KEY_DOB, data.getDob());
            values.put(Database.KEY_COUNTRY, data.getCountry());
            values.put(Database.KEY_HEIGHT, data.getHeight());
            values.put(Database.KEY_SPOUSE, data.getSpouse());
            values.put(Database.KEY_CHILD, data.getChildren());
//            String selectQuery = "SELECT * FROM " + Database.TABLE_NAME + " WHERE userId " + " = '" + "1" + "'";
//
//            Cursor va = db.rawQuery(selectQuery, null);
////            if (va.getColumnIndexOrThrow("name") > 0) {
//                try {
//                    String name = va.getString(va.getColumnIndexOrThrow("name"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                // updating row
            if (isFirst) {
                db.insert(Database.TABLE_NAME, null, values);
            } else {
                db.update(Database.TABLE_NAME, values, Database.KEY_USER_ID + " = ?",
                        new String[]{data.getUserId()});
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getting All timer data
    public List<Actors> getDBList(ArrayList<Actors> actorsList) {
        actorsList.clear();
        try {

            // Select All Query
            String selectQuery = "SELECT * FROM " + Database.TABLE_NAME;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Actors actorData = new Actors();
                    actorData.setUserId(cursor.getString(0));
                    actorData.setImage(cursor.getString(1));
                    actorData.setName(cursor.getString(2));
                    actorData.setDescription(cursor.getString(3));
                    actorData.setDob(cursor.getString(4));
                    actorData.setCountry(cursor.getString(5));
                    actorData.setHeight(cursor.getString(6));
                    actorData.setSpouse(cursor.getString(7));
                    actorData.setChildren(cursor.getString(8));
                    // Adding contact to list
                    actorsList.add(actorData);
                } while (cursor.moveToNext());
                db.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        // return timer list
        return actorsList;
    }

}
