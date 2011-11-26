package com.steo.vocab.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
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

    public  List<IDItem<String>> getSets() {

        Cursor cursor = mDatabase.query(VocabDatabaseHelper.SETTBL_NAME,
                new String[] {
                    VocabDatabaseHelper.SETTBL_KEY_ID,
                    VocabDatabaseHelper.SETTBL_KEY_NAME },
                null, null, null, null, null);

        int idCol = cursor.getColumnIndex(VocabDatabaseHelper.SETTBL_KEY_ID);
        int setCol = cursor.getColumnIndex(VocabDatabaseHelper.SETTBL_KEY_NAME);

        ArrayList<IDItem<String>> sets = new ArrayList<IDItem<String>>();

        while(cursor.moveToNext()) {
            sets.add(new IDItem<String>(cursor.getInt(idCol),
                    cursor.getString(setCol)));
        }

        return sets;
    }

    public boolean addSet(String set) {

        ContentValues values = new ContentValues();
        values.put(VocabDatabaseHelper.SETTBL_KEY_NAME, set);

        return mDatabase.insert(VocabDatabaseHelper.SETTBL_NAME, null, values) != -1;
    }

    public boolean deleteSet(IDItem<String> idItem) {

        Log.d(getClass().getSimpleName(), "Deleting Set Item ID [" +
                idItem.id + "] and name [" + idItem.item + "]");

        return mDatabase.delete(VocabDatabaseHelper.SETTBL_NAME,
                VocabDatabaseHelper.SETTBL_KEY_ID + "=?",
                new String[] { Integer.toString(idItem.id) }) > 0;
    }

    public List<IDItem<String>> getCategories() {

        Cursor cursor = mDatabase.query(VocabDatabaseHelper.CATEGORYTBL_NAME,
                new String[] {
                    VocabDatabaseHelper.CATEGORYTBL_KEY_ID,
                    VocabDatabaseHelper.CATEGORYTBL_KEY_CATEGORY },
                null, null, null, null, null);

        int idCol = cursor.getColumnIndex(VocabDatabaseHelper.CATEGORYTBL_KEY_ID);
        int catCol = cursor.getColumnIndex(VocabDatabaseHelper.CATEGORYTBL_KEY_CATEGORY);

        ArrayList<IDItem<String>> cats = new ArrayList<IDItem<String>>();

        while(cursor.moveToNext()) {
            cats.add(new IDItem<String>(cursor.getInt(idCol),
                    cursor.getString(catCol)));
        }

        return cats;
    }

    public boolean addCategory(String category) {

        ContentValues values = new ContentValues();
        values.put(VocabDatabaseHelper.CATEGORYTBL_KEY_CATEGORY, category);

        return mDatabase.insert(VocabDatabaseHelper.CATEGORYTBL_NAME,
                null, values) > 0;
    }

    public boolean deleteCategory(IDItem<String> idItem) {

        Log.d(getClass().getSimpleName(), "Deleting Category Item ID [" +
                idItem.id + "] and name [" + idItem.item + "]");

        return mDatabase.delete(VocabDatabaseHelper.CATEGORYTBL_NAME,
                VocabDatabaseHelper.CATEGORYTBL_KEY_ID + "=?",
                new String[] { Integer.toString(idItem.id) }) > 0;
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
