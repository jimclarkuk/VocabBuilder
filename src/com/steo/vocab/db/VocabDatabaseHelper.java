package com.steo.vocab.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VocabDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "applicationdata";
    private static final int DATABASE_VERSION = 1;

    //TODO: May want a class per table to keep this tidy
    public static String WORDTBL_NAME = "tbl_vocab";
    public static String WORDTBL_KEY_NATIVE_WORD = "native_word";
    public static String WORDTBL_KEY_FOREIGN_WORD = "foreign_word";
    public static String WORDTBL_KEY_CATEGORY = "category";
    public static String WORDTBL_KEY_ID = "_id";

    public static String CATEGORYTBL_NAME = "tbl_categories";
    public static String CATEGORYTBL_KEY_CATEGORY = "category";
    public static String CATEGORYTBL_KEY_ID = "_id";

    private final String[] mDefaultCategories = { "Home", "Food", "Sport" };

    private static final String CATEGORY_TABLE_CREATE =
            "create table " + CATEGORYTBL_NAME +
            "(" + CATEGORYTBL_KEY_ID + " integer primary key autoincrement, " +
            CATEGORYTBL_KEY_CATEGORY + " text not null); ";

    private static final String WORD_TABLE_CREATE =
            "create table " + WORDTBL_NAME +
            "(" + WORDTBL_KEY_ID +" integer primary key autoincrement, " +
            WORDTBL_KEY_NATIVE_WORD + " text not null, " +
            WORDTBL_KEY_FOREIGN_WORD + " text not null, " +
            WORDTBL_KEY_CATEGORY + " integer, FOREIGN KEY (" + CATEGORYTBL_KEY_CATEGORY +
            ") REFERENCES " + CATEGORYTBL_NAME +
            ");";

    public VocabDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CATEGORY_TABLE_CREATE);
        database.execSQL(WORD_TABLE_CREATE);

        for(String s : mDefaultCategories) {

            ContentValues vals = new ContentValues();
            vals.put(VocabDatabaseHelper.CATEGORYTBL_KEY_CATEGORY, s);

            database.insert(VocabDatabaseHelper.CATEGORYTBL_NAME, null, vals);
        }
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
            int newVersion) {
    }
}