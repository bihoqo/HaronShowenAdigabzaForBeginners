package com.mastegoane.adigabzaforbeginners.databases;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dictionary")
public class DictionaryEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String fullId;
    private String cyrillic;
    private String latin;
    private String audio;

    public DictionaryEntry(String fullId, String cyrillic, String latin, String audio) {
        this.fullId = fullId;
        this.cyrillic = cyrillic;
        this.latin = latin;
        this.audio = audio;
    }

    public int getId() {
        return id;
    }

    public String getFullId() {
        return fullId;
    }

    public String getCyrillic() {
        return cyrillic;
    }

    public String getLatin() {
        return latin;
    }

    public String getAudio() {
        return audio;
    }

    public void setId(int id) {
        this.id = id;
    }
}
