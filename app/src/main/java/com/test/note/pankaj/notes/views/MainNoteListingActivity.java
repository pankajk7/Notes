package com.test.note.pankaj.notes.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.test.note.pankaj.notes.model.Note;
import com.test.note.pankaj.notes.db.NoteDatabase;
import com.test.note.pankaj.notes.adapters.NoteListAdapter;
import com.test.note.pankaj.notes.R;

import java.util.List;

public class MainNoteListingActivity extends AppCompatActivity {

    private NoteListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_note_listing_layout);

        recyclerView = findViewById(R.id.recycler_view);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainNoteListingActivity.this, AddUpdateNoteActivity.class));
            }
        });

        setAdapterOrUpdateAdapter();
    }

    private void setAdapterOrUpdateAdapter() {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(MainNoteListingActivity.this);
        noteDatabase.daoAccess().fetchAllData()
                .observe(this, new android.arch.lifecycle.Observer<List<Note>>() {
                    @Override
                    public void onChanged(@Nullable List<Note> notes) {
                        if (adapter == null) {
                            adapter = new NoteListAdapter(MainNoteListingActivity.this, notes);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainNoteListingActivity.this));
                            recyclerView.setAdapter(adapter);
                        } else {
                            adapter.addUpdateList(notes);
                        }
                    }
                });
    }
}
