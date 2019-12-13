package net.jongilmour.dynanotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

// UWS1920 COMP 10013 Dynamic Web Technologies
// B00346666 Jon A. Gilmour //

public class NoteDB extends SQLiteOpenHelper {

    //----- Class-Global Variables -----//

    // Database Variables //
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NotesDB";
    private static final String TABLE_NAME = "notes";

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String DATE = "date";
    private static final String TIME = "time";

    NoteDB(Context context){
        // Constructor class for NoteDB.java
        // Runs when class is run //
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Called when the app is run for the first time //
        // Creates the table Notes //

        String query = "CREATE TABLE " + TABLE_NAME + "( " +                        // Create a table called "notes".. //
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +                          // Make the first field a number field called "id" that must be a unique value //
                TITLE + " TEXT, " +                                                 // Make a text field called "title" //
                CONTENT + " TEXT, " +                                               // Make a text field called "content" //
                DATE + " TEXT, " +                                                  // Make a text field called "date" that contains the date of record input //
                TIME + " TEXT" +                                                    // Make a text field called "time" that contains the time of the record input //
                ")";
        db.execSQL(query);          // Execute the SQL query //
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        if (oldVer > newVer) return;
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;                        // If the table already exists, delete the table //
        db.execSQL(query);          // Execute the SQL query //
        onCreate(db);               // Create a new table //
    }

    public long addNote(Note note){
        // Inserts a note into the DB //
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(TITLE, note.getTitle());
        c.put(CONTENT, note.getContent());
        c.put(DATE, note.getDate());
        c.put(TIME, note.getTime());

        long id = db.insert(TABLE_NAME, null, c);
        Log.d("Inserted Data", "ID: " + id);
        return id;
    }

    public int editNote(Note note){
        // Updates an existing record with new information //
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited Note", "Note Edited: " + note.getTitle());
        c.put(TITLE, note.getTitle());
        c.put(CONTENT, note.getContent());
        c.put(DATE, note.getDate());
        c.put(TIME, note.getTime());
        return db.update(TABLE_NAME, c, ID+"=?", new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(long target){
        // Deletes a record in the database //
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID+"=?", new String[]{String.valueOf(target)});
        db.close();
    }

    public Note getNote(long id){
        // Retrieves a single note from the DB //
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, TITLE, CONTENT, DATE, TIME}, ID+"=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null){    // If the row exists.. //
            cursor.moveToFirst();
        }
        return new Note(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
    }

    public List<Note> getNotes(){
        // Retrieves the entire list of notes from the DB //
        SQLiteDatabase db = this.getReadableDatabase();
        List<Note> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID + " DESC";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){    // If any rows exist.. //
            do {    // Set the new note object to contain the note record //
                Note note = new Note();
                note.setId(cursor.getLong(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));
                Log.d("NOTES DB: ", String.valueOf(cursor.getLong(0) + ", " + cursor.getString(2)));
                allNotes.add(note); // Adds the single record to the array //
            } while (cursor.moveToNext());
        }
        return allNotes;
    }
}