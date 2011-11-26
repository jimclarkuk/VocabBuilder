package com.steo.vocab.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class VocabDatabaseAdapter {

    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private final VocabDatabaseHelper mHelper;

    public VocabDatabaseAdapter(Context context) {
        mContext = context;
        mHelper = new VocabDatabaseHelper(mContext);
    }

    public VocabDatabaseAdapter open() throws SQLException {

        mDatabase = mHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mHelper.close();
    }

    public long addVocab(String nativeWord, String foreignWord) {

        return 0;
        //ContentValues values = new ContentValues();
        //values.put(VocabDatabaseHelper.WORDTBL_KEY_FOREIGN_WORD, foreignWord);
        //values.put(VocabDatabaseHelper.WORDTBL_KEY_NATIVE_WORD, nativeWord);

        //return mDatabase.insert(VocabDatabaseHelper.WORDTBL_NAME, null, values);
    }

    public ArrayList<String> getSets() {

        Cursor cursor = mDatabase.query(VocabDatabaseHelper.SETTBL_NAME,
                new String[] { VocabDatabaseHelper.SETTBL_KEY_NAME },
                null, null, null, null, null);

        int setCol = cursor.getColumnIndex(VocabDatabaseHelper.SETTBL_KEY_NAME);

        ArrayList<String> sets = new ArrayList<String>();

        while(cursor.moveToNext()) {
            sets.add(cursor.getString(setCol));
        }

        return sets;
    }

    public ArrayList<String> getCategories() {

        Cursor cursor = mDatabase.query(VocabDatabaseHelper.CATEGORYTBL_NAME,
                new String[] { VocabDatabaseHelper.CATEGORYTBL_KEY_CATEGORY },
                null, null, null, null, null);

        int catCol = cursor.getColumnIndex(VocabDatabaseHelper.CATEGORYTBL_KEY_CATEGORY);

        ArrayList<String> cats = new ArrayList<String>();

        while(cursor.moveToNext()) {
            cats.add(cursor.getString(catCol));
        }

        return cats;
    }

    /**
    public ArrayList<WordMapping> getVocab() {

        return null;

        Cursor cursor = mDatabase.query(VocabDatabaseHelper.WORDTBL_NAME,
                new String[] { VocabDatabaseHelper.WORDTBL_KEY_ID,
                    VocabDatabaseHelper.WORDTBL_KEY_NATIVE_WORD,
                    VocabDatabaseHelper.WORDTBL_KEY_FOREIGN_WORD },
                null, null, null, null, null);

        int nativeCol = cursor.getColumnIndex(VocabDatabaseHelper.WORDTBL_KEY_NATIVE_WORD);
        int foreignCol = cursor.getColumnIndex(VocabDatabaseHelper.WORDTBL_KEY_FOREIGN_WORD);

        ArrayList<WordMapping> wordMappings = new ArrayList<WordMapping>();

        while(cursor.moveToNext()) {

            ArrayList<WordMapping.Word> words = new ArrayList<WordMapping.Word>();
            words.add(new WordMapping.Word(cursor.getString(nativeCol), 0));
            words.add(new WordMapping.Word(cursor.getString(foreignCol), 0));

            wordMappings.add(new WordMapping(words, 0));
        }

        return wordMappings;

    }
*/
    /*
    public long addCategory(String category) {

        ContentValues values = new ContentValues();
        values.put(VocabDatabaseHelper.CATEGORYTBL_KEY_CATEGORY, category);

        return mDatabase.insert(VocabDatabaseHelper.CATEGORYTBL_NAME, null, values);
    }
    */

    public void debugDumpTables() {

        String[] tables = {
                VocabDatabaseHelper.WORDTBL_NAME,
                VocabDatabaseHelper.CATEGORYTBL_NAME,
                VocabDatabaseHelper.SETTBL_NAME,
                VocabDatabaseHelper.LANGTBL_NAME,
                VocabDatabaseHelper.WORDTBL_NAME,
                VocabDatabaseHelper.WORDCAT_TBL_NAME,
                VocabDatabaseHelper.WORDSET_TBL_NAME,
                VocabDatabaseHelper.WORDTRANSLATION_TBL_NAME };

        for(String table : tables) {

            Log.d(getClass().getSimpleName(), "Dumping [" + table + "]");

            Cursor cs = mDatabase.rawQuery("SELECT * FROM " + table, null);
            while(cs.moveToNext() ) {

                int numcols = cs.getColumnCount();
                String col = "";
                for(int i = 0; i < numcols; i++) {
                    col += "\t [" + cs.getString(i) + "] ";
                }

                Log.d(getClass().getSimpleName(), col);
            }


            cs.close();

        }
    }
}
