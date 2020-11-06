package com.example.notesapp.db;

public class NoteContract {
    private NoteContract(){

    }
    public static class Notes{
        public static final String TABLE_NAME="notes";
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_DESCRIPTION="description";
        public static final String COLUMN_DATE="date";
        public static final String COLUMN_PRIORITY="priority";
        public static final String COLUMN_ID="id";

    }
}
