package com.example.simplenotesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simplenotesapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText noteEditText;
    private Button addNoteButton, editNoteButton, deleteNoteButton;
    private ListView noteListView;

    private ArrayList<String> notesList;
    private ArrayAdapter<String> notesAdapter;
    private int selectedNotePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteEditText = findViewById(R.id.noteEditText);
        addNoteButton = findViewById(R.id.addNoteButton);
        editNoteButton = findViewById(R.id.editNoteButton);
        deleteNoteButton = findViewById(R.id.deleteNoteButton);
        noteListView = findViewById(R.id.noteListView);

        notesList = new ArrayList<>();
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        noteListView.setAdapter(notesAdapter);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        editNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNote();
            }
        });

        deleteNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        });

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedNotePosition = position;
                updateUI();
            }
        });
    }

    private void addNote() {
        String newNote = noteEditText.getText().toString().trim();
        if (!newNote.isEmpty()) {
            notesList.add(newNote);
            notesAdapter.notifyDataSetChanged();
            noteEditText.getText().clear();
            Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show();
        }
    }

    private void editNote() {
        if (selectedNotePosition != -1) {
            String updatedNote = noteEditText.getText().toString().trim();
            if (!updatedNote.isEmpty()) {
                notesList.set(selectedNotePosition, updatedNote);
                notesAdapter.notifyDataSetChanged();
                noteEditText.getText().clear();
                selectedNotePosition = -1;
                updateUI();
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Select a note to edit", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteNote() {
        if (selectedNotePosition != -1) {
            notesList.remove(selectedNotePosition);
            notesAdapter.notifyDataSetChanged();
            selectedNotePosition = -1;
            updateUI();
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Select a note to delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI() {
        if (selectedNotePosition != -1) {
            noteEditText.setText(notesList.get(selectedNotePosition));
            editNoteButton.setEnabled(true);
            deleteNoteButton.setEnabled(true);
        } else {
            noteEditText.getText().clear();
            editNoteButton.setEnabled(false);
            deleteNoteButton.setEnabled(false);
        }
    }
}
