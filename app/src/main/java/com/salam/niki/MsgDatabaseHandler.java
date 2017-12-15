package com.salam.niki;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raj on 14-Dec-17.
 */

public class MsgDatabaseHandler extends SQLiteOpenHelper {



    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "messages";

    // Contacts table name
    private static final String TABLE_MSG = "msg";

    // Contacts Table Columns names
    private static final String KEY_MSG= "msg";
    private static final String KEY_SENDER = "sender";
    private static final String KEY_RECEIVER = "receiver";



    public MsgDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MSG + "("
                + KEY_MSG + " TEXT," + KEY_SENDER + " TEXT,"
                + KEY_RECEIVER + " VARCHAR" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MSG);

        // Create tables again
        onCreate(sqLiteDatabase);
    }


    // Adding new contact
    void addMsg(Messages_Info msgifno) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MSG,msgifno.get_msg());
        values.put(KEY_SENDER, msgifno.get_sender()); // Contact Name
        values.put(KEY_RECEIVER, msgifno.get_receiver()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_MSG, null, values);
        db.close(); // Closing database connection
    }


    public List<Messages_Info> getAllMessages(String receiver) {
        List<Messages_Info> msglist = new ArrayList<Messages_Info>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MSG +" WHERE "+ KEY_RECEIVER +" = "+ receiver;
        Log.e("query",selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.e("cursormsgcount", String.valueOf(cursor.getCount()));

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Messages_Info msginfo = new Messages_Info();
                msginfo.set_msg(cursor.getString(0));
                msginfo.set_sender(cursor.getString(1));
                msginfo.set_receiver(cursor.getString(2));
                // Adding contact to list
                msglist.add(msginfo);
            } while (cursor.moveToNext());
        }

        // return contact list
        return msglist;
    }

    public  List<Messages_Info> getAllReceiversNames(){
        List<Messages_Info> msglist = new ArrayList<Messages_Info>();
        // Select All Query
        String selectQuery = "SELECT  DISTINCT "+ KEY_RECEIVER + " FROM " + TABLE_MSG;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.e("cursor_coutn", String.valueOf(cursor.getCount()));

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Messages_Info msginfo = new Messages_Info();
                msginfo.set_receiver(cursor.getString(0));
                // Adding contact to list
                msglist.add(msginfo);
            } while (cursor.moveToNext());
        }

        // return contact list
        return msglist;
    }




}
