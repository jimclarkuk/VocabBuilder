package com.steo.vocab.db;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.steo.vocab.R;

public class VocabDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "applicationdata";
    private static final int DATABASE_VERSION = 1;

    //Partly copied from
    //http://stackoverflow.com/questions/2662749/database-schema-for-multiple-category-product-relationship

    //See a roughly drawn schema of this in schema.png at root of project

    //TODO: May want a class per table to keep this tidy
    //I really don't like having the schema defined like this. It's a mess. Perhaps there
    //is a resource file of SQL we can get execute or somefink.
    public static String CATEGORYTBL_NAME = "tbl_categories";
    public static String CATEGORYTBL_KEY_ID = "_id";
    public static String CATEGORYTBL_KEY_CATEGORY = "category";

    private static String CATEGORY_TBL_CREATE =
            "create table " + CATEGORYTBL_NAME +
            "(" + CATEGORYTBL_KEY_ID + " integer primary key autoincrement, " +
            CATEGORYTBL_KEY_CATEGORY + " text not null); ";

    public static String SETTBL_NAME = "tbl_sets";
    public static String SETTBL_KEY_ID = "_id";
    public static String SETTBL_KEY_NAME = "set_name";

    private static String SET_TBL_CREATE =
            "create table " + SETTBL_NAME +
            " (" + SETTBL_KEY_ID + " integer primary key autoincrement, " +
            SETTBL_KEY_NAME + " text not null);";

    public static String LANGTBL_NAME = "tbl_languages";
    public static String LANGTBL_KEY_ID = "_id";
    public static String LANGTBL_KEY_NAME = "language";

    private static String LANG_TBL_CREATE =
            "create table " + LANGTBL_NAME +
            " (" + LANGTBL_KEY_ID + " integer primary key autoincrement, " +
            LANGTBL_KEY_NAME + " text not null);";

    public static String WORDTBL_NAME = "tbl_words";
    public static String WORDTBL_KEY_ID = "_id";
    public static String WORDTBL_KEY_WORD = "word";
    public static String WORDTBL_KEY_LANGUAGE = "language";

    private static String WORD_TBL_CREATE =
            "create table " + WORDTBL_NAME + "(" +
            WORDTBL_KEY_ID +" integer primary key autoincrement, " +
            WORDTBL_KEY_WORD + " text not null, " +
            WORDTBL_KEY_LANGUAGE + " integer, " +
            "FOREIGN KEY (" + WORDTBL_KEY_LANGUAGE + ") REFERENCES " + LANGTBL_NAME + "(" + LANGTBL_KEY_ID + "));";

    //This tables maps a word to a category thus supporting multiple words per cat
    public static String WORDCAT_TBL_NAME = "tbl_word_categories";
    public static String WORDCAT_KEY_WORDID = "word_id";
    public static String WORDCAT_KEY_CATEGORY_ID = "category_id";

    public static String WORDCAT_TBL_CREATE =
            "create table " + WORDCAT_TBL_NAME + "(" +
            WORDCAT_KEY_WORDID +" integer, " +
            WORDCAT_KEY_CATEGORY_ID + " integer, " +
            "FOREIGN KEY (" + WORDCAT_KEY_WORDID + ") REFERENCES " + WORDTBL_NAME + "(" + WORDTBL_KEY_ID + ")," +
            "FOREIGN KEY (" + WORDCAT_KEY_CATEGORY_ID + ") REFERENCES " + CATEGORYTBL_NAME + "(" + CATEGORYTBL_KEY_ID + "));";

    //TODO: Is a set really any different to a category? Its just a different named grouping really.
    //      but in the UI I want to be able to distinguish between a set (like a weeks vocab) and
    //      a category. But maybe that doesn't need to be reflected so explicitly in the back end

    //This tables maps a word to a set thus supporting multiple sets per
    public static String WORDSET_TBL_NAME = "tbl_word_sets";
    public static String WORDSET_KEY_WORDID = "word_id";
    public static String WORDSET_KEY_SET_ID = "set_id";

    public static String WORDSET_TBL_CREATE =
            "create table " + WORDSET_TBL_NAME + "(" +
            WORDSET_KEY_WORDID +" integer, " +
            WORDSET_KEY_SET_ID + " integer, " +
            "FOREIGN KEY (" + WORDSET_KEY_WORDID + ") REFERENCES " + WORDTBL_NAME + "(" + WORDTBL_KEY_ID + ")," +
            "FOREIGN KEY (" + WORDSET_KEY_SET_ID + ") REFERENCES " + SETTBL_NAME + "(" + SETTBL_KEY_ID + "));";

    //This table maps a word to another word ie. translation
    public static String WORDTRANSLATION_TBL_NAME = "tbl_word_translation";
    public static String WORDTRANSLATION_KEY_WORD_ID1 = "word1";
    public static String WORDTRANSLATION_KEY_WORD_ID2 = "word2";

    public static String WORDTRANSLATION_TBL_CREATE =
            "create table " + WORDTRANSLATION_TBL_NAME + "(" +
            WORDTRANSLATION_KEY_WORD_ID1 +" integer, " +
            WORDTRANSLATION_KEY_WORD_ID2 + " integer, " +
            "FOREIGN KEY (" + WORDTRANSLATION_KEY_WORD_ID1 + ") REFERENCES " + WORDTBL_NAME + "(" + WORDTBL_KEY_ID + ")," +
            "FOREIGN KEY (" + WORDTRANSLATION_KEY_WORD_ID2 + ") REFERENCES " + WORDTBL_NAME + "(" + WORDTBL_KEY_ID + "));";

    private final Context mContext;

    public VocabDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        //These motherfuckers aren't on by default. That's right.
        //I used motherfucker in a comment. Not at work now.
        database.execSQL("PRAGMA foreign_keys = ON;");

        database.execSQL(CATEGORY_TBL_CREATE);
        database.execSQL(SET_TBL_CREATE);
        database.execSQL(LANG_TBL_CREATE);
        database.execSQL(WORD_TBL_CREATE);
        database.execSQL(WORDCAT_TBL_CREATE);
        database.execSQL(WORDSET_TBL_CREATE);
        database.execSQL(WORDTRANSLATION_TBL_CREATE);

        Activity act = (Activity)mContext;

        String[] defaultCats =
                act.getResources().getStringArray(R.array.default_categories);

        for(String s : defaultCats) {

            ContentValues vals = new ContentValues();
            vals.put(CATEGORYTBL_KEY_CATEGORY, s);

            database.insert(CATEGORYTBL_NAME, null, vals);
        }

        String[] defaultSets =
                act.getResources().getStringArray(R.array.default_sets);

        for(String s : defaultSets) {

            ContentValues vals = new ContentValues();
            vals.put(SETTBL_KEY_NAME, s);

            database.insert(SETTBL_NAME, null, vals);
        }
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
            int newVersion) {
    }
}