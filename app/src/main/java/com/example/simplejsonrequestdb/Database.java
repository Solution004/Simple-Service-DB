package com.example.simplejsonrequestdb;


public class Database {
    public static final String DATABASE_NAME = "dummyManager";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "tableName";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_NAME= "name";
    public static final String KEY_DESC = "description";
    public static final String KEY_DOB = "dob";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_HEIGHT= "height";
    public static final String KEY_SPOUSE = "spouse";
    public static final String KEY_CHILD = "child";
    public static final String KEY_USER_ID = "userId";

    public static final String CREATE_ONE_TABLE = "CREATE TABLE "
            + TABLE_NAME
            + "("
            + KEY_USER_ID + " TEXT,"
            + KEY_IMAGE + " TEXT,"
            + KEY_NAME + " TEXT,"
            + KEY_DESC + " TEXT,"
            + KEY_DOB+ " TEXT,"
            + KEY_COUNTRY + " TEXT,"
            + KEY_HEIGHT + " TEXT,"
            + KEY_SPOUSE + " TEXT,"
            + KEY_CHILD + " TEXT"
            + ")";
}
