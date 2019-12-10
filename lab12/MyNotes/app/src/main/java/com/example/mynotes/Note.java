package com.example.mynotes;

import com.google.firebase.Timestamp;

public class Note {

    private String id;
    private Timestamp date;
    private String content;
    public Note(String id, Timestamp date, String content) {
        this.id = id;
        this.date = date;
        this.content = content;
    }
    public Note() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}