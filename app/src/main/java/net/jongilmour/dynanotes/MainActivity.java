package net.jongilmour.dynanotes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.List;


// UWS1920 COMP 10013 Dynamic Web Technologies
// B00346666 Jon A. Gilmour //


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    Adapter adapter;
    List<Note> notes;

    //Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    Vibrator v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NoteDB db = new NoteDB(this);
        notes = db.getNotes();
        recyclerView = findViewById(R.id.notesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, notes);
        recyclerView.setAdapter(adapter);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This will create the menu bar on app creation //
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @RequiresApi(api = 26)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent i = new Intent(this, AddNote.class);
            startActivity(i);
            v.vibrate(200);
            Toast.makeText(this, "Creating a new note", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
