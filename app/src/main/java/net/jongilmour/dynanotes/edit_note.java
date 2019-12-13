package net.jongilmour.dynanotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class edit_note extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle, noteDetails;
    Calendar c;
    String curDate;
    String curTime;
    NoteDB db;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        long id = i.getLongExtra("ID", 0);
        db = new NoteDB(this);
        note = db.getNote(id);

        getSupportActionBar().setTitle(note.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteTitle = findViewById(R.id.txtNoteTitle);
        noteDetails = findViewById(R.id.txtNoteDetails);
        noteTitle.setText(note.getTitle());
        noteDetails.setText(note.getContent());

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){        // If the user has typed something.. /
                    getSupportActionBar().setTitle(s);  // Set the appbar title to what the user is typing //
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Get the current date and time //
        c = Calendar.getInstance();
        curDate = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR); // format dd/mm/yyyy, month index starts from 0 so +1 to get actual month
        curTime = pad(c.get(Calendar.HOUR)) + ":" + pad(c.get(Calendar.MINUTE));
        Log.d("Date and Time", curDate + " " + curTime);
    }


    private String pad(int i) {
        if (i < 10){
            return "0" + i;
        }
        return String.valueOf(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This will create the menu bar on app creation //
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            note.setTitle(noteTitle.getText().toString());
            note.setContent(noteDetails.getText().toString());
            int id = db.editNote(note);
            if(id == note.getId()){
                Toast.makeText(this, "note updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "an unexpected error occurred when updating.", Toast.LENGTH_SHORT).show();
            }
            goToMain();
        }

        if (item.getItemId() == R.id.delete) {
            db.deleteNote(note.getId());
            goToMain();
            Toast.makeText(this, "note deleted", Toast.LENGTH_SHORT).show();
            goToMain();
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        // Reload the main activity so it will show the updated list of notes //
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}

