package com.example.notesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String database_name = "db_note";
    public static final String table_name = "table_notes";

    public static final String row_id = "_id";
    public static final String title = "title";
    public static final String note = "note";
    public static final String created = "created";

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + title + " TEXT," + note + " TEXT, " + created + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int x) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    public Cursor allData() {
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_id + " DESC ", null);
        return cur;
    }

    public Cursor oneData(Long id) {
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_id + "=" + id, null);
        return cur;
    }

    public void insertData(ContentValues values) {
        db.insert(table_name, null, values);
    }

    public void updateData(ContentValues values, long id) {
        db.update(table_name, values, row_id + "=" + id, null);
    }

    public void deleteData(long id) {
        db.delete(table_name, row_id + "=" + id, null);
    }


    public Cursor searchData(String keyword) {
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + title
                + " like ? " + "ORDER BY " + row_id + " DESC ", new String[]{ "%" + keyword + "%" }, null);
        return cur;
    }
}