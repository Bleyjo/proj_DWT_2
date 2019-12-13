package net.jongilmour.dynanotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


// UWS1920 COMP 10013 Dynamic Web Technologies
// B00346666 Jon A. Gilmour //

public class Details extends AppCompatActivity {
    TextView mDetails;
    NoteDB db;
    Note note;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        long id = i.getLongExtra("ID", 0);
        mDetails = findViewById(R.id.noteDesc);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        db = new NoteDB(this);
        note = db.getNote(id);

        getSupportActionBar().setTitle(note.getTitle());
        mDetails.setText(note.getContent());
        mDetails.setMovementMethod(new ScrollingMovementMethod());

        //Toast.makeText(this, "title:" + note.getTitle(), Toast.LENGTH_SHORT).show();

        FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.edit_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteNote(note.getId());
                goToMain();
            }
        });
    }

    private void goToMain() {
        // Reload the main activity so it will show the updated list of notes //
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This will create the menu bar on app creation //
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            Intent i = new Intent(this, edit_note.class);
            i.putExtra("ID", note.getId());
            v.vibrate(200);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}
