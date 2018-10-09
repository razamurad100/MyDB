package com.muiz.mydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//first we have to create a class (any name) then we have to extend it from SQLITEOPENHELPER
// remove error by creating constructor and create onCreate an onUpgrade function which should be overide

public class DBHelper extends SQLiteOpenHelper {
    //Database name
    public static final String DATABASE_NAME = "muiz.com";
    //Database Version
    public static final int DATABASE_VERSION = 1;
    //Table Name
    public static final String  TABLE_NAME = "myusers";
    //Table myusers Column
    //ID column @primaryKey
    public static final String KEY_ID = "id";
    //Column username
    public static final String KEY_USER_NAME = "username";
    //Column Email
    public static final String KEY_EMAIL = "email";
    //Column Password
    public static final String KEY_PASSWORD = "password";
    //SQL for creating user table
//    public static final String SQL_TABLE_USERS = " CREATE TABLE " +TABLE_USERS
//            + " ( "
//            +KEY_ID + " INTEGER PRIMARY KEY, "
//            +KEY_USER_NAME + " TEXT, "
//            +KEY_EMAIL + " TEXT, "
//            +KEY_PASSWORD + " TEXT "
//            +" ) ";


    public DBHelper( Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        String CREATE_TABLE =" CREATE TABLE " + TABLE_NAME + " ( "
                +KEY_ID + " INTEGER PRIMARY KEY,"
                +KEY_USER_NAME + " TEXT,"
                +KEY_EMAIL + " TEXT,"
                +KEY_PASSWORD + " TEXT"
                +" ) ";
        sqLiteDatabase.execSQL( CREATE_TABLE );
        Log.d("DBHelper","Table created" +TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL( " DROP TABLE IF EXISTS " + TABLE_NAME  );
    }

        //using this method we can add users to myuser table
    public void addUser(User user){
        //get writable database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //create content values to insert
        ContentValues contentValues = new ContentValues(  );
        //putting username in contentvalue
        contentValues.put( KEY_USER_NAME, user.userName );
        //putting email in contentvalues
        contentValues.put( KEY_EMAIL, user.email );
        //putting password in contentvalues
        contentValues.put( KEY_PASSWORD , user.password );
        //insert row
        long todo_id = sqLiteDatabase.insert( TABLE_NAME,null,contentValues );
    }
    public  User Authenticate(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,//selecting table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},
                KEY_EMAIL + "=?" ,
                new String[]{user.email},//where clause
                null,null,null);
        if(cursor != null && cursor.moveToFirst() && cursor.getCount()>0){
            //if cursor has value then in user database there is user associated with this
            User user1 = new User(cursor.getString( 0 ),cursor.getString( 1 ),cursor.getString( 2 ),cursor.getString( 3 ));

            //Match both passwords check they are same or not
            if(user.password.equalsIgnoreCase(user1.password)){
                return user1;
            }
        }
        //if user password does not matches or there is no record with that email then return false
        return null;
    }
    public boolean isEmailExists(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query( TABLE_NAME, //selecting table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//selecting column want to querry
                KEY_EMAIL + " =? ",
                new String[]{email},//where clause
                null,null,null );
        if(cursor != null && cursor.moveToFirst() && cursor.getCount()>0){
            //if cursor has value then in user database there is user associated with this given email
            return true;
        }
        //if email does not exist return false
        return false;
    }

}
