package com.example.notesapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Note implements Parcelable {
    int priority;
    String title;
    String description ;
    String date;
    boolean isExpandable;
    int id;

    public Note(int priority, String title, String description, String date) {
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.date = date;
        isExpandable=false;
        Log.i("priority","priority from the note model constructor "+ priority);
    }

    public Note(int priority, String title, String description, String date, int id) {
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.date = date;
        this.id = id;
        isExpandable=false;
    }

    protected Note(Parcel in) {
        priority = in.readInt();
        title = in.readString();
        description = in.readString();
        date = in.readString();
        isExpandable = in.readByte() != 0;
        id = in.readInt();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public int getPriority()
    {Log.i("priority","priority from the note model getter "+ priority+title);
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(priority);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(date);
        parcel.writeByte((byte) (isExpandable ? 1 : 0));
        parcel.writeInt(id);
    }
}
