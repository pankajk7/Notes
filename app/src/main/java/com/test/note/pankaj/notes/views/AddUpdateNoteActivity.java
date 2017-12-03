package com.test.note.pankaj.notes.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.test.note.pankaj.notes.R;
import com.test.note.pankaj.notes.db.NoteDatabase;
import com.test.note.pankaj.notes.model.Note;

import java.util.Calendar;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class AddUpdateNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String ARG_NOTE_DATA = "note_data";

    EditText editText;
    private Note objNote;

    public static void startActivity(Context context, Note obj) {
        Intent intent = new Intent(context, AddUpdateNoteActivity.class);
        intent.putExtra(ARG_NOTE_DATA, obj);
        context.startActivity(intent);
    }

    private void extractArgument(Bundle bundle) {
        if (bundle == null) return;
        objNote = (Note) bundle.getSerializable(ARG_NOTE_DATA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_note);

        extractArgument(getIntent().getExtras());

        Toolbar toolbar = findViewById(R.id.toolbar_add_update);
        TextView tvTitle = findViewById(R.id.tv_title_add_update);
        if (objNote != null) {
            tvTitle.setText(getString(R.string.edit_note));
        } else {
            tvTitle.setText(getString(R.string.add_note));
        }
        editText = findViewById(R.id.et_note);
        ImageButton ibAddUpdate = findViewById(R.id.ib_add_update);
        ibAddUpdate.setOnClickListener(this);
        ImageButton ibBack = findViewById(R.id.ib_back);
        ibBack.setOnClickListener(this);

        setUpViewBasedOnNoteObject();
    }

    private void setUpViewBasedOnNoteObject() {
        if (objNote == null) return;
        editText.setText(objNote.getNote());
        editText.setSelection(editText.getText().toString().length());
    }

    private void addUpdateRecord() {
        final String noteString = editText.getText().toString().trim();
        if (TextUtils.isEmpty(noteString)) {
            Toast.makeText(AddUpdateNoteActivity.this, R.string.enter_some_text,
                    Toast.LENGTH_LONG).show();
            return;
        }
        Observable.defer(new Func0<Observable<List<Note>>>() {
            @Override
            public Observable<List<Note>> call() {
                Note note = new Note("Note Title", noteString,
                        Calendar.getInstance().getTime().getTime());
                NoteDatabase noteDatabase = NoteDatabase.getInstance(AddUpdateNoteActivity.this);
                if (objNote == null) {
                    noteDatabase.daoAccess().insertOnlySingleRecord(note);
                } else {
                    note.setId(objNote.getId());
                    noteDatabase.daoAccess().updateRecord(note);
                }

                return Observable.just(null);
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Note>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(AddUpdateNoteActivity.this, "Some error occured",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(List<Note> notes) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_add_update:
                addUpdateRecord();
                break;
        }
    }
}