package com.example.notesapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.notesapp.db.NoteContract.Notes.COLUMN_DATE;
import static com.example.notesapp.db.NoteContract.Notes.COLUMN_DESCRIPTION;
import static com.example.notesapp.db.NoteContract.Notes.COLUMN_ID;
import static com.example.notesapp.db.NoteContract.Notes.COLUMN_PRIORITY;
import static com.example.notesapp.db.NoteContract.Notes.COLUMN_TITLE;
import static com.example.notesapp.db.NoteContract.Notes.TABLE_NAME;

public class NoteDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="notesmanager";
    public static final  int DATABASE_VERSION=1;
    public NoteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE_NOTES = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME
                +" ( "+COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +COLUMN_TITLE +" TEXT NOT NULL ,"
                +COLUMN_DESCRIPTION +" TEXT NOT NULL ,"
                +COLUMN_DATE +" TEXT NOT NULL ,"
                +COLUMN_PRIORITY +" INTEGER );";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       final String SQL_DROP_TABLE_NOTES ="DROP TABLE IF EXISTS " +TABLE_NAME;
       sqLiteDatabase.execSQL(SQL_DROP_TABLE_NOTES);
       onCreate(sqLiteDatabase);
    }
}
