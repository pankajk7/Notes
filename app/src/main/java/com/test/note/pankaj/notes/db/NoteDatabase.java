package com.test.note.pankaj.notes.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.test.note.pankaj.notes.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static volatile NoteDatabase noteDatabaseInstance;

    public abstract DaoAccess daoAccess();

    public static NoteDatabase getInstance(Context context) {
        if (noteDatabaseInstance == null) {
            synchronized (NoteDatabase.class) {
                if (noteDatabaseInstance == null) {
                    noteDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "Note.db")
                            .build();
                }
            }
        }
        return noteDatabaseInstance;
    }
}