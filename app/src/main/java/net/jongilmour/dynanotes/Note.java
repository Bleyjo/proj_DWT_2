package net.jongilmour.dynanotes;

// UWS1920 COMP 10013 Dynamic Web Technologies
// B00346666 Jon A. Gilmour //

public class Note {
    private long id;
    private String title;
    private String content;
    private String date;
    private String time;

    Note(){
        // Runs when no data is passed into the class //
    }

    Note(String title, String content, String date, String time){
        // Runs when specified data is passed into the class //
        // This will be used when creating a new note //
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
    }

    Note(long id, String title, String content, String date, String time){
        // Runs when specified data is passed into the class //
        // This will be used when editing a previously-created note //
        this.id = id;
        this.title = title;
        if (title == null || title==""){
            this.title = "New note";
        }
        this.content = content;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
