package com.example.notesapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.notesapp.model.Note;

import java.util.ArrayList;

import static com.example.notesapp.db.NoteContract.Notes.COLUMN_DATE;
import static com.example.notesapp.db.NoteContract.Notes.COLUMN_DESCRIPTION;
import static com.example.notesapp.db.NoteContract.Notes.COLUMN_ID;
import static com.example.notesapp.db.NoteContract.Notes.COLUMN_PRIORITY;
import static com.example.notesapp.db.NoteContract.Notes.COLUMN_TITLE;
import static com.example.notesapp.db.NoteContract.Notes.TABLE_NAME;

public class NoteDao {

    private NoteDBHelper helper;

    public NoteDao(Context context){
        helper = new NoteDBHelper(context);
    }
    public long addNote(Note note){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,note.getTitle());
        cv.put(COLUMN_DESCRIPTION,note.getDescription());
        cv.put(COLUMN_DATE,note.getDate());
        cv.put(COLUMN_PRIORITY,note.getPriority());

        Log.i("priority","priority from the data base add method "+ note.getPriority());

        long id = db.insert(TABLE_NAME,null,cv);
        db.close();
        Log.i("dao","added");
        return id;
    }
    public void updateNote(Note note){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,note.getTitle());
        cv.put(COLUMN_DESCRIPTION,note.getDescription());
        cv.put(COLUMN_DATE,note.getDate());
        cv.put(COLUMN_PRIORITY,note.getPriority());
        db.update(TABLE_NAME,cv,COLUMN_ID + " = ? ",
                new String[]{String.valueOf(note.getId())});
        Log.i("dao","updated");
    }
    public int deleteNote(Note note){
        SQLiteDatabase db = helper.getWritableDatabase();
        int rows = db.delete(TABLE_NAME,
                COLUMN_ID +" = ?",
                new String[]{String.valueOf(note.getId())});
        if(rows!=0)
          Log.i("dao","note deleted");
        return rows;
    }

    public ArrayList<Note> getAllNotes(){
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID,COLUMN_TITLE,COLUMN_DESCRIPTION,COLUMN_DATE,COLUMN_PRIORITY},
                null,null,
                null,null,
                null,null
        );
        if(cursor!=null){
            while (cursor.moveToNext()){
                int noteId = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String date = cursor.getString(3);
                int priority = cursor.getInt(4);
                notes.add(new Note(priority,title,description,date,noteId));
            }
        }
       return notes;
    }
}
