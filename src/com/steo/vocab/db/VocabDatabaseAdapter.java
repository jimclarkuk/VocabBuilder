package com.steo.vocab.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.steo.vocab.Category;
import com.steo.vocab.WordMapping;

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

        ContentValues values = new ContentValues();
        values.put(VocabDatabaseHelper.WORDTBL_KEY_FOREIGN_WORD, foreignWord);
        values.put(VocabDatabaseHelper.WORDTBL_KEY_NATIVE_WORD, nativeWord);

        return mDatabase.insert(VocabDatabaseHelper.WORDTBL_NAME, null, values);
    }

    public ArrayList<WordMapping> getVocab() {

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

    public long addCategory(String category) {

        ContentValues values = new ContentValues();
        values.put(VocabDatabaseHelper.CATEGORYTBL_KEY_CATEGORY, category);

        return mDatabase.insert(VocabDatabaseHelper.CATEGORYTBL_NAME, null, values);
    }

    public ArrayList<Category> getCategories() {

        Cursor cursor = mDatabase.query(VocabDatabaseHelper.CATEGORYTBL_NAME,
                new String[] { VocabDatabaseHelper.CATEGORYTBL_KEY_ID,
                    VocabDatabaseHelper.CATEGORYTBL_KEY_CATEGORY },
                null, null, null, null, null);

        ArrayList<Category> cats = new ArrayList<Category>();

        int idCol = cursor.getColumnIndex(VocabDatabaseHelper.CATEGORYTBL_KEY_ID);
        int categoryCol = cursor.getColumnIndex(VocabDatabaseHelper.CATEGORYTBL_KEY_CATEGORY);

        while(cursor.moveToNext()) {

            Category cat = new Category(cursor.getString(categoryCol),
                    cursor.getInt(idCol));

            cats.add(cat);
        }

        return cats;
    }
}
