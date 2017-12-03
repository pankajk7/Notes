package com.test.note.pankaj.notes.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.test.note.pankaj.notes.model.Note;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DaoAccess {

    @Insert
    void insertMultipleRecord(Note... notes);

    @Insert
    void insertMultipleListRecord(List<Note> notes);

    @Insert
    void insertOnlySingleRecord(Note note);

    @Query("SELECT * FROM Note")
    List<Note> fetchAllDataNormal();

    @Query("SELECT * FROM Note")
    LiveData<List<Note>> fetchAllData();

    @Query("SELECT * FROM Note WHERE id =:note_id")
    Note getSingleRecord(int note_id);

    @Update(onConflict = REPLACE)
    void updateRecord(Note note);

    @Delete
    void deleteRecord(Note note);
}