package net.jongilmour.dynanotes;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
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

// UWS1920 COMP 10013 Dynamic Web Technologies
// B00346666 Jon A. Gilmour //


public class AddNote extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle, noteDetails;
    Calendar c;
    String curDate;
    String curTime;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        noteTitle = findViewById(R.id.txtNoteTitle);
        noteDetails = findViewById(R.id.txtNoteDetails);
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
        curTime = c.get(Calendar.HOUR) + ":" + pad(c.get(Calendar.MINUTE));
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
            Note note = new Note(noteTitle.getText().toString(), noteDetails.getText().toString(), curDate, curTime);
            NoteDB db = new NoteDB(this);
            db.addNote(note);
            v.vibrate(200);
            Toast.makeText(this, "note saved", Toast.LENGTH_SHORT).show();
            goToMain();
        }

        if (item.getItemId() == R.id.delete) {
            v.vibrate(200);
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
