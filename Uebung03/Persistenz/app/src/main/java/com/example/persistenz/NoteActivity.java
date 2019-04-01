package com.example.persistenz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long noteId = getIntent().getExtras().getLong("noteId");
        if (noteId < 0) {
            setContentView(R.layout.activity_edit_note);
        } else {
            setContentView(R.layout.activity_show_note);
        }
        Log.i("NoteActivity", "TODO");
    }
}
